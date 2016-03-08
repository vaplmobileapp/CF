package com.vaighaiprotein;


import java.util.ArrayList;
import java.util.Calendar;

import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayActivity extends Activity {
	
	private DbHelper mHelper;
	DbHelper database;
	private SQLiteDatabase dataBase;

	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> disp_lorryno = new ArrayList<String>();
	private ArrayList<String> disp_pono = new ArrayList<String>();
	private ArrayList<String> Disp_itemmasterid = new ArrayList<String>();
	private ArrayList<String> Disp_itemid = new ArrayList<String>();
	private ArrayList<String> Disp_partymastid = new ArrayList<String>();
	private ArrayList<String> Disp_partyid = new ArrayList<String>();
	private ArrayList<String> Disp_category = new ArrayList<String>();
	private ArrayList<String> DISP_QTY = new ArrayList<String>();
	private ArrayList<String> Disp_IMEINO = new ArrayList<String>();
	private ArrayList<String> Disp_DISPID = new ArrayList<String>();	
	private ListView userList;
	private AlertDialog.Builder build;
	private String subcate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_activity);		
		subcate=getIntent().getExtras().getString("subcat");
		  ActionBar actionBar = getActionBar();
	        actionBar.setHomeButtonEnabled(true);
	        actionBar.setDisplayHomeAsUpEnabled(true);
		
		userList = (ListView) findViewById(R.id.List);
		mHelper = new DbHelper(this);
	
		userList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String val=userId.get(arg2);
				
				Intent i = new Intent(getApplicationContext(),
						AddActivity.class);
				
				i.putExtra("ID", val);	
				i.putExtra("subcat", subcate);
				
				startActivity(i);

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
				+ DbHelper.TABLE_NAME + " WHERE "+ DbHelper.LOT_STAT+"='PENDING'" , null);
		
		userId.clear();
		disp_lorryno.clear();
		disp_pono.clear();
		Disp_itemmasterid.clear();
		Disp_itemid.clear();
		Disp_partymastid.clear();
		Disp_partyid.clear();
		Disp_category.clear();
		DISP_QTY.clear();
		Disp_IMEINO.clear();
		Disp_DISPID.clear();		
		
		if (mCursor.moveToFirst()) {
			do {
				userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_SINO)));
				disp_lorryno.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_lorryno)));
				disp_pono.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_PONO)));
				Disp_itemmasterid.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.KEY_ITEMMASTERID) ));
				Disp_itemid.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.KEY_ITEMID) ));
				Disp_partymastid.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.KEY_PARTYMASTID) ));
				Disp_partyid.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.KEY_PARTYID) ));
				Disp_category.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.KEY_CATEGORY) ));
				DISP_QTY.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.KEY_DISPQTY) ));
				Disp_IMEINO.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.KEY_IMEINO) ));
				Disp_DISPID.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.KEY_DISPID) ));
				
				
			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(DisplayActivity.this,userId,  disp_lorryno,disp_pono,Disp_itemid,DISP_QTY);
		
		
		
		mCursor.close();
		dataBase.close();
		
		userList.setAdapter(disadpt);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
	
	switch (item.getItemId()) {
	case android.R.id.home:
		
		DisplayActivity.this.onBackPressed();
		break;

	default:
		break;
	}
	
		return super.onOptionsItemSelected(item);
	}
	
	
}
