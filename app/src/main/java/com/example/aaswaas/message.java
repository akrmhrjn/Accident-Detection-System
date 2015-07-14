package com.example.aaswaas;

public class message {
	
	//private variables
		int _id;
		//String _msg;
		String _message1;
		//String _msg;
		String _message2;
		
		// Empty constructor
		public message(){
			
		}
		// constructor
		public message(int id, String _message1, String _message2){
			this._id = id;
			this._message1 = _message1;
			this._message2 = _message2;
		}
		
		// constructor
		public message(String _message1,String _message2){
			this._message1 = _message1;
			this._message2 = _message2;
		}
		// getting ID
		public int getID(){
			return this._id;
		}
		
		// setting id
		public void setID(int id){
			this._id = id;
		}

		
		// getting message1
		public String getMessage1(){
			return this._message1;
		}
		
		// setting message1
		public void setMessage1(String message1){
			this._message1 = message1;
		}
		
		// getting message2
		public String getMessage2(){
			return this._message2;
		}
		
		// setting message2
		public void setMessage2(String message2){
			this._message2 = message2;
		}
	}




