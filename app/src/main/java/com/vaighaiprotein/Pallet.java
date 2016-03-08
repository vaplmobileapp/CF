package com.vaighaiprotein;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class Pallet extends Activity {
	
	Spinner sp;
	Editable eqty;
	Button btn;
	int j;
	String bas,ITEMNA;
	List<String> spinnerArray =  new ArrayList<String>();
	private DbHelper mHelper;
	DbHelper database;
	private SQLiteDatabase dataBase;
	ListView userList;
	private ArrayList<String> palletno = new ArrayList<String>();
	private ArrayList<String> paldat = new ArrayList<String>();
	private ArrayList<String> status = new ArrayList<String>();
	private ArrayList<String> Qty = new ArrayList<String>();
	private ArrayList<String> Pal_CFSREC = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pallet);
		bas=getIntent().getExtras().getString("item");
		ITEMNA=getIntent().getExtras().getString("item");
		
		ActionBar actionBar = getActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);	 
	    userList=(ListView) findViewById(R.id.listView1);
	    mHelper = new DbHelper(this);
	    
	    userList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				Intent I = new Intent(Pallet.this,Pallatization.class);
				I.putExtra("update", true);			
				I.putExtra("PALNO", palletno.get(arg2));
				startActivity(I);
			}
		});
	
	}
	
	@Override
	protected void onResume() {
		displayData();
		super.onResume();
	}
	
	private void displayData() {
		
		 database=new DbHelper(this);  
       //Toast.makeText(getApplicationContext(), Dat_str.toString(),Toast.LENGTH_LONG).show();
        
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DbHelper.Table_pallet +" WHERE "+DbHelper.Pal_CFSREC +"="+ITEMNA , null);
		
		palletno.clear();
		paldat.clear();
		status.clear();
		Qty.clear();
		Pal_CFSREC.clear();
				
		
		if (mCursor.moveToFirst()) {
			do {
				palletno.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.Pal_no)));
				paldat.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.Pal_Entdate)));
				status.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.Pal_Status)));
				Qty.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.Pal_Qty) ));
				Pal_CFSREC.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.Pal_CFSREC) ));				
				
			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(Pallet.this,palletno,  paldat,status,Qty,Pal_CFSREC);
		
		
		
		mCursor.close();
		dataBase.close();
		
		userList.setAdapter(disadpt);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lorry, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		
		switch (item.getItemId()) {
		case android.R.id.home:
			
			Pallet.this.onBackPressed();
			break;
			
		case R.id.Load:
			Intent it = new Intent(Pallet.this,Pallatization.class);
			 it.putExtra("cfsbas", bas);
			startActivity(it);

		default:
			break;
		}
		
			return super.onOptionsItemSelected(item);
		}

}
