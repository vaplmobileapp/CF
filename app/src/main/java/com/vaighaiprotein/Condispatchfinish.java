package com.vaighaiprotein;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

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
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Condispatchfinish extends Activity {
	
	
	
	EditText inputsearch,docdat,closingqty;
	Button btn;
	String pallno,TODAYDAT,s1,palldt,id,contqty;
	private ArrayList<String> ARE_ANAME = new ArrayList<String>();
	private ListView listView;
	DbHelper database;
	private SQLiteDatabase dataBase;
	private DbHelper mHelper;
	private ArrayAdapter<String> adapter;
	static final int DATE_DIALOG_ID = 100;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.condispatchfin);

		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		btn = (Button) findViewById(R.id.btn);
		docdat =  (EditText) findViewById(R.id.docdate);
		closingqty = (EditText) findViewById(R.id.clsqty);
		
		id=getIntent().getExtras().getString("ID");
		cd = new ConnectionDetector(getApplicationContext());
		 mHelper=new DbHelper(this);
		 
		  Time today = new Time(Time.getCurrentTimezone());
	         today.setToNow();
	         
	         int day = today.monthDay;
	         int mon = today.month+1;
	         String selMonth = null,selDay = null;
	         
	         docdat.setInputType(InputType.TYPE_NULL);
	         
	         
	         if(mon < 10){

	         selMonth = ("0" + mon);
	         }
	         else {
	         	selMonth=String.valueOf( mon) ;
	 		}
	         if(day < 10){

	         selDay = "0" + day;
	         }
	         else {
	         	selDay=String.valueOf( day) ;
	 		}
	         
	       TODAYDAT =  selDay + "/"+selMonth +"/"+today.year;             // Day of the month (1-31)
	          
	       String date_format=today.year+"."+selMonth+"."+selDay;
	       
	       docdat.setText(TODAYDAT);
		
	
	
	docdat.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 showDialog(DATE_DIALOG_ID);
			
		}
	});
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			//	pallno = ed.getText().toString().trim();
				palldt = docdat.getText().toString().trim();
				contqty = closingqty.getText().toString().trim();
				
				isInternetPresent = cd.isConnectingToInternet();
				
			  if(docdat.length()>0 && isInternetPresent)
			  {
				
				Savedata();
			  }
			  else {

					AlertDialog.Builder alertBuilder=new AlertDialog.Builder(Condispatchfinish.this);
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
	}

	protected void Savedata() {
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String IMEINO=telephonyManager.getDeviceId();	
		
	  		
		//dataBase=mHelper.getWritableDatabase();
		//ContentValues values=new ContentValues();
		
		//values.put(DbHelper.pen_contID,pallno);
		//values.put(DbHelper.paldocdate,palldt );
		//values.put(DbHelper.Pal_CFSREC,id);
		//values.put(DbHelper.Pal_Entdate,TODAYDAT);
		
		
		
	//	dataBase.update(DbHelper.Table_PENCONT, values, DbHelper.pen_contID+"="+id, null);
		
		//dataBase.insert(DbHelper.Table_pall_dis, null, values);		
		
		
		String formattedMonth = null;
		InputStream webs = null;	
		
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			
			String surl ="http://223.30.82.99:8080/worldwide/findispupd.php?MOBID="+id+"&IMEINO="+IMEINO+"&DOCDATE="+palldt+"&CONTCLSQTY="+contqty;
			HttpPost httppost = new HttpPost(surl);			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			webs = entity.getContent();	
			
		}
		catch (Exception e)
		{		 	  
	   
		}	
		//dataBase.close();
		finish();
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
        	docdat.setText(docdate);
    		
    		
        }
    };    
    
	


	
}
