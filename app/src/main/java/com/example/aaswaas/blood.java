package com.example.aaswaas;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class blood extends Fragment{
	
	// Progress Dialog
		public ProgressDialog pDialog;

	
		// url to login
		private static String url = "http://192.168.38.50/aaswaas_api/get_blood.php";
		// JSON Node names
		//private static final String TAG_SUCCESS = "success";
		private static final String TAG_USER = "users";
		private static final String TAG_NAME = "name";
		private static final String TAG_ADDRESS = "address";
		
		  // contacts JSONArray
	    JSONArray users = null;
	    
	    ListView bloodlist;
	    ArrayList<HashMap<String,String>> userlist;

	public blood(){}
    

    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    
        /*WebView browser = (WebView)getView().findViewById(R.id.webView);
        String url = "http://www.ankurmaharjan.com.np";
        browser.getSettings().setLoadsImagesAutomatically(true);
        browser.getSettings().setJavaScriptEnabled(true);
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.loadUrl(url);
        browser.setWebViewClient(new MyBrowser());*/
    	 	
    	bloodlist = (ListView)getView().findViewById(R.id.bloodlist);
    	userlist = new ArrayList<HashMap<String,String>>();    	
    	new GetUser().execute();
    }
    
		/**
	     * Async task class to get json by making HTTP call
	     * */
	    private class GetUser extends AsyncTask<Void, Void, Void> {
	 
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            
	            pDialog = new ProgressDialog(getActivity());
				pDialog.setMessage("Please Wait..");
				//pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
	 
	        }
	 
	        @Override
	        protected Void doInBackground(Void... arg0) {
	        	
	        	
	            // Creating service handler class instance
	            ServiceHandler jp = new ServiceHandler();
	 
	            // Making a request to url and getting response
	            String jsonStr = jp.makeServiceCall(url,ServiceHandler.GET);
	 
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
	                        
	 
	                        // tmp hashmap for single contact
	                        HashMap<String, String> user = new HashMap<String, String>();
	 
	                        // adding each child node to HashMap key => value
	                        
	                        user.put(TAG_NAME, name);
	                        user.put(TAG_ADDRESS, address);
	 
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
	        
	        
	        // Dismiss the progress dialog
	        if (pDialog.isShowing())
	            pDialog.dismiss();
	        /**
	         * Updating parsed JSON data into ListView
	         * */
	        
	        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), userlist, 
			 R.layout.blood_row, new String[] { TAG_NAME, TAG_ADDRESS },new int[] { R.id.txtname,R.id.txtaddress });
	
			// Setting the adapter on mDrawerList
			bloodlist.setAdapter(adapter);
	    }
		
	}

    /*
     private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
           view.loadUrl(url);
           return true;
        }

   	}
     */
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.blood_layout, container, false);
        
       return rootView;
    }

}
