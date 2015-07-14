package com.example.aaswaas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class blood_login extends Fragment{
	
	// Progress Dialog
	public ProgressDialog pDialog;


	// url to login
	private static String url = "http://10.0.3.2/aaswaas_api/get_login.php";
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	
	  // contacts JSONArray
    JSONArray login = null;
    
    //Edittext
    EditText number;
	EditText password;
	TextView register;
	  
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	
    	number=(EditText)getView().findViewById(R.id.txtnum);
		password=(EditText)getView().findViewById(R.id.txtpass);
		register = (TextView)getView().findViewById(R.id.txtregister);
    	
    	Button login = (Button)getView().findViewById(R.id.btnlogin);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new GetLogin().execute();
				
			}
		});
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity().getBaseContext(), blood_register.class);
				startActivity(i);
				
				
			}
		});
		
		
    }
    
    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetLogin extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            
            pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Logging In..");
			//pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
        	
        	
            // Creating service handler class instance
            ServiceHandler jp = new ServiceHandler();
 
            
            String num = number.getText().toString();
			String pass = password.getText().toString();
			
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("number", num));
			params.add(new BasicNameValuePair("password", pass));
			
            // Making a request to url and getting response
            String jsonStr = jp.makeServiceCall(url,ServiceHandler.POST, params);
 
            Log.d("Response: ", "> " + jsonStr);
 
           if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    int success = jsonObj.getInt(TAG_SUCCESS);

    				if (success == 1) {
    					// successfully created product
    					Intent i = new Intent(getActivity().getBaseContext(), blood_search.class);
    					startActivity(i);
    					 //Toast.makeText(getActivity().getBaseContext(), "Success!!", Toast.LENGTH_LONG).show(); 
    					
    					// closing this screen
    					//finish();
    				} else {
    					// failed to create product
    				}
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.blood_login, container, false);
        
       return rootView;
    }
}
