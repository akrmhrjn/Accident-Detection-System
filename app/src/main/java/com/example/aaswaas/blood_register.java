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
import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


public class blood_register extends Activity {

	// Progress Dialog
	public ProgressDialog pDialog;


	// url to login
	private static String url = "http://10.0.3.2/aaswaas_api/insert_user.php";
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	  // contacts JSONArray
    JSONArray users = null;
    
    ListView bloodlist;
    ArrayList<HashMap<String,String>> userlist;
	private String mTitle;
	
	 //Edittext
    EditText name;
    EditText address;
    EditText phone;
    EditText password;
    
    //Dropdownlist
    private String[] bg = { "A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve","O+ve","O-ve" };
	String bl;
	Spinner blood;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.blood_register);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mTitle="Register";
		getActionBar().setTitle(mTitle);
		getActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg));

        final DbHandler db = new DbHandler(this);


        List<UserInfo> user = db.getAllUser();

		
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
		name=(EditText)findViewById(R.id.name);
		address=(EditText)findViewById(R.id.address);
		phone=(EditText)findViewById(R.id.phone);
		password=(EditText)findViewById(R.id.pass);
		
		
    	//userlist = new ArrayList<HashMap<String,String>>(); 
    	
    	Button register = (Button)findViewById(R.id.btnregister);
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new InsertUser().execute();
				
			}
		});

        for (UserInfo us : user) {


            String nm = us.getName();
            String addr = us.getAddress();
            String mob =  us.getPhoneNumber();
            String bg = us.getBloodGrp();


            name.setText(nm);
            address.setText(addr);
            phone.setText(mob);

            int spinnerPosition = adapter_state.getPosition(bg);
            blood.setSelection(spinnerPosition);
            blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
                    bl = (String) blood.getSelectedItem();

                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });

        }
 
		
	}

	/**
     * Async task class to get json by making HTTP call
     * */
    private class InsertUser extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            pDialog = new ProgressDialog(blood_register.this);
			pDialog.setMessage("Please Wait..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
        	
        	
            // Creating service handler class instance
            ServiceHandler jp = new ServiceHandler();
            
            String nm = name.getText().toString();
            String ad = address.getText().toString();
            String ph = phone.getText().toString();
            String pass = password.getText().toString();
			
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", nm));
			params.add(new BasicNameValuePair("address", ad));
			params.add(new BasicNameValuePair("phone", ph));
			params.add(new BasicNameValuePair("blood", bl));
			params.add(new BasicNameValuePair("password", pass));
			
			
            // Making a request to url and getting response
            String jsonStr = jp.makeServiceCall(url,ServiceHandler.POST, params);
			//jp.makeServiceCall(url,ServiceHandler.POST, params);
			
			//Toast.makeText(getApplicationContext(), "Successfully registered!!", Toast.LENGTH_LONG).show();
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
            	try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    int success = jsonObj.getInt(TAG_SUCCESS);

    				if (success == 1) {
    					// successfully inserted
    					Intent i = new Intent(blood_register.this, HomeActivity.class);
    					startActivity(i);
    					 //Toast.makeText(getBaseContext(), "Successfully registered!!", Toast.LENGTH_LONG).show();
    					// closing this screen
    					//finish();
    				} else {
    					// failed to create product
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
        
        // Dismiss the progress dialog
            pDialog.dismiss();
      
      }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

	
