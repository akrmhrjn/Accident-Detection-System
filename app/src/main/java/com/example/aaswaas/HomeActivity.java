package com.example.aaswaas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class HomeActivity extends Activity {


    // Within which the entire activity is enclosed
    private DrawerLayout mDrawerLayout;

    // ListView represents Navigation Drawer
    private ListView mDrawerList;

    // ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar
    private ActionBarDrawerToggle mDrawerToggle;

    // Title of the action bar
    public String mTitle;


    //service button
    //Button startserviceButton;
    ToggleButton tgbutton;
    TextView txtactivate;
    GPSTracker gps;
    String address;

    //Service
    public static boolean isService = false;
    //public static boolean noService = false;

    public static boolean open;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

        moveTaskToBack(true);
        //open = false;

    }


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        mTitle = "aasWaas";
        getActionBar().setTitle(mTitle);
        getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));

        //For Location
        gps = new GPSTracker(this);

        // check if GPS enabled     
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            address = getAddress(latitude, longitude);

        }else{

            //gps.showSettingsAlert();
        }
        // Getting reference to the DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        // Getting reference to the ActionBarDrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();

            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("aasWaas");
                invalidateOptionsMenu();
            }
        };


        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }

        // Setting DrawerToggle on DrawerLayout
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // Creating an ArrayAdapter to add items to the listview mDrawerList


        List<HashMap<String,String>> data = GetSampleData();
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                R.layout.drawer_list_item, new String[] { "image", "list" },
                new int[] { R.id.image,android.R.id.text1 });




        // Setting the adapter on mDrawerList
        mDrawerList.setAdapter(adapter);

        // Enabling Home button
        getActionBar().setHomeButtonEnabled(true);

        // Enabling Up navigation
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting item click listener for the listview mDrawerList
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // display view for selected nav drawer item
                open = true;
                displayView(position);
            }

        });



        tgbutton = (ToggleButton) findViewById(R.id.btntoggle);
        tgbutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (tgbutton.isChecked()) {
                    startService(new Intent(HomeActivity.this,BackgroundService.class));
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                    isService = true;
                    open = false;

                } else {
                    stopService(new Intent(HomeActivity.this,BackgroundService.class));
                    isService = false;
                    open = true;
                    Toast.makeText(getApplicationContext(), "Deactivated ", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopService(new Intent(HomeActivity.this,BackgroundService.class));
        open = true;
        tgbutton.setChecked(false);
        if(isService)
        {
            //TextView tv = (TextView) findViewById(R.id.textView1);
            //tv.setText("Service Resumed");
            //isService = false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    public void displayView(int position) {
        // update the main content by replacing fragments
        tgbutton = (ToggleButton) findViewById(R.id.btntoggle);
        txtactivate = (TextView) findViewById(R.id.txtactivate);
        Fragment fragment = null;
        switch (position) {
            case 0:
                txtactivate.setVisibility(View.INVISIBLE);
                tgbutton.setVisibility(View.INVISIBLE);
                fragment = new dashboard();
                break;

            case 1:
                txtactivate.setVisibility(View.INVISIBLE);
                tgbutton.setVisibility(View.INVISIBLE);
                fragment = new show_profile();
                break;

            case 2:
                txtactivate.setVisibility(View.INVISIBLE);
                tgbutton.setVisibility(View.INVISIBLE);
                fragment = new profile();
                break;

            case 3:
                txtactivate.setVisibility(View.INVISIBLE);
                tgbutton.setVisibility(View.INVISIBLE);
                fragment = new edit_message();
                break;

            case 4:
                txtactivate.setVisibility(View.INVISIBLE);
                tgbutton.setVisibility(View.INVISIBLE);
                fragment = new add_contacts();
                break;

            case 5:
                txtactivate.setVisibility(View.INVISIBLE);
                tgbutton.setVisibility(View.INVISIBLE);
                fragment = new blood_login();
                break;

            case 6:
                txtactivate.setVisibility(View.VISIBLE);
                tgbutton.setVisibility(View.VISIBLE);
                fragment = new settings();
                break;


            default:
                break;
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            String[] menuItems = getResources().getStringArray(R.array.menus);
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mTitle = menuItems[position];
            mDrawerLayout.closeDrawer(mDrawerList);
        } else
        {
            // error in creating fragment
            Log.e("HomeActivity", "Error in creating fragment");
        }
    }

    //Event called when volume key up is pressed
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            // Do your thing
            final DbHandler db = new DbHandler(this);
            List<Contact> contacts = db.getAllContacts();

            List<message> msg = db.getAllMsg();
            String m1 = "",m2 = "";
            for (message ms : msg) {
                m1 = ms.getMessage1();//panic huda
                m2 = ms.getMessage2();//accident huda

            }
            for (Contact cn : contacts) {
                String log = cn.getPhoneNumber();
                PendingIntent pi = PendingIntent.getActivity(this, 0,
                        new Intent(this, dashboard.class), 0);
                SmsManager sms = SmsManager.getDefault();
                // this is the function that does all the magic
                sms.sendTextMessage(log, null, m2 + " Address: "+ address + "-via aaswaas", pi, null);

            }
            Toast.makeText(this,"Message sent." , Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    //Event called when volume key down is pressed
   /* public boolean onKeyDown(int keyCode, KeyEvent event) { 
	   if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) { 
	       // Do your thing 
		   
		   Toast.makeText(this, "Volume down pressed ", Toast.LENGTH_SHORT).show();
	       return true;
	   } else {
	       return super.onKeyDown(keyCode, event); 
	   }
	}*/

    List<HashMap<String,String>> GetSampleData()
    {
        int[] pic = new int[]{R.drawable.dashboard,R.drawable.profile,android.R.drawable.ic_menu_edit,R.drawable.contact,R.drawable.msg,R.drawable.blood,R.drawable.settings};
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

        HashMap<String,String> map = new HashMap<String,String>();

        map.put("list", "Dashboard");
        map.put("image", String.valueOf(pic[0]));
        list.add(map);

        map = new HashMap<String,String>();
        map.put("image", String.valueOf(pic[1]));
        map.put("list", "Profile");
        list.add(map);

        map = new HashMap<String,String>();
        map.put("image", String.valueOf(pic[2]));
        map.put("list", "Edit Profile");
        list.add(map);

        map = new HashMap<String,String>();
        map.put("image", String.valueOf(pic[4]));
        map.put("list", "Edit Message");
        list.add(map);

        map = new HashMap<String,String>();
        map.put("image", String.valueOf(pic[3]));
        map.put("list", "Manage Contacts");
        list.add(map);

        map = new HashMap<String,String>();
        map.put("image", String.valueOf(pic[5]));
        map.put("list", "Blood Group");
        list.add(map);

        map = new HashMap<String,String>();
        map.put("image", String.valueOf(pic[6]));
        map.put("list", "Settings");
        list.add(map);

        return list;
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0)).append(", ");//shiva
                result.append(address.getSubLocality()).append(", ");//sanepa
                result.append(address.getLocality()).append(", ");//lalitpur
                result.append(address.getCountryName());//nepal
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

}