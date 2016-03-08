package com.vaighaiprotein;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Condispatch extends Activity {
	
	private DbHelper mHelper;
	DbHelper database;
	private SQLiteDatabase dataBase;

	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> disp_contno = new ArrayList<String>();
	private ArrayList<String> disp_packtype = new ArrayList<String>();
	private ArrayList<String> Disp_Qty = new ArrayList<String>();
	private ArrayList<String> Disp_docdate = new ArrayList<String>();
	
	private ListView userList;
	private AlertDialog.Builder build;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.containerdispatch);			
		
		ActionBar actionBar = getActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		userList = (ListView) findViewById(R.id.listView1);
		mHelper = new DbHelper(this);
	
		userList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String val=userId.get(arg2);
				
				
				Intent i = new Intent(getApplicationContext(),
						Condispatchfinish.class);
			
				i.putExtra("ID" , userId.get(arg2));		
				
				startActivity(i);
				
				//Toast.makeText(getApplicationContext(), val.toString(), 10000).show();

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
				+ DbHelper.Table_PENCONT  , null);
		
		userId.clear();
		disp_contno.clear();
		disp_packtype.clear();
		Disp_Qty.clear();
		Disp_docdate.clear();
		
		
		if (mCursor.moveToFirst()) {
			do {
				
				userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.pen_contID)));
				disp_contno.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.pen_containerno)));
				disp_packtype.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.pen_contDATE)));
				Disp_Qty.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.pen_QTY) ));
				Disp_docdate.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.pen_DOCDATE) ));
				
			} while (mCursor.moveToNext());
	     	}
		DisplayAdapter disadpt = new DisplayAdapter(Condispatch.this,userId,Disp_Qty  ,disp_packtype,disp_contno,Disp_docdate);
		
		mCursor.close();
		dataBase.close();
		
		userList.setAdapter(disadpt);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
	
	switch (item.getItemId()) {
	case android.R.id.home:
		
		Condispatch.this.onBackPressed();
		break;

	default:
		break;
	}
	
		return super.onOptionsItemSelected(item);
	}
	
	
}
