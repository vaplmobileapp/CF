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
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Containerinw extends Activity {
	
	EditText CONTDOCDATE,CONTRACTNO,CONSIGNEE,PRODUCT,PACKINGTYPE,CONTAINERNO,contqty,inputsearch;
	String TODAYDAT,ordno,eitem,cdate,conno,conconsignee,conproduct,conptype,concontainer,conconqty,id,sons,ctype;
	Spinner conttype;
	private DbHelper mHelper;
	private SQLiteDatabase dataBase;
	static final int DATE_DIALOG_ID = 100;
	private ListView listView;
	DbHelper database;
	String status="Pending";
			private boolean isUpdate;
	private ArrayList<String> ARE_ANAME = new ArrayList<String>();
	private String s1;
	Button btn;
	InputStream webs = null;	
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.containerinw);
		
		ActionBar actionBar = getActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
	        
	    CONTDOCDATE = (EditText) findViewById(R.id.docdate);
	    CONTRACTNO = (EditText) findViewById(R.id.contno);
	    CONSIGNEE = (EditText) findViewById(R.id.consignee);
	    PRODUCT = (EditText) findViewById(R.id.Product);
	    PACKINGTYPE = (EditText) findViewById(R.id.ptype);
	    CONTAINERNO = (EditText) findViewById(R.id.containerno);
	    conttype = (Spinner) findViewById(R.id.contytpe);	 
	    contqty = (EditText) findViewById(R.id.tarewt);
	    btn=(Button) findViewById(R.id.button1);
	    isUpdate=getIntent().getExtras().getBoolean("update");
	    id=getIntent().getExtras().getString("updval");
	    cd = new ConnectionDetector(getApplicationContext());
	    
	    mHelper=new DbHelper(this);
	    
	    if(isUpdate)
        {
	    	 database=new DbHelper(Containerinw.this);   
			 dataBase = mHelper.getReadableDatabase();

			String selectQuery = "SELECT  * FROM " + DbHelper.Table_cont + " WHERE "
					+ DbHelper.Lot_ID + " = " + id;
			
			Cursor c = dataBase.rawQuery(selectQuery, null);			


			if (c.moveToFirst()) {
			do {
				CONTRACTNO.setText( ( c.getString( c.getColumnIndex(DbHelper.CONT_CONTNO))));
				CONTDOCDATE.setText( ( c.getString( c.getColumnIndex(DbHelper.CONT_docdate))));
				CONSIGNEE.setText( ( c.getString( c.getColumnIndex(DbHelper.cont_consignee)))); 				
				PRODUCT.setText( ( c.getString( c.getColumnIndex(DbHelper.cont_product))));
				sons= ( c.getString( c.getColumnIndex(DbHelper.ecptype)));	    
				CONTAINERNO.setText( ( c.getString( c.getColumnIndex(DbHelper.CONT_CONTNO))));
				PACKINGTYPE.setText( ( c.getString( c.getColumnIndex(DbHelper.cont_packtype))));
				contqty.setText( ( c.getString( c.getColumnIndex(DbHelper.contqty))));
							
			} while (c.moveToNext());
		}   		
		
		
			String myString = sons; //the value you want the position for

			ArrayAdapter myAdap = (ArrayAdapter) conttype.getAdapter(); //cast to an ArrayAdapter

			int spinnerPosition = myAdap.getPosition(myString);

			//set the default according to value
			conttype.setSelection(spinnerPosition);
       	 
        }
	        
	        Time today = new Time(Time.getCurrentTimezone());
	         today.setToNow();
	         
	         int day = today.monthDay;
	         int mon = today.month+1;
	         String selMonth = null,selDay = null;
	         
	         CONTDOCDATE.setInputType(InputType.TYPE_NULL);
	         CONTRACTNO.setInputType(InputType.TYPE_NULL);
	         CONSIGNEE.setInputType(InputType.TYPE_NULL);
	         PRODUCT.setInputType(InputType.TYPE_NULL);
	         PACKINGTYPE.setInputType(InputType.TYPE_NULL);
	         
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
	       
	       CONTDOCDATE.setText(TODAYDAT);
	        
	        
	        CONTDOCDATE.setOnClickListener(new OnClickListener() {
	    		
	    		@Override
	    		public void onClick(View v) {
	    			 showDialog(DATE_DIALOG_ID);
	    			
	    		}
	    	});
	        
	        CONTRACTNO.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					searchcontno();
					
				}
			});
	        
	      
	        PRODUCT.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					searchproduct();
					
				}
			});
	        
	        btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					conno = CONTRACTNO.getText().toString().trim();		
					cdate=CONTDOCDATE.getText().toString().trim();		
					conconsignee=CONSIGNEE.getText().toString().trim();
					conproduct=PRODUCT.getText().toString().trim();
					conptype=PACKINGTYPE.getText().toString().trim();
					concontainer=CONTAINERNO.getText().toString().trim();
					conconqty=contqty.getText().toString().trim();
					ctype=conttype.getSelectedItem().toString().trim();
				
					isInternetPresent = cd.isConnectingToInternet();				
					if(conno.length()>0  && cdate.length()>0 && conconsignee.length() >0 && conproduct.length()>0 && conptype.length()>0 && concontainer.length()>0 && conconqty.length()>0 && isInternetPresent && ctype.length()>0 )
					{
					Savedata();
					}
					else {
						AlertDialog.Builder alertBuilder=new AlertDialog.Builder(Containerinw.this);
						alertBuilder.setTitle("Invalid Data");
						alertBuilder.setMessage("Please, Enter valid data");
						alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
								
							}
						});
						alertBuilder.create().show();
					}
					
					dataBase.close();
					finish();
				}
			});
	        
	}

	protected void Savedata() {
		
		dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String IMEINO=telephonyManager.getDeviceId();
		
		values.put(DbHelper.CONT_CONTNO,conno);
		values.put(DbHelper.CONT_docdate,cdate );
		values.put(DbHelper.cont_consignee,conconsignee );
		values.put(DbHelper.cont_product,conproduct );
		values.put(DbHelper.ecptype,ctype );
		values.put(DbHelper.cont_containerno,concontainer );
		values.put(DbHelper.contqty,conconqty );
		values.put(DbHelper.contSTATUS ,status );
		values.put(DbHelper.cont_packtype ,conptype );
		
				
		if(isUpdate)
		{			
			
		dataBase.update(DbHelper.Table_cont, values, DbHelper.Lot_ID+"="+id, null);
		
		}
		else {		
		
		dataBase.insert(DbHelper.Table_cont, null, values);
		
		try
		{			
			conconsignee=conconsignee.replace(" ", "%20");
			conproduct=conproduct.replace(" ", "%20");
			concontainer=concontainer.replace(" ", "%20");
			conptype=conptype.replace(" ", "%20");			
			
			HttpClient httpclient = new DefaultHttpClient();
			String surl = "http://223.30.82.99:8080/worldwide/CFSDIPADD.php?DOCDATE="+cdate+"&CONTNO="+conno+"&CONSIGNEE="+conconsignee+"&PRODUCT="+conproduct+"&PACKTYPE="+conptype+"&IMEINO="+IMEINO+"&QTY="+conconqty+"&Containerno="+concontainer;				
			HttpPost httppost = new HttpPost(surl);			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			webs = entity.getContent();	
			
		}
		catch (Exception e)
		{		 	  
	   
		}
			
		}
		
		database.close();
		finish();
		
	}

	protected void searchproduct() {
		
		ARE_ANAME.clear();
        final Dialog dialog = new Dialog(Containerinw.this);
        dialog.setContentView(R.layout.area);
        dialog.setTitle("Select Item");
        listView = (ListView) dialog.findViewById(R.id.list);
        inputsearch =  (EditText) dialog.findViewById(R.id.editText1);      
        database=new DbHelper(Containerinw.this);    
        //ordno=pono.getText().toString().trim();
    		dataBase = mHelper.getWritableDatabase();
    		Cursor mCursor = dataBase.rawQuery("SELECT DISTINCT "+DbHelper.ecproduct+" FROM "
    		    	   + DbHelper.Table_Contract +" WHERE "+ DbHelper.ecno + " IN ('"+ordno+"')"  , null);   		
    		    	    		   		
    	    		
    		if (mCursor.moveToFirst()) {
    			do {
    				ARE_ANAME.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.ecproduct)));
   				
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
                PRODUCT.setText(itemValue);                
                dialog.cancel();
                Setptype();
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

	protected void Setptype() {
		
		  database=new DbHelper(Containerinw.this);    
	        ordno=CONTRACTNO.getText().toString().trim();
	        eitem=PRODUCT.getText().toString().trim();
	    		dataBase = mHelper.getWritableDatabase();
	    		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
	    	   + DbHelper.Table_Contract +" WHERE "+ DbHelper.ecno + " IN ('"+ordno+"') AND  "+DbHelper.ecproduct +" IN ('"+eitem+"')" , null);   		
	    	    		
	    		if (mCursor.moveToFirst()) {
	    			do {
	    			
	    				PACKINGTYPE.setText((mCursor.getString(mCursor.getColumnIndex(DbHelper.ecptype))));
	   				
	   			} while (mCursor.moveToNext());
	    		}   		
		
	
		
	}

	protected void searchcontno() {
		
		ARE_ANAME.clear();
        final Dialog dialog = new Dialog(Containerinw.this);
        dialog.setContentView(R.layout.area);
        dialog.setTitle("Select Contract");
        listView = (ListView) dialog.findViewById(R.id.list);
        inputsearch =  (EditText) dialog.findViewById(R.id.editText1);      
        database=new DbHelper(Containerinw.this);    
        //ordno=pono.getText().toString().trim();
    		dataBase = mHelper.getWritableDatabase();
    		Cursor mCursor = dataBase.rawQuery("SELECT DISTINCT "+ DbHelper.ecno+ " FROM "
    	   + DbHelper.Table_Contract   , null);   		
    	    		
    		if (mCursor.moveToFirst()) {
    			do {
    				ARE_ANAME.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.ecno)));
   				
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
                CONTRACTNO.setText(itemValue);                
                dialog.cancel();
                Setconsignee();
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

	protected void Setconsignee() {
		
		  database=new DbHelper(Containerinw.this);    
	        ordno=CONTRACTNO.getText().toString().trim();
	    		dataBase = mHelper.getWritableDatabase();
	    		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
	    	   + DbHelper.Table_Contract +" WHERE "+ DbHelper.ecno + " IN ('"+ordno+"')"  , null);   		
	    	    		
	    		if (mCursor.moveToFirst()) {
	    			do {
	    			
	    				CONSIGNEE.setText((mCursor.getString(mCursor.getColumnIndex(DbHelper.ecconsignee))));
	   				
	   			} while (mCursor.moveToNext());
	    		}   		
		
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
	    	CONTDOCDATE.setText(docdate);
			
			
	    }
	};    
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
	
	switch (item.getItemId()) {
	case android.R.id.home:
		
		Containerinw.this.onBackPressed();
		break;
					
	default:
		break;
	}
	
		return super.onOptionsItemSelected(item);
	}

}
