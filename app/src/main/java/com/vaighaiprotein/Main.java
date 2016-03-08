package com.vaighaiprotein;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Main extends Activity {
	
	ListView ls;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);

		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		ls=(ListView) findViewById(R.id.listView1);
		
		ls.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				switch(arg2)
				{
				case 0:
					 Intent it = new Intent(Main.this,Receiptdisp.class);
					 startActivity(it);
					 break;
				case 1:
					Intent i = new Intent(Main.this,Pendpallet.class);
					startActivity(i);
					break;
				case 2:
					Intent coint = new Intent(Main.this,Conproduct.class);
					startActivity(coint);					
				break;
				case 3 :					
					Intent dispint = new Intent(Main.this,Undispcont.class);
					startActivity(dispint);
					
					break;
					
				case 4 :					
					Intent conint = new Intent(Main.this,Condispatch.class);
					startActivity(conint);
					
					break;
				
				default:
					
					break;
				}				
				
				
				
			}
		});
		
	}

}
