package com.vaighaiprotein;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Pendpallet extends Activity {
	
	private ListView ls;
	private DbHelper mHelper;
	String itemValue;
	private ArrayList<String> PENSI = new ArrayList<String>();
	private ArrayList<String> PENITEM = new ArrayList<String>();
	private ArrayList<String> LORRYN = new ArrayList<String>();
	private ArrayList<String> PONO = new ArrayList<String>();
	private ArrayList<String> CFSRECEIPT = new ArrayList<String>();
	private ArrayList<String> REQTY = new ArrayList<String>();
	//private ArrayList<String> PACKITYPE = new ArrayList<String>();
	DbHelper database;
	private SQLiteDatabase dataBase;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.palletlist);
		ActionBar actionBar = getActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
		ls = (ListView) findViewById(R.id.listView1);
		
		mHelper = new DbHelper(this);
		
    ls.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			itemValue = CFSRECEIPT.get(arg2);
			//Toast.makeText(getApplicationContext(), itemValue.toString(), 3000).show();
			Intent it = new Intent(Pendpallet.this,Pallet.class);
			it.putExtra("item", itemValue);
			startActivity(it);
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
        
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DbHelper.Table_penpall , null);
		
		PENSI.clear();
		LORRYN.clear();
		PONO.clear();
		REQTY.clear();
		PENITEM.clear();
		
		
		if (mCursor.moveToFirst()) {
			do {
				PENSI.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.PENSINO)));
				LORRYN.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.PENLORRYNO)));
				PONO.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.PENPONO)));
				REQTY.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.PENRECEIPTQTY) ));
				PENITEM.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.PENITEM) ));
				CFSRECEIPT.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.Pal_CFSREC) ));
				
				
				
			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(Pendpallet.this,PENSI,LORRYN,PONO,PENITEM,REQTY);
		
		
		
		mCursor.close();
		dataBase.close();
		
		ls.setAdapter(disadpt);
			
		
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
		
		switch (item.getItemId()) {
		case android.R.id.home:
			
			Pendpallet.this.onBackPressed();
			break;

		default:
			break;
		}
		
			return super.onOptionsItemSelected(item);
		}
	
}
