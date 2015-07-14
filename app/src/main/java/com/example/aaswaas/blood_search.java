package com.example.aaswaas;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;


public class blood_search extends Activity {

	// Progress Dialog
	public ProgressDialog pDialog;


	// url to login
	private static String url = "http://10.0.3.2/aaswaas_api/get_blood.php";
	
	// JSON Node names
	//private static final String TAG_SUCCESS = "success";
	private static final String TAG_USER = "users";
	private static final String TAG_NAME = "name";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_BLOOD = "blood";
	
	  // contacts JSONArray
    JSONArray users = null;
    
    ListView bloodlist;
    ArrayList<HashMap<String,String>> userlist;
	private String mTitle = "aasWaas";
	
	 //Edittext
    EditText bld;
    
    //Dropdownlist
    private String[] bg = { "A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve","O+ve","O-ve" };
	String bl;
	Spinner blood;
	Button search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.blood_search);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		getActionBar().setTitle("Blood Search");
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));
		
		//bld=(EditText)findViewById(R.id.txtsearch);
		
		bloodlist = (ListView)findViewById(R.id.bloodlist);
    	userlist = new ArrayList<HashMap<String,String>>(); 
    	
    	blood = (Spinner)findViewById(R.id.blood);
		 
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bg);
		  adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  blood.setAdapter(adapter_state);
		  
		  blood.setOnItemSelectedListener(new OnItemSelectedListener() {
			 public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
				  blood.setSelection(position);
				  bl = (String) blood.getSelectedItem();
				  
				 }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
    	
    	search = (Button)findViewById(R.id.btnsearch);
		
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				blood.setVisibility(View.INVISIBLE);
				
				new GetUser().execute();
				
			}
		});
 
		
	}

	/**
     * Async task class to get json by making HTTP call
     * */
    private class GetUser extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            search.setVisibility(View.INVISIBLE);
            pDialog = new ProgressDialog(blood_search.this);
			pDialog.setMessage("Please Wait..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
        	
        	
            // Creating service handler class instance
            ServiceHandler jp = new ServiceHandler();
            
            //String bloodsearch = bld.getText().toString();
			
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("blood", bl));
			
			
            // Making a request to url and getting response
            String jsonStr = jp.makeServiceCall(url,ServiceHandler.POST, params);
 
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                
                    // Getting JSON Array node
                    users = jsonObj.getJSONArray(TAG_USER);
 
                    // looping through All Users
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);
                         
                        //String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String address = c.getString(TAG_ADDRESS);
                        String phone = c.getString(TAG_PHONE);
                        String blood = c.getString(TAG_BLOOD);
                        
 
                        // tmp hashmap for single contact
                        HashMap<String, String> user = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        
                        user.put(TAG_NAME, name);
                        user.put(TAG_ADDRESS, address);
                        user.put(TAG_PHONE, phone);
                        user.put(TAG_BLOOD, blood);
 
 
                        // adding contact to contact list
                        userlist.add(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
 			
            return null;
        }

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	
	@Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        bloodlist.clearChoices();
        
        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
        /**
         * Updating parsed JSON data into ListView
         * */
        SimpleAdapter adapter = null;
        adapter = new SimpleAdapter(getApplicationContext(), userlist, 
		 R.layout.blood_row, new String[] { TAG_NAME, TAG_ADDRESS,TAG_BLOOD,TAG_PHONE },new int[] { R.id.txtname,R.id.txtaddress,R.id.txtblood,R.id.txtphone });

		// Setting the adapter on mDrawerList
        
		bloodlist.setAdapter(adapter);
		//adapter.notifyDataSetChanged();
		
    }
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
          	  return true;
               
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
 
	
}

	
