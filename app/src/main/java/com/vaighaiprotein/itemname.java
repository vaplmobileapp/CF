package com.vaighaiprotein;

public class itemname {
	
	String _id;
	String _itemname;
	
public itemname(){
		
	}

public itemname(String id, String itemname){
	this._id = id;
	this._itemname = itemname;
	
}

public String getID(){
	return this._id;
}

// setting id
public void setID(String id){
	this._id = id;
}

public String getName(){
	return this._itemname;
}

// setting name
public void setName(String itemname){
	this._itemname = itemname;
}

}
