package com.vaighaiprotein;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Disppallet extends Activity {

	EditText ed,inputsearch,docdat;
	Button btn;
	String pallno,TODAYDAT,s1,palldt,id;
	private ArrayList<String> ARE_ANAME = new ArrayList<String>();
	private ListView listView;
    private Button button;
	DbHelper database;
	private SQLiteDatabase dataBase;
	private DbHelper mHelper;
	private ArrayAdapter<String> adapter;
	static final int DATE_DIALOG_ID = 100;
	final CharSequence[] items = {" Easy "," Medium "," Hard "," Very Hard "};
	final ArrayList seletedItems=new ArrayList();
    JSONObject jsonObject = new JSONObject();
    JSONArray mJSONArray = new JSONArray();
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
    private int mLastCorrectPosition = -1;
    private int mButtonPosition = 0;
	private String[] pallets = new String[5000];
	int len;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pendpallet);

        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
		ed = (EditText) findViewById(R.id.palletno);
		btn = (Button) findViewById(R.id.btn);
		docdat =  (EditText) findViewById(R.id.docdate);
		cd = new ConnectionDetector(getApplicationContext());

		id=getIntent().getExtras().getString("ID");

		 mHelper=new DbHelper(this);

		  Time today = new Time(Time.getCurrentTimezone());
	         today.setToNow();

	         int day = today.monthDay;
	         int mon = today.month+1;
	         String selMonth = null,selDay = null;

	         docdat.setInputType(InputType.TYPE_NULL);


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

	       docdat.setText(TODAYDAT);

	ed .setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {

			Searchpalletno();

		}
	});

	docdat.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog(DATE_DIALOG_ID);

		}
	});

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				if (pallno.length() > 0 && palldt.length() > 0 && isInternetPresent) {

					Savedata();
				} else {
					AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Disppallet.this);
					alertBuilder.setTitle("Invalid Data");
					alertBuilder.setMessage("Please, Enter valid data");
					alertBuilder.setMultiChoiceItems(items, null,
							new DialogInterface.OnMultiChoiceClickListener() {
								// indexSelected contains the index of item (of which checkbox checked)
								@Override
								public void onClick(DialogInterface dialog, int indexSelected,
													boolean isChecked) {
									if (isChecked) {
										// If the user checked the item, add it to the selected items
										// write your code when user checked the checkbox
										seletedItems.add(indexSelected);
									} else if (seletedItems.contains(indexSelected)) {
										// Else, if the item is already in the array, remove it
										// write your code when user Uchecked the checkbox
										seletedItems.remove(Integer.valueOf(indexSelected));
									}
								}
							});


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

	protected void Savedata() {

		palldt=docdat.getText().toString().trim();

		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String IMEINO=telephonyManager.getDeviceId();
		dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();

		values.put(DbHelper.Pal_no,pallno);
		values.put(DbHelper.paldocdate,palldt );
		values.put(DbHelper.Pal_CFSREC,id);
		values.put(DbHelper.Pal_Entdate,TODAYDAT);

		dataBase.insert(DbHelper.Table_pall_dis, null, values);
		String formattedMonth = null;
		InputStream webs = null;

		try
		{
		//	Toast.makeText(getApplication(), pallets[0].toString(), Toast.LENGTH_LONG).show();
		//	Toast.makeText(getApplication(), palldt.toString(), Toast.LENGTH_LONG).show();
		//	Toast.makeText(getApplication(), IMEINO.toString(), Toast.LENGTH_LONG).show();

		//	HttpClient httpclient = new DefaultHttpClient();
		//	String surl = "http://223.30.82.99:8080/Worldwide/CFSDISPPALLETDETAIL.php?CFSDISPATCHBASICID="+id+"&DOCDATE="+palldt+"&PALLETNO="+pallets[0]+"&IMEINO="+IMEINO;
		//	HttpPost httppost = new HttpPost(surl);
		//	HttpResponse response = httpclient.execute(httppost);
		//	HttpEntity entity = response.getEntity();
		//	webs = entity.getContent();


		//	String s="";
			//try
			//{
				HttpClient httpClient=new DefaultHttpClient();
				HttpPost httpPost=new HttpPost("http://223.30.82.99:8080/Worldwide/CFSDISPPALLETDETAIL.php");

				List<NameValuePair> list=new ArrayList<NameValuePair>();

				for (int i = 0; i < len; i++) {

					if(pallets[i]!="")
					{
						list.add(new BasicNameValuePair("name[]", pallets[i]));
					}
				}

					list.add(new BasicNameValuePair("CFSDISPATCHBASICID", id));
					list.add(new BasicNameValuePair("DOCDATE", palldt));
					list.add(new BasicNameValuePair("IMEINO", IMEINO));

				//list.add(new BasicNameValuePair("pass",valuse[1]));
				httpPost.setEntity(new UrlEncodedFormEntity(list));
				HttpResponse httpResponse=  httpClient.execute(httpPost);

				HttpEntity httpEntity=httpResponse.getEntity();

			}
			catch(Exception exception)  {}
			//return s;


		//}
		//catch (Exception e)
		//{

//		}

		dataBase.close();
		finish();
	}

	protected void Searchpalletno() {

		ARE_ANAME.clear();
        final Dialog dialog = new Dialog(Disppallet.this);
        dialog.setContentView(R.layout.pelletch );
        dialog.setTitle("Select Pallet");
        listView = (ListView) dialog.findViewById(R.id.list);
        button= (Button) dialog.findViewById(R.id.button);

        inputsearch =  (EditText) dialog.findViewById(R.id.editText1);
        database=new DbHelper(Disppallet.this);

    		dataBase = mHelper.getWritableDatabase();
    		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
    				+ DbHelper.Table_PENDPALLET , null);

    		if (mCursor.moveToFirst()) {
    			do {
    				ARE_ANAME.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.pen_PALLETNO)));

   			} while (mCursor.moveToNext());
            }
        dialog.show();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice , ARE_ANAME);
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                len = listView.getCount();
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                for (int i = 0; i < len; i++) {
                    if (checked.get(i)) {
                        String item = ARE_ANAME.get(i);

                        pallets[i] = item;


//     jsonObject.put("formaat", mJSONArray);
  /* do whatever you want with the checked item */
                    }
                }


                //  Toast.makeText(getApplication(), item.toString(), Toast.LENGTH_LONG).show();
                //ed.setText(itemValue)
                dialog.cancel();
                // ed.setText(items);


                //	if (isInternetPresent) {

                Savedata();
                //		}

            }

        });

       inputsearch.addTextChangedListener(new TextWatcher() {

           @Override
           public void onTextChanged(CharSequence s, int start, int before,
                                     int count) {
               adapter.getFilter().filter(s.toString());
           }

           @Override
           public void beforeTextChanged(CharSequence s, int start, int count,
                                         int after) {
           }

           @Override
           public void afterTextChanged(Editable s) {
           }
       });

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
        	docdat.setText(docdate);


        }
    };



}
