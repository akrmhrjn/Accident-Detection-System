package com.example.aaswaas;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;





public class dashboard extends Fragment implements SensorEventListener {
	
	
	
	public dashboard(){}
    

    private double tempg = 0;
	//private boolean mInitialized;
	public SensorManager mSensorManager;
    public Sensor mAccelerometer;
    
	int i=1;
	GPSTracker gps;
	String address;
	TextView gf;
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	
    
    	//For G-Force
    	mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
        
        gf = (TextView)getView().findViewById(R.id.txtgf);
    
        //For Location
        gps = new GPSTracker(getActivity());
        TextView location = (TextView)getView().findViewById(R.id.location);

        // check if GPS enabled     
       if(gps.canGetLocation()){
             
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            location.setText("Searching...");
            address = getAddress(latitude, longitude);
            location.setText(address);
            
      }else{

        		gps.showSettingsAlert();
        }
        
        
      //To Extract Phone Number
        final DbHandler db = new DbHandler(getActivity().getBaseContext());
         	
        /**
         * CRUD Operations
         * */
   	
        List<Contact> contacts = db.getAllContacts();
        LinearLayout mylinear =(LinearLayout)getView().findViewById(R.id.mylinear);
        //TextView[] arraycn = new TextView[100];
       
       for (Contact cn : contacts) {
            String log = i + ". "+cn.getPhoneNumber();
            TextView rowcn = new TextView(getActivity());

            rowcn.setTextColor(Color.BLACK);
            rowcn.setTextSize(16);
            rowcn.setText("\n"+log);

            mylinear.addView(rowcn);
            
            i++;
        }
       
       List<UserInfo> user = db.getAllUser();
       TextView uname = (TextView)getView().findViewById(R.id.txtuname);
       for (UserInfo us: user){
    	   String name = us.getName();
    	   uname.setText(name);

    	   
       }

   	}



        
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.dashboard, container, false);
        
       return rootView;
    }
    
    public void onStart() {
        super.onStart();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
   HomeActivity ser = new HomeActivity();
    
   
    public void onStop(){
    	super.onStop();
    	//if(ser.isService)
    	//getActivity().moveTaskToBack(true);
    
    	if (ser.open)
    		mSensorManager.unregisterListener(this);
    	else
    	 	mSensorManager.registerListener(this,mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    


    public void onPause() {
        super.onPause();
         	
        mSensorManager.unregisterListener(this);
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// can be safely ignored for this demo
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		
		double a = Math.sqrt(x*x+y*y+z*z);
		double g = a/9.81;
		if(g>tempg)
		{
			tempg = g;
		}	
		
		if(g>3)
		{
			final DbHandler db = new DbHandler(getActivity());
			List<Contact> contacts = db.getAllContacts();
			
			List<message> msg = db.getAllMsg();
			String m1 = "",m2 = "";
			for (message ms : msg) {
			  m1 = ms.getMessage1();//panic huda
			  m2 = ms.getMessage2();//accident huda
			  
			}
          
			
			for (Contact cn : contacts) {	
	            String log = cn.getPhoneNumber();
	            PendingIntent pi = PendingIntent.getActivity(getActivity(), 0,
	            new Intent(getActivity().getBaseContext(), dashboard.class), 0);                
	            SmsManager sms = SmsManager.getDefault();
	            // this is the function that does all the magic
	            sms.sendTextMessage(log, null, m2 + " Address: "+ address + "-via aaswaas", pi, null);
	        }
			Toast.makeText(getActivity(),"Message sent." , Toast.LENGTH_SHORT).show();
			//Toast.makeText(getActivity().getBaseContext(),m2+"message sent" , Toast.LENGTH_SHORT).show();
		}
		//String gd="again";
		gf.setText(Double.toString(g));
		//return tempg;
	}
    
	
	private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0)).append(", ");//shiva
                //result.append(address.getFeatureName()).append("\n");
                result.append(address.getSubLocality()).append(", ");//sanepa
                //result.append(address.getSubAdminArea()).append("\n");//bagmati
               //result.append(address.getMaxAddressLineIndex()).append("\n");
                //result.append(address.getAdminArea()).append("\n");//cdr
                result.append(address.getLocality()).append(", ");//lalitpur
                result.append(address.getCountryName());//nepal
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

}

