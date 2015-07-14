package com.example.aaswaas;

import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.*;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class add_contacts extends Fragment {

	public add_contacts(){}
    
	int i=1;
   
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	
    	
    	 final DbHandler db = new DbHandler(getActivity());
         
    	 
    	   List<Contact> contacts = db.getAllContacts();
           LinearLayout mylinear =(LinearLayout)getView().findViewById(R.id.mylinear);
           //TextView[] arraycn = new TextView[100];
           int n = 0;
           int count = 1;
           for (Contact cn : contacts) {
        	   n = cn.getID();
        	  
               String log = i + ". "+cn.getPhoneNumber();
               TextView rowcn = new TextView(getActivity());
               rowcn.setTextColor(Color.BLACK);
               rowcn.setTextSize(16);
               rowcn.setText("\n"+log);
               mylinear.addView(rowcn);
               i++;
               count++;
            }
           
           final int s = n;
           final int c = count;
           
           /**
          * CRUD Operations
          * */
    	 Button add = (Button) getView().findViewById(R.id.btnadd);
    	 Button delete = (Button) getView().findViewById(R.id.btndelete);
    	 
    	 add.setOnClickListener(new OnClickListener() {

 			@Override
 			public void onClick(View arg0) {
 				
 				if(c<6){
	 				EditText con =(EditText) getView().findViewById(R.id.txtcontact);
	 		    	final String phone = con.getText().toString();
	 				db.addContact(new Contact(phone));
	 				Toast.makeText(getActivity(), "Contact Added.", Toast.LENGTH_SHORT).show();
	 				Intent i = new Intent(getActivity(), HomeActivity.class);
					startActivity(i);
	 				con.setText(null);
 				}
 				else{
 					Toast.makeText(getActivity().getBaseContext(), "No more Contacts can be Added.", Toast.LENGTH_SHORT).show();
 				}
 
 			
 			}

 		});
    
    	 delete.setOnClickListener(new OnClickListener() {
    		 
  	   
  			@Override
  			public void onClick(View arg0) {
  	   
  				db.deleteCon(s);
  				Toast.makeText(getActivity(), "Contact deleted.", Toast.LENGTH_SHORT).show();
  				
  				Intent i = new Intent(getActivity(), HomeActivity.class);
				startActivity(i);
  				
  			}
  			
  			
  		});
    	 

	}
         
    	
    
    

        
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.add_contacts, container, false);
        
       return rootView;
    }
}
    