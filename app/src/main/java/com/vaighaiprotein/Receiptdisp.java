package com.vaighaiprotein;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Receiptdisp extends Activity {
	
	private DbHelper mHelper;
	private SQLiteDatabase dataBase;
	private AlertDialog.Builder build;	
	String id;
	Boolean isInternetPresent = false;
	private ArrayList<String> PENSINO = new ArrayList<String>();
	private ArrayList<String> PENPONO = new ArrayList<String>();
	private ArrayList<String> PENLORRYNO = new ArrayList<String>();		
	private ArrayList<String> PENRQTY = new ArrayList<String>();
	private ArrayList<String> PENRECEIPTQTY = new ArrayList<String>();
	private ConnectionDetector cd;
	
	ListView LS;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receiptdisp);		
		
		ActionBar actionBar = getActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);		
		mHelper = new DbHelper(this);		
		LS = (ListView) findViewById(R.id.listView1);
		
		 cd = new ConnectionDetector(getApplicationContext());
		   isInternetPresent = cd.isConnectingToInternet();
		
		LS.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent i = new Intent(getApplicationContext(),AddActivity.class);
						
				
				i.putExtra("ID", PENSINO.get(arg2).toString());
				i.putExtra("subcat","ALL");
				i.putExtra("update", true);
				
				startActivity(i);
				
			}
		});
		
		LS.setOnItemLongClickListener(new OnItemLongClickListener() {			
			
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				build = new AlertDialog.Builder(Receiptdisp.this);
				
				build.setTitle("Delete " + PENSINO.get(arg2) + " ");
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

						
							public void onClick(DialogInterface dialog,
									int which) {
								
								if(isInternetPresent)
								{

								Toast.makeText(
										getApplicationContext(),
										PENLORRYNO.get(arg2) + " "											
												+ " is deleted.", 3000).show();
								
								String vid =PENSINO.get(arg2).toString();
								 TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
									String IMEINO=telephonyManager.getDeviceId();


								dataBase.delete(
										DbHelper.Table_lot,
										DbHelper.Lot_ID + "="
												+ PENSINO.get(arg2), null);
							
									
									InputStream webs = null;	
									try
									{
										HttpClient httpclient = new DefaultHttpClient();
										String surl = "http://223.30.82.99:8080/worldwide/delcfsrec.php?imeino="+IMEINO+"&TRANSID="+vid;								
										HttpPost httppost = new HttpPost(surl);			
										HttpResponse response = httpclient.execute(httppost);
										HttpEntity entity = response.getEntity();
										webs = entity.getContent();	
										
									}
									catch (Exception e)
									{		 	  
								   
									}
								}
								else
								{
									Toast.makeText(getApplicationContext(), "Network Not Available", 10000).show();
									
								}
								displayData();
								dialog.cancel();
								
								
								
							}
						});

				build.setNegativeButton("No",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = build.create();
				alert.show();

				return true;
			}

			
			
		} );
	}
	

	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}
	
	private void displayData() {
		
		String formattedMonth = null;		
		
		 final Calendar c = Calendar.getInstance();
		
         int year = c.get(Calendar.YEAR);
         int month = c.get(Calendar.MONTH);
         int day = c.get(Calendar.DAY_OF_MONTH);
         String formattedDayOfMonth = "" ;
         month=month+1;
         if(month < 10){

	        	formattedMonth = ("0" + month);
	        }
         else {
        	 formattedMonth = (""+ month);
			
		}
	        if(day < 10){

	        	formattedDayOfMonth = "0" + day;
	        }
	        else {
	        	formattedDayOfMonth = "" + day;
			}
	        
         
         String Dat_str = formattedDayOfMonth+"/"+formattedMonth+"/"+year;
		
         
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DbHelper.Table_lot +" WHERE "+  DbHelper.LOT_ENTRYDATE+ " IN('"+Dat_str+"')"  , null);
		 //+ " WHERE "+DbHelper.visit_docdate+" IN ('"+Dat_str+"')"
		
		PENSINO.clear();
		PENPONO.clear();
		PENLORRYNO.clear();
		PENRQTY.clear();
		
		if (mCursor.moveToFirst()) {
			do {
				PENSINO.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.Lot_ID)));
				PENPONO.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.LOT_PONO)));
				PENLORRYNO.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.LOT_LORRYNO)));
				PENRQTY.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.LOT_TOTQTY)));
				
			} while (mCursor.moveToNext());
		}
		Displaylot disadpt = new Displaylot(this,PENSINO,PENPONO,PENLORRYNO,PENRQTY);		
		LS.setAdapter(disadpt);
		mCursor.close();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lorry, menu);
		return true;
		}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {		
		
		case R.id.Load:
			Intent i = new Intent(getApplicationContext(),Cfsmain.class);
			startActivity(i);
			break;
			
		case android.R.id.home:
			Receiptdisp.this.onBackPressed();
			break;
			
			default:
				
			break;
		}
		return super.onOptionsItemSelected(item);
	}


}
