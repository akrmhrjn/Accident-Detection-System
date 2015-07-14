package com.example.aaswaas;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class show_profile extends Fragment {

	
	public show_profile(){}
    
	 public void onActivityCreated(Bundle savedInstanceState) {
	    	super.onActivityCreated(savedInstanceState);
	    
	    	 final DbHandler db = new DbHandler(getActivity());
	         
	    	 
	    	 List<UserInfo> user = db.getAllUser();
	          // RelativeLayout myrelative =(RelativeLayout)getView().findViewById(R.id.myrelative);
	    	 TextView nm =(TextView) getView().findViewById(R.id.name);
	 			TextView addr =(TextView) getView().findViewById(R.id.address);
	 			TextView ph =(TextView) getView().findViewById(R.id.mob);
	 			TextView blood =(TextView) getView().findViewById(R.id.blood);
	           //int n = 0;
	           for (UserInfo us : user) {
	       
	        	  
		            String name = us.getName();
		            String address = us.getAddress();
		            String mob =  us.getPhoneNumber();
		            String bg = us.getBloodGrp();
		            
	             
		 			nm.setText(name);
		 			addr.setText(address);
		 			ph.setText(mob);
		 			blood.setText(bg);
		 			//n++;
	            }

	          
	    	 

		}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.show_profile, container, false);
          
        return rootView;
    }
}
