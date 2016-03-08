package com.vaighaiprotein;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

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
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Pallatization extends Activity {

	int j;
	Spinner sp,sp_txt;
	EditText eqty,doc_date,noofpallets,noofblocks,palletwt;
	Button btn;
	String imc_met,qty,status,TODAYDAT,s1,cfsno,DOCDt,imeino,txt_pal,nospallet,nosblocks,nospalletwt;
	private boolean isUpdate;
	static final int DATE_DIALOG_ID = 100;
	private SQLiteDatabase dataBase;
	private DbHelper mHelper;
	List<String> spinnerArray =  new ArrayList<String>();
	ConnectionDetector cd;
	Boolean isInternetPresent = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pallatization);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		isUpdate=getIntent().getExtras().getBoolean("update");
	    sp = (Spinner) findViewById(R.id.spinner1);
	    eqty = (EditText) findViewById(R.id.editText1);
	    btn = (Button) findViewById(R.id.button1);
	    doc_date = (EditText) findViewById(R.id.editText2);
	    sp_txt = (Spinner) findViewById(R.id.spinner2);
		noofpallets = (EditText) findViewById(R.id.Noofpallets);
		noofblocks = (EditText) findViewById(R.id.nooblocks);
		palletwt = (EditText) findViewById(R.id.Palleboxwt);

		String result = null,party,palletn = null,palletnos=null;
		cd = new ConnectionDetector(getApplicationContext());

		 TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		 imeino=telephonyManager.getDeviceId();

		 InputStream webs = null;

		 mHelper=new DbHelper(this);
		 dataBase = mHelper.getWritableDatabase();

		  doc_date.setInputType(InputType.TYPE_NULL);
		  cfsno=getIntent().getExtras().getString("cfsbas");

			try
			{
				HttpClient httpclient = new DefaultHttpClient();
				String surl = "http://223.30.82.99:8080/worldwide/pallet.php";
				HttpPost httppost = new HttpPost(surl);
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				webs = entity.getContent();

			}
			catch (Exception e)
			{

			}

			try
			{
			BufferedReader br = new BufferedReader(new InputStreamReader(webs,"iso.8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line=br.readLine())!=null)
			{
			sb.append(line+"/n");
			}

			webs.close();
			result=sb.toString() ;
			}
			catch (Exception e)
			{

			}
			 try {
			 	   JSONArray jArray = new JSONArray(result);
			 	   String[] Palletno = new  String[jArray.length()];

			 	   for (  j = 0; j < jArray.length(); j++) {
			 		   JSONObject Json = jArray.getJSONObject(j);
			 		  Palletno[j] = Json.getString("PALLETNO");

			 		 palletn=Json.getString("PALLETNO");
			 		spinnerArray.add(palletn);

			 	   }

				 }
			 		   catch(Exception e)
						  {
						  }

			 spinnerArray.add("Spillage");

			 ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					    this, android.R.layout.simple_spinner_item, spinnerArray);

			 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			// Spinner sItems = (Spinner) findViewById(R.id.spinner1);
			 sp.setAdapter(adapter);



			 Time today = new Time(Time.getCurrentTimezone());
	         today.setToNow();

	         int day = today.monthDay;
	         int mon = today.month+1;
	         String selMonth = null,selDay = null;


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

	       doc_date.setText(TODAYDAT);

	         doc_date.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					 showDialog(DATE_DIALOG_ID);

				}
			});



		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			String surl = "http://223.30.82.99:8080/worldwide/Palletcount.php?cfsno="+cfsno;
			HttpPost httppost = new HttpPost(surl);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			webs = entity.getContent();

		}
		catch (Exception e)
		{

		}

		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(webs,"iso.8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line=br.readLine())!=null)
			{
				sb.append(line+"/n");
			}

			webs.close();
			result=sb.toString() ;
		}
		catch (Exception e)
		{

		}
		try {
			JSONArray jArray = new JSONArray(result);
			String[] PALLETCOUNT = new  String[jArray.length()];

			for (  j = 0; j < jArray.length(); j++) {
				JSONObject Json = jArray.getJSONObject(j);
				PALLETCOUNT[j] = Json.getString("PALLETCOUNT");

				palletnos=Json.getString("PALLETCOUNT");


			}

		}
		catch(Exception e)
		{
		}


		noofpallets.setText(palletnos);

			 btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					imc_met=sp.getSelectedItem().toString();
					qty=eqty.getText().toString().trim();
					DOCDt=doc_date.getText().toString().trim();
					status="Pending";
					txt_pal=sp_txt.getSelectedItem().toString();
					nospallet = noofpallets.getText().toString().trim();
					nosblocks = noofblocks.getText().toString().trim();
					nospalletwt=palletwt.getText().toString().trim();

					isInternetPresent = cd.isConnectingToInternet();

					if(qty.length()>0 && DOCDt.length()>0 && isInternetPresent )
					{

					savedata();

					}
					else {

						AlertDialog.Builder alertBuilder=new AlertDialog.Builder(Pallatization.this);
						alertBuilder.setTitle("Invalid Data");
						alertBuilder.setMessage("Please, Enter valid data");
						alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();

							}
						});
						alertBuilder.create().show();
					}


				}
			});

	}


	protected void savedata() {

	//Toast.makeText(getApplicationContext(), txt_pal.toString(), 3000).show();

		dataBase=mHelper.getWritableDatabase();

		ContentValues val=new ContentValues();
		val.put(DbHelper.Pal_no, imc_met );
		val.put(DbHelper.Pal_Entdate,TODAYDAT );
		val.put(DbHelper.Pal_Qty,qty );
		val.put(DbHelper.Pal_Status,status );
		val.put(DbHelper.Pal_CFSREC,cfsno );
		val.put(DbHelper.paldocdate ,DOCDt );
		val.put(DbHelper.Pallet_typ ,txt_pal );
		val.put(DbHelper.Pallet_nos ,nospallet );
		val.put(DbHelper.Pallet_nosblocks ,nosblocks );
		val.put(DbHelper.Pallet_empwt ,nospalletwt );


		if(isUpdate)
		{
		}
		else {

		dataBase.insert(DbHelper.Table_pallet, null, val);

		InputStream webs = null;

		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			String surl = "http://223.30.82.99:8080/Worldwide/pallatizationadd.php?CFSRECEIPTBASICID="+cfsno+"&DOCDATE="+DOCDt+"&PALLETNO="+imc_met+"&QTY="+qty+"&IMEINO="+imeino+"&PALTYPE="+txt_pal+"&PALCOUNT="+nospallet+"&PALLETBLOCK="+nosblocks+"&PALLETWT="+nospalletwt;
			HttpPost httppost = new HttpPost(surl);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			webs = entity.getContent();

		}
		catch (Exception e)
		{

		}
		}


		dataBase.close();
		finish();



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
        	doc_date.setText(docdate);


        }
    };


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

	switch (item.getItemId()) {
	case android.R.id.home:

		Pallatization.this.onBackPressed();
		break;



	default:
		break;
	}

		return super.onOptionsItemSelected(item);
	}

}
