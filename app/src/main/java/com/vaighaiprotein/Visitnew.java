package com.vaighaiprotein;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Visitnew extends Activity {
	private DbHelper mHelper;
	private SQLiteDatabase dataBase;
	private EditText millname,millstk,othparty1,othparty2,othparty3,othparty4,othparty5,othqty1,othqty2,othqty3,othqty4,othqty5,Areana,inputsearch,visit_date,othrate1,othrate2,othrate3,othrate4,othrate5,reason;
	private String party,partyskt,otherparty1,otherparty2,otherparty3,otherparty4,otherparty5,otherqty1,otherqty2,otherqty3,otherqty4,otherqty5,contact,areaname,visdate,othparrate1,othparrate2,othparrate3,othparrate4,othparrate5,othreason;
	private Spinner contmode;
	private String cantact_mode;
	private Button savebutton;
	private ListView listView,listparty;
	private EditText item_search;
	private String id;
	DbHelper database;
	private boolean isUpdate;
	private ArrayList<String> party_name = new ArrayList<String>();
	private ArrayList<String> ARE_ANAME = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private ArrayAdapter<String> areaadapter;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	static final int DATE_DIALOG_ID = 100;
	String s1;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.visitnew);
//	 setCurrentDate();
	id=getIntent().getExtras().getString("visit");	
	 ActionBar actionBar = getActionBar();
     actionBar.setHomeButtonEnabled(true);   
	millname=(EditText) findViewById(R.id.mill);
	millstk=(EditText) findViewById(R.id.stkqty);
	othparty1=(EditText) findViewById(R.id.othparty1);
	othparty2=(EditText) findViewById(R.id.othparty2);
	othparty3=(EditText) findViewById(R.id.othparty3);
	othparty4=(EditText) findViewById(R.id.othparty4);
	othparty5=(EditText) findViewById(R.id.othparty5);
	othqty1=(EditText) findViewById(R.id.othqty1);
	othqty2=(EditText) findViewById(R.id.othqty2);
	othqty3=(EditText) findViewById(R.id.othqty3);
	othqty4=(EditText) findViewById(R.id.othqty4);
	othqty5=(EditText) findViewById(R.id.othqty5);
	contmode=(Spinner) findViewById(R.id.spinner1);
	savebutton=(Button) findViewById(R.id.visit_save);
	Areana=(EditText) findViewById(R.id.areana);
	othrate1=(EditText) findViewById(R.id.rate1);
	othrate2=(EditText) findViewById(R.id.rate2);
	othrate3=(EditText) findViewById(R.id.rate3);
	othrate4=(EditText) findViewById(R.id.rate4);
	othrate5=(EditText) findViewById(R.id.rate5);
	reason=(EditText) findViewById(R.id.reason);
	visit_date=(EditText) findViewById(R.id.docdate);
	  cd = new ConnectionDetector(getApplicationContext());
	 mHelper=new DbHelper(this);
	 isUpdate=getIntent().getExtras().getBoolean("update");
	 
	 visit_date.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 showDialog(DATE_DIALOG_ID);
			
		}
	});
	 
	  
	
	savebutton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			party=millname.getText().toString().trim();
			partyskt=millstk.getText().toString();
			otherparty1=othparty1.getText().toString().trim();
			otherparty2=othparty2.getText().toString().trim();
			otherparty3=othparty3.getText().toString().trim();
			otherparty4=othparty4.getText().toString().trim();
			otherparty5=othparty5.getText().toString().trim();
			otherqty1=othqty1.getText().toString().trim();
			otherqty2=othqty2.getText().toString().trim();
			otherqty3=othqty3.getText().toString().trim();
			otherqty4=othqty4.getText().toString().trim();
			otherqty5=othqty5.getText().toString().trim();
			contact=contmode.getSelectedItem().toString();		
			areaname=Areana.getText().toString();
			visdate=visit_date.getText().toString();
			othparrate1=othrate1.getText().toString().trim();
			othparrate2=othrate2.getText().toString().trim();
			othparrate3=othrate3.getText().toString().trim();
			othparrate4=othrate4.getText().toString().trim();
			othparrate5=othrate5.getText().toString().trim();
			othreason=reason.getText().toString().trim();
			isInternetPresent = cd.isConnectingToInternet();
			if(party.length() >0 && areaname.length()>0 && isInternetPresent && visdate.length()>0 )
			{
			savedata();
			}
			else {
				AlertDialog.Builder alertBuilder=new AlertDialog.Builder(Visitnew.this);
				alertBuilder.setTitle("Invalid Data");
				alertBuilder.setMessage("Please, Enter valid data");
				alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
						
					}	
				});
				alertBuilder.create().show();				
			}
		}
	});
	
	
	  if(isUpdate)
      {
			 dataBase = mHelper.getReadableDatabase();

			String selectQuery = "SELECT  * FROM " + DbHelper.TABLE_visit + " WHERE "
					+ DbHelper.KEY_ID + " = " + id;
			
			Cursor c = dataBase.rawQuery(selectQuery, null);

			if (c.moveToFirst()) {
  			do {
  				millname.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_mill))));	    					    				
  				millstk.setText( ( c.getString( c.getColumnIndex(DbHelper.Visit_stock))));
  				othparty1.setText( c.getString( c.getColumnIndex(DbHelper.visit_othparty1)));
  				othparty2.setText((  c.getString( c.getColumnIndex(DbHelper.visit_othparty2))));
  				othparty3.setText((  c.getString( c.getColumnIndex(DbHelper.visit_othparty3))));
  				othparty4.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_othparty4))));
  				othparty5.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_othparty5))));
  				cantact_mode=( c.getString( c.getColumnIndex(DbHelper.visit_contact)));
  				othqty1.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_othqty1))));
  				othqty2.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_othqty2))));
  				othqty3.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_othqty3))));
  				othqty4.setText((c.getString( c.getColumnIndex(DbHelper.visit_othqty4))));
  				othqty5.setText((c.getString( c.getColumnIndex(DbHelper.visit_othqty5))));
  				Areana.setText((c.getString( c.getColumnIndex(DbHelper.visit_area))));
  				visit_date.setText((c.getString( c.getColumnIndex(DbHelper.visit_docdate))));
  				othrate1.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_rate1))));
  				othrate2.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_rate2))));
  				othrate3.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_rate3))));
  				othrate4.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_rate4))));
  				othrate5.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_rate5))));
  				reason.setText( ( c.getString( c.getColumnIndex(DbHelper.visit_reason))));
  				
 			} while (c.moveToNext());
  		}   		
  		
			String myString = cantact_mode; //the value you want the position for

			ArrayAdapter myAdap = (ArrayAdapter) contmode.getAdapter(); //cast to an ArrayAdapter

			int spinnerPosition = myAdap.getPosition(myString);

			//set the default according to value
			contmode.setSelection(spinnerPosition);
		
      }
	
	millname.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			searchparty();
			
		}
	});
	
	Areana.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			searcharea();
			
		}

	
	});

}

@Override
protected Dialog onCreateDialog(int id) {

    switch (id) {
    case DATE_DIALOG_ID:
       // set date picker as current date
    	   final Calendar c = Calendar.getInstance();
          int year = c.get(Calendar.YEAR);
          int month = c.get(Calendar.MONTH);
          int day = c.get(Calendar.DAY_OF_MONTH);
    	
   return new DatePickerDialog(this, datePickerListener, year, month,day);
    }
    return null;
}

private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
	   
	
    public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
    	selectedMonth=selectedMonth+1;
    	 String formattedMonth = "" + selectedMonth;
	        String formattedDayOfMonth = "" + selectedDay;
	        if(selectedMonth < 10){

	        	formattedMonth = ("0" + formattedMonth);
	        }
	        if(selectedDay < 10){

	        	formattedDayOfMonth = "0" + formattedDayOfMonth;
	        }
	        String docdate=((formattedDayOfMonth+"/"+formattedMonth+"/"+selectedYear));    	    
    	s1=formattedDayOfMonth+"/"+formattedMonth+"/"+selectedYear;
    	visit_date.setText(docdate);
		
		
    }
};    




protected void searcharea() {
	
	ARE_ANAME.clear();
    final Dialog dialog = new Dialog(Visitnew.this);
    dialog.setContentView(R.layout.area);
    dialog.setTitle("Select Area");
    listView = (ListView) dialog.findViewById(R.id.list);
    inputsearch =  (EditText) dialog.findViewById(R.id.editText1);      
    database=new DbHelper(Visitnew.this);    
  
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DbHelper.Table_Area , null);
		
		if (mCursor.moveToFirst()) {
			do {
				ARE_ANAME.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.Ar_Name)));
				
			} while (mCursor.moveToNext());
		}   		
		
	
    dialog.show();
    
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ARE_ANAME); 
 
    listView.setAdapter(adapter);        
    
    listView.setTextFilterEnabled(true);
    
    
   listView.setOnItemClickListener(new OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            int itemPosition = position;
            String itemValue = (String) listView
                    .getItemAtPosition(position);
            Areana.setText(itemValue);
            dialog.cancel();

        }

    });
    
   inputsearch.addTextChangedListener(new TextWatcher() {
	   
       @Override
       public void onTextChanged(CharSequence s, int start, int before,
               int count) {
           adapter.getFilter().filter(s.toString());
       }

       @Override
       public void beforeTextChanged(CharSequence s, int start, int count,
               int after) {
       }

       @Override
       public void afterTextChanged(Editable s) {
       }
   });
}
public void searchparty() {

	party_name.clear();
	areaname=Areana.getText().toString();
    final Dialog dialog = new Dialog(Visitnew.this);
    dialog.setContentView(R.layout.party);
    dialog.setTitle("Select Party");
    listparty = (ListView) dialog.findViewById(R.id.partylist);
    item_search =  (EditText) dialog.findViewById(R.id.partysearch);      
    database=new DbHelper(Visitnew.this);    
 
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DbHelper.Table_Party + " WHERE "+DbHelper.Ar_Name+" IN ('"+areaname.toString() +"')", null);
		
		if (mCursor.moveToFirst()) {
			do {
				party_name.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.party)));
				
			} while (mCursor.moveToNext());
		}   		
		
	
    dialog.show();
    
    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, party_name); 
 
    listparty.setAdapter(adapter);        
    
    listparty.setTextFilterEnabled(true);
    
    
   listparty.setOnItemClickListener(new OnItemClickListener() {

        public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
            int itemPosition = position;
            String itemValue = (String) listparty
                    .getItemAtPosition(position);
            millname.setText(itemValue);
            dialog.cancel();

        }

    });
    
   item_search.addTextChangedListener(new TextWatcher() {
	   
       @Override
       public void onTextChanged(CharSequence s, int start, int before,
               int count) {
           adapter.getFilter().filter(s.toString());
       }

       @Override
       public void beforeTextChanged(CharSequence s, int start, int count,
               int after) {
       }

       @Override
       public void afterTextChanged(Editable s) {
       }
   });

	

	

	
}
public void savedata() {
	
	 TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String IMEINO=telephonyManager.getDeviceId();
	
	dataBase=mHelper.getWritableDatabase();
	ContentValues values=new ContentValues();
	
	values.put(DbHelper.visit_mill,party);
	values.put(DbHelper.Visit_stock,partyskt );
	values.put(DbHelper.visit_othparty1,otherparty1 );
	values.put(DbHelper.visit_othparty2,otherparty2 );
	values.put(DbHelper.visit_othparty3,otherparty3 );
	values.put(DbHelper.visit_othparty4,otherparty4 );
	values.put(DbHelper.visit_othparty5,otherparty5 );
	values.put(DbHelper.visit_othqty1,otherqty1 );
	values.put(DbHelper.visit_othqty2,otherqty2 );
	values.put(DbHelper.visit_othqty3,otherqty3 );
	values.put(DbHelper.visit_othqty4,otherqty4 );
	values.put(DbHelper.visit_othqty5,otherqty5 );
	values.put(DbHelper.visit_contact,contact );
	values.put(DbHelper.visit_area,areaname);
	values.put(DbHelper.visit_docdate,visdate);
	values.put(DbHelper.visit_rate1,othparrate1);
	values.put(DbHelper.visit_rate2,othparrate2);
	values.put(DbHelper.visit_rate3,othparrate3);
	values.put(DbHelper.visit_rate4,othparrate4);
	values.put(DbHelper.visit_rate5,othparrate5);
	values.put(DbHelper.visit_reason,othreason);
	areaname=areaname.replace(" ", "%20");
	party=party.replace(" ", "%20");	
	otherparty1=otherparty1.replace(" ", "%20");
	otherparty2=otherparty2.replace(" ", "%20");
	otherparty3=otherparty3.replace(" ", "%20");
	otherparty4=otherparty4.replace(" ", "%20");
	otherparty5=otherparty5.replace(" ", "%20");
	othreason=othreason.replace(" ", "%20");
	
	
	
	//System.out.println("");
	if(isUpdate)
	{    
		//update database with new data 
	dataBase.update(DbHelper.TABLE_visit, values, DbHelper.KEY_ID+"="+id, null);
	
	InputStream webs = null;
	
	String formattedMonth = null;

	
	try
	{
		HttpClient httpclient = new DefaultHttpClient();
		String surl = "http://223.30.82.99:8080/protein/updvisitor.php?PARTYSTOCK="+partyskt+"&OTHPARTY1="+otherparty1+"&OTHPARTY2="+otherparty2+"&OTHPARTY3="+otherparty3+"&OTHPARTY4="+otherparty4+"&OTHPARTY5="+otherparty5+"&OTHQTY1="+otherqty1+"&OTHQTY2="+otherqty2+"&OTHQTY3="+otherqty3+"&OTHQTY4="+otherqty4+"&OTHQTY5="+otherqty5+"&CNTMODE="+contact+"&IMEINO="+IMEINO+"&MILLNAME="+party+"&VID="+id+"&AREA="+areaname+"&VISDATE="+visdate+"&othrate1="+othparrate1+"&othrate2="+othparrate2+"&othrate3="+othparrate3+"&othrate4="+othparrate4+"&othrate5="+othparrate5+"&othreason="+othreason;				
		HttpPost httppost = new HttpPost(surl);			
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		webs = entity.getContent();	
		
	}
	catch (Exception e)
	{		 	  
   
	}
	
	finish();
	}
	else
	{
		//insert data into database
		dataBase.insert(DbHelper.TABLE_visit, null, values);
		
		long rowcnt = 	(mHelper.VISIT_getrowid());
		InputStream webs = null;	
	
	
		
		
		 //String imeino="100";
		 
			try
			{
				HttpClient httpclient = new DefaultHttpClient();
				String surl = "http://223.30.82.99:8080/protein/VISITOR.php?PARTYSTOCK="+partyskt+"&OTHPARTY1="+otherparty1+"&OTHPARTY2="+otherparty2+"&OTHPARTY3="+otherparty3+"&OTHPARTY4="+otherparty4+"&OTHPARTY5="+otherparty5+"&OTHQTY1="+otherqty1+"&OTHQTY2="+otherqty2+"&OTHQTY3="+otherqty3+"&OTHQTY4="+otherqty4+"&OTHQTY5="+otherqty5+"&CNTMODE="+contact+"&IMEINO="+IMEINO+"&MILLNAME="+party+"&VID="+rowcnt+"&AREA="+areaname+"&VISDATE="+visdate+"&othrate1="+othparrate1+"&othrate2="+othparrate2+"&othrate3="+othparrate3+"&othrate4="+othparrate4+"&othrate5="+othparrate5+"&othreason="+othreason;				
				HttpPost httppost = new HttpPost(surl);			
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				webs = entity.getContent();	
				
			}
			catch (Exception e)
			{		 	  
		   
			}
	dataBase.close();
	finish();
	
	
}



}
}