package com.vaighaiprotein;

import java.io.InputStream;
import java.util.ArrayList;

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

public class Containerdisp extends Activity {
	
	ListView userList;
	private DbHelper mHelper;
	DbHelper database;
	private SQLiteDatabase dataBase;
	private AlertDialog.Builder build;
	
	private ArrayList<String> userId = new ArrayList<String>();
	private ArrayList<String> contdocdate = new ArrayList<String>();
	private ArrayList<String> consignee = new ArrayList<String>();
	private ArrayList<String> product = new ArrayList<String>();
	private ArrayList<String> qty = new ArrayList<String>();	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.containerdisp);
		
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
						Containerinw.class);

				i.putExtra("update", true);
				i.putExtra("updval", val.toString());
				
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
        
		dataBase = mHelper.getWritableDatabase();
		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
				+ DbHelper.Table_cont  , null);
		
		userId.clear();
		contdocdate.clear();
		consignee.clear();
		product.clear();
		qty.clear();
				
		
		if (mCursor.moveToFirst()) {
			do {
				userId.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.Lot_ID)));
				contdocdate.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.CONT_docdate)));
				consignee.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.cont_consignee)));
				product.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.cont_product) ));
				qty.add(mCursor.getString(mCursor.getColumnIndex( DbHelper.cont_containerno) ));
				
			} while (mCursor.moveToNext());
		}
		DisplayAdapter disadpt = new DisplayAdapter(Containerdisp.this,userId,  contdocdate,consignee,product,qty);
		
		
		
		mCursor.close();

		userList.setAdapter(disadpt);
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lorry, menu);
		return true;
		}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {		
	
	switch (item.getItemId()) {
	case android.R.id.home:
		
		Containerdisp.this.onBackPressed();
		break;
		case R.id.Load:
			
			Intent i = new Intent(Containerdisp.this,Containerinw.class );
			i.putExtra("update", false);
			startActivity(i);
			
	default:
		break;
	}
	
		return super.onOptionsItemSelected(item);
	}

}
