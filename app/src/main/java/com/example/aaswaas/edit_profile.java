package com.example.aaswaas;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class edit_profile extends Activity {

    Spinner blood;
    String bl;
    //Dropdownlist
    private String[] bg = { "A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve","O+ve","O-ve" };

	

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        
        final DbHandler db = new DbHandler(this);
        
   	 
   	 	List<UserInfo> user = db.getAllUser();
         // RelativeLayout myrelative =(RelativeLayout)findViewById(R.id.myrelative);
          //TextView[] arraycn = new TextView[100];
          int n = 0;
          for (UserInfo us : user) {
      
        	  us.getID();
             //String log = "Name: " + us.getID();
             /*TextView rowcn = new TextView(this);
             rowcn.setTextColor(Color.BLACK);
             //rowcn.setText("\n"+log);
             myrelative.addView(rowcn);*/
             n++;
           }
          final int s = n;
          Button save = (Button)findViewById(R.id.btnsave);
          save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				
				EditText name =(EditText)findViewById(R.id.name);
				EditText address =(EditText) findViewById(R.id.address);
				EditText phone =(EditText) findViewById(R.id.phone);
				//Spinner blood =(Spinner) findViewById(R.id.blood);
                blood = (Spinner)findViewById(R.id.blood);

                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, bg);
                adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                blood.setAdapter(adapter_state);

                blood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
                        blood.setSelection(position);
                        bl = (String) blood.getSelectedItem();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }
                });
				
				
		    	final String nm = name.getText().toString();
		    	final String ad = address.getText().toString();
		    	final String ph = phone.getText().toString();
		    	//final String bl = blood.getText().toString();
		    	
				
		    	if(s>0){
		    		db.updateUser(new UserInfo(nm,ph,ad,bl));
		    		Toast.makeText(edit_profile.this, "Profile Updated.", Toast.LENGTH_SHORT).show();
		    		Intent intent = new Intent(edit_profile.this, show_profile.class);
		    		edit_profile.this.startActivity(intent);	
		    	}
		    	else{
		    		db.addUserInfo(new UserInfo(nm,ph,ad,bl));
		    		Toast.makeText(edit_profile.this, "Profile Saved.", Toast.LENGTH_SHORT).show();
		    		Intent intent = new Intent(edit_profile.this, show_profile.class);
		    		edit_profile.this.startActivity(intent);	
		    	}			
			}

		});
          
  
    }
}
