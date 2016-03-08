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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Cfsmain extends Activity {
	private ListView ls;
	private DbHelper mHelper;
	String itemValue;
	private ArrayList<String> catname = new ArrayList<String>();
	DbHelper database;
	private SQLiteDatabase dataBase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.cfsitem);
		ActionBar actionBar = getActionBar();
	    actionBar.setHomeButtonEnabled(true);
	    actionBar.setDisplayHomeAsUpEnabled(true);
		
		
		ls = (ListView) findViewById(R.id.listView1);
		
	
		  mHelper=new DbHelper(this);
		
		 database=new DbHelper(this);    
			dataBase = mHelper.getWritableDatabase();
		
			Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
    				+ DbHelper.TABLE_CAT , null);
    		
    		if (mCursor.moveToFirst()) {
    			do {
    				catname.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.CAT_PROD)));
   				
   			} while (mCursor.moveToNext());
    		}   		
    		
    		mCursor.close();
    		dataBase.close();
    		
    		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, catname);
    		ls.setAdapter(adapter);
    		
    ls.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			itemValue = (String) ls.getItemAtPosition(arg2);
			//Toast.makeText(getApplicationContext(), itemValue.toString(), 3000).show();
			if(itemValue.equals("Coir") || itemValue.equals("Pith"))
			{
			Intent it = new Intent(Cfsmain.this,DisplayActivity.class);
			
			it.putExtra("ID", "0");
			it.putExtra("subcat",itemValue);
			//Toast.makeText(getApplicationContext(), itemValue.toString() , 3000).show();
			startActivity(it);
			}		
			else {
				
				Intent it = new Intent(Cfsmain.this,AddActivity.class);
				it.putExtra("ID", "0");
				it.putExtra("subcat",itemValue);
				//Toast.makeText(getApplicationContext(), itemValue.toString() , 3000).show();
				startActivity(it); 
			}
		}
	});
	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
	
	switch (item.getItemId()) {
	case android.R.id.home:
		
		Cfsmain.this.onBackPressed();
		break;

	default:
		break;
	}
	
		return super.onOptionsItemSelected(item);
	}

}
