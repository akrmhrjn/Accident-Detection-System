package com.example.aaswaas;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
//import android.view.Menu;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import android.view.View;
//import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.os.Bundle;
//import android.telephony.SmsManager;

public class MainActivity extends Activity implements SensorEventListener {
	
	//private float mLastX, mLastY, mLastZ;
	private double tempg = 0;
	//private boolean mInitialized;
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
   // private final float NOISE = (float) 2.0;
	 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mInitialized = false;
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    
    
    }
    
    //Event called when volume key up is pressed
    	public boolean onKeyUp(int keyCode, KeyEvent event) { 
 		   if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) { 
 		       // Do your thing 
 			/*PendingIntent pi = PendingIntent.getActivity(this, 0,
 	            new Intent(this, MainActivity.class), 0);                
 	        SmsManager sms = SmsManager.getDefault();
 	        // this is the function that does all the magic
 	        sms.sendTextMessage("9841782679", null, "test", pi, null);*/

 			  Toast.makeText(this, "Volume up pressed ", Toast.LENGTH_SHORT).show();
 		       return true;
 		   } else {
 		       return super.onKeyUp(keyCode, event); 
 		   }
 		}
    	//Event called when volume key down is pressed   
        public boolean onKeyDown(int keyCode, KeyEvent event) { 
		   if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) { 
		       // Do your thing 
			   
			   Toast.makeText(this, "Volume down pressed ", Toast.LENGTH_SHORT).show();
		       return true;
		   } else {
		       return super.onKeyDown(keyCode, event); 
		   }
		}
        

    
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// can be safely ignored for this demo
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		TextView tvX= (TextView)findViewById(R.id.x_axis);
		TextView tvY= (TextView)findViewById(R.id.y_axis);
		TextView tvZ= (TextView)findViewById(R.id.z_axis);
		TextView gf = (TextView)findViewById(R.id.txtgf);
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		
		double a = Math.sqrt(x*x+y*y+z*z);
		double g = a/9.81;
		if(g>tempg)
		{
			tempg = g;
		}		
		tvX.setText(Float.toString(x));
		tvY.setText(Float.toString(y));
		tvZ.setText(Float.toString(z));
		gf.setText(Double.toString(tempg));
	}
	
	public double getg()
	{
		return tempg;
	
	}
}
