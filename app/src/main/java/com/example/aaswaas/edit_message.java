package com.example.aaswaas;

import java.util.List;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class edit_message extends Fragment {

	
	public edit_message(){}
    
	 public void onActivityCreated(Bundle savedInstanceState) {
	    	super.onActivityCreated(savedInstanceState);
        
	    	final DbHandler db = new DbHandler(getActivity());
			
   	 
	   	 	List<message> msg = db.getAllMsg();
	
	          int n = 0;
	          
	          EditText msg1 =(EditText)getView().findViewById(R.id.editText1);
			  EditText msg2 =(EditText) getView().findViewById(R.id.editText2);
	          for (message ms : msg) {
	      
				  //ms.getID();
				  String m1 = ms.getMessage1();
				  String m2 = ms.getMessage2();
				  msg1.setText(m1);
				  msg2.setText(m2);
				  n++;
	           }
	          final int s = n;
	          Button save = (Button)getView().findViewById(R.id.btnsave);
	          save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						
						
						EditText msg1 =(EditText)getView().findViewById(R.id.editText1);
						EditText msg2 =(EditText) getView().findViewById(R.id.editText2);
						
				    	final String nm = msg1.getText().toString();
				    	final String ad = msg2.getText().toString();
				    	
				    	
						
				    	if(s>0){
				    		db.updateMsg(new message(nm,ad));
				    		Toast.makeText(getActivity(), "Message Updated.", Toast.LENGTH_SHORT).show();
				    			
				    	}
				    	else{
				    		db.addMessage(new message(nm,ad));
				    		Toast.makeText(getActivity(), "Message Saved.", Toast.LENGTH_SHORT).show();
				    		;	
				    	}			
					}

		});
          
  
    }
	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	  
	        View rootView = inflater.inflate(R.layout.edit_message, container, false);
	          
	        return rootView;
	    }
}
