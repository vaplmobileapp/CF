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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class visit extends Activity {
	private DbHelper mHelper;
	private SQLiteDatabase dataBase;

	private ArrayList<String> Lotid = new ArrayList<String>();
	private ArrayList<String> partyname = new ArrayList<String>();
	private ArrayList<String> purtype = new ArrayList<String>();		
	private ArrayList<String> Qty = new ArrayList<String>();
	private ArrayList<String> Rate = new ArrayList<String>();
	
	private ListView userList;
	private AlertDialog.Builder build;	
	String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visit);
		 ActionBar actionBar = getActionBar();
	        actionBar.setHomeButtonEnabled(true);   
		userList = (ListView) findViewById(R.id.visit_list);		
		mHelper = new DbHelper(this);	
	
		userList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				Intent i = new Intent(getApplicationContext(),
						Visitnew.class);					
				i.putExtra("visit", Lotid.get(arg2).toString() );				
				i.putExtra("update", true);
				startActivity(i);

			}
		});
		
		userList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {

				build = new AlertDialog.Builder(visit.this);
				build.setTitle("Delete " + partyname.get(arg2) + " ");
				build.setMessage("Do you want to delete ?");
				build.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								Toast.makeText(
										getApplicationContext(),
										partyname.get(arg2) + " "											
												+ " is deleted.", 3000).show();

								dataBase.delete(
										DbHelper.TABLE_visit,
										DbHelper.KEY_ID + "="
												+ Lotid.get(arg2), null);
								String vid =Lotid.get(arg2).toString();
								 TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
									String IMEINO=telephonyManager.getDeviceId();
								
									InputStream webs = null;	
									try
									{
										HttpClient httpclient = new DefaultHttpClient();
										String surl = "http://223.30.82.99:8080/PROTEIN/delvisitor.php?VID="+vid+"&IMEINO="+IMEINO;								
										HttpPost httppost = new HttpPost(surl);			
										HttpResponse response = httpclient.execute(httppost);
										HttpEntity entity = response.getEntity();
										webs = entity.getContent();	
										
									}
									catch (Exception e)
									{		 	  
								   
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
		});
			
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
				+ DbHelper.TABLE_visit + " WHERE "+DbHelper.visit_docdate+" IN ('"+Dat_str+"')" , null);
		Lotid.clear();
		Qty.clear();
		partyname.clear();
		purtype.clear();
		
		if (mCursor.moveToFirst()) {
			do {
				Lotid.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ID)));
				partyname.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.visit_mill)));
				Qty.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.Visit_stock)));
				Rate.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.visit_contact)));
				
			} while (mCursor.moveToNext());
		}
		Displaylot disadpt = new Displaylot(this,Lotid,  partyname,Qty,Rate);		
		userList.setAdapter(disadpt);
		mCursor.close();
	}
	
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.visit, menu);
	return super.onCreateOptionsMenu(menu);
	
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case R.id.Visited:
		Intent it = new Intent(getApplicationContext(),
				Visitnew.class);
		it.putExtra("update", false);
		startActivity(it);
		break;

	default:
		break;
	}
	
	return super.onOptionsItemSelected(item);
	
}
	
}
