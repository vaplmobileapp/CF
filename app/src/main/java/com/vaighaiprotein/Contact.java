package com.vaighaiprotein;

public class Contact {

    // private variables
    public int _id;
    public int _freight;
    public int _other;
    public String _name;
    public String _phone_number;
    public String _email;
    public String _Branch;
    public String _loaddate;
    public String _loadchg;
    

    public Contact() {
    }

    // constructor
    public Contact(int id, String name, String _phone_number, String _email,int _freight,int _other,String _Branch,String _loaddate,String _loadchg) {
	this._id = id;
	this._name = name;
	this._phone_number = _phone_number;
	this._email = _email;
	this._freight=_freight;
	this._other=_other;
	this._Branch=_Branch;
	this._loaddate=_loaddate;
	this._loadchg=_loadchg;

    }

    // constructor
    public Contact(String name, String _phone_number, String _email,int _freight,int _other, String _Branch,String _loaddate,String _loadchg) {
	this._name = name;
	this._phone_number = _phone_number;
	this._email = _email;
	this._freight=_freight;
	this._other=_other;
	this._Branch=_Branch;
	this._loaddate=_loaddate;
	this._loadchg=_loadchg;
    }

    // getting ID
    public int getID() {
	return this._id;
    }

    // setting id
    public void setID(int id) {
	this._id = id;
    }

    // getting name
    public String getName() {
	return this._name;
    }

    // setting name
    public void setName(String name) {
	this._name = name;
    }

    // getting phone number
    public String getPhoneNumber() {
	return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
	this._phone_number = phone_number;
    }

    // getting email
    public String getEmail() {
	return this._email;
    }

    // setting email
    public void setEmail(String email) {
	this._email = email;
    }
    
    public int getfreight() {
    	return this._freight;
        }

        // setting id
        public void setfreight(int _freight) {
    	this._freight = _freight;
        }
        public int getothchg() {
        	return this._other;
            }

            // setting id
            public void setothchg(int _other) {
        	this._other = _other;
            }

            
                public String getbranch() {
                	return this._Branch;
                    }

                    // setting id
                    public void setbranc(String _Branch) {
                	this._Branch = _Branch;
                    }
                    public String getloaddate() {
                    	return this._loaddate;
                        }

                        // setting id
                        public void setloaddate(String _loaddate) {
                    	this._loaddate = _loaddate;
                        }
                        
                        public String getloadchg() {
                        	return this._loadchg;
                            }

                            // setting id
                            public void setlpadchg(String _loadchg) {
                        	this._loadchg = _loadchg;
                            }
}