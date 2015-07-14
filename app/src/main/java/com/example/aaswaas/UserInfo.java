package com.example.aaswaas;

public class UserInfo {

	//private variables
		int _id;
		String _name;
		String _phone_number;
		String _address;
		String _bloodgrp;
		byte[] _image;
		
		// Empty constructor
		public UserInfo(){
			
		}
		// constructor
		public UserInfo(int id, String name,String phone_number,String address,String bloodgrp,byte[] image){
			this._id = id;
			this._name = name;
			this._phone_number = phone_number;
			this._address = address;
			this._bloodgrp = bloodgrp;
			this._image = image;
		}
		
		// constructor
		public UserInfo(int id, String name,String phone_number,String address,String bloodgrp){
			this._id = id;
			this._name = name;
			this._phone_number = phone_number;
			this._address = address;
			this._bloodgrp = bloodgrp;
		
		}
		
		// constructor
		public UserInfo(String name,String phone_number,String address,String bloodgrp,byte[] image){
			this._name = name;
			this._phone_number = phone_number;
			this._address = address;
			this._bloodgrp = bloodgrp;
			this._image = image;
		}
		
		// constructor
		public UserInfo(String name,String phone_number,String address,String bloodgrp){
			this._name = name;
			this._phone_number = phone_number;
			this._address = address;
			this._bloodgrp = bloodgrp;

		}
		
		//constructor
		public UserInfo(byte[] image)
		{
			this._image = image;
		}
		
		// getting ID
		public int getID(){
			return this._id;
		}
		
		// setting id
		public void setID(int id){
			this._id = id;
		}
		
		//getting name
		public String getName(){
			return this._name;
		}
		
		// setting name
		public void setName(String name){
			this._name = name;
		}
		
		
		// getting phone number
		public String getPhoneNumber(){
			return this._phone_number;
		}
		
		// setting phone number
		public void setPhoneNumber(String phone_number){
			this._phone_number = phone_number;
		}

		//getting address
		public String getAddress(){
			return this._address;
		}
		
		// setting address
		public void setAddress(String address){
			this._address = address;
		}
		
		//getting bloodgrp
		public String getBloodGrp(){
			return this._bloodgrp;
		}
		
		// setting bloodgrp
		public void setBloodGrp(String bloodgrp){
			this._bloodgrp = bloodgrp;
		}
		
		// getting image
		public byte[] getImage() {
			return this._image;
		}

		// setting image
		public void setImage(byte[] image) {
			this._image = image;
		}


}
