package com.example.aaswaas;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.*;


public class profile extends Fragment {

	private String[] bg = { "A+ve","A-ve","B+ve","B-ve","AB+ve","AB-ve","O+ve","O-ve" };
	String bl;
	Spinner blood;
	 
	public profile(){}
    
	 public void onActivityCreated(Bundle savedInstanceState) {
	    	super.onActivityCreated(savedInstanceState);
	    
	    	 
	    	 final DbHandler db = new DbHandler(getActivity());
	    	 	List<UserInfo> user = db.getAllUser();
	           //RelativeLayout myrelative =(RelativeLayout)getView().findViewById(R.id.myrelative);
	           //TextView[] arraycn = new TextView[100];
	           int n = 0;
	        
	           
	      	 	EditText nm =(EditText) getView().findViewById(R.id.name);
	 			EditText addr =(EditText) getView().findViewById(R.id.address);
	 			EditText ph =(EditText) getView().findViewById(R.id.phone);
	 			//EditText blood =(EditText) getView().findViewById(R.id.blood);
	 
	 			blood = (Spinner)getView().findViewById(R.id.blood);
	 			 
	 	          ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, bg);
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
	 			
	           for (UserInfo us : user) {
	       
	        	  
		            String name = us.getName();
		            String address = us.getAddress();
		            String mob =  us.getPhoneNumber();
		            String bg = us.getBloodGrp();
		            
	             
		 			nm.setText(name);
		 			addr.setText(address);
		 			ph.setText(mob);

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
		 			//blood.setText(bg);
		 			n++;
	            }
	           final int s = n;
	          
	           Button save = (Button) getView().findViewById(R.id.btnsave);
	           save.setOnClickListener(new OnClickListener() {
	        	  
	 			@Override
	 			public void onClick(View arg0) {
	 				
 					EditText address =(EditText) getView().findViewById(R.id.address);
 				 	EditText name =(EditText) getView().findViewById(R.id.name);					
					EditText phone =(EditText) getView().findViewById(R.id.phone);
											
					String ad = address.getText().toString();
					String nm = name.getText().toString();			    	
			    	String ph = phone.getText().toString();
			    	
	 		    	if(s>0){
	 		    		db.updateUser(new UserInfo(nm,ph,ad,bl));
	 		    		Toast.makeText(getActivity(), "Profile Updated.", Toast.LENGTH_SHORT).show();
	 		    		
	 		    	}
	 		    	else{
	 		    		db.addUserInfo(new UserInfo(nm,ph,ad,bl));
	 		    		Toast.makeText(getActivity(), "Profile Saved.", Toast.LENGTH_SHORT).show();
	 		    		
	 		    	}			
	 			}

	 		});
	           

	          
	    	 

		}
	 


	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.profile, container, false);
          
        return rootView;
    }
}