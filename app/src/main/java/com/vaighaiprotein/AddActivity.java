package com.vaighaiprotein;



import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import android.annotation.SuppressLint;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

@SuppressLint("ShowToast")
public class AddActivity extends Activity implements OnClickListener  {
private Button btn_save;
private ListView listView;
private EditText doc_date,inputsearch,receipttime,pono,lorryno,packtype,uom,opwt,clwt,rqty,nosqty,DISPID,item,nosbags;
private DbHelper mHelper;
private SQLiteDatabase dataBase;
private String id,recdate,rectime,recpono,lorry,packing,produom,openingwt,closingwt,recqty,qtyno,TODAYDAT,dispbasicid,product,imeino,subcate,ordno,sbags;
private boolean isUpdate;
static final int DATE_DIALOG_ID = 100;
private ArrayList<String> ARE_ANAME = new ArrayList<String>();
private ArrayAdapter<String> adapter;
DbHelper database;
Float qty;
private String best;
ConnectionDetector cd;
Boolean isInternetPresent = false;
String s1;
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.add_activity);             
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        doc_date=(EditText) findViewById(R.id.Dat);
        receipttime=(EditText) findViewById(R.id.receipttime);
        btn_save=(Button)findViewById(R.id.save_btn);
        pono=(EditText)findViewById(R.id.frst_editTxt);
        lorryno=(EditText)findViewById(R.id.last_editTxt);        
        packtype=(EditText) findViewById(R.id.packtype);
        uom=(EditText) findViewById(R.id.produom);
        opwt=(EditText) findViewById(R.id.opwt);
        clwt=(EditText) findViewById(R.id.clswt);
        rqty=(EditText) findViewById(R.id.totqty);
        nosqty=(EditText) findViewById(R.id.qtyinno);
        DISPID=(EditText) findViewById(R.id.dispbasicid);        
        item=(EditText) findViewById(R.id.product);
		nosbags = (EditText) findViewById(R.id.Bags);
        cd = new ConnectionDetector(getApplicationContext());
        id=getIntent().getExtras().getString("ID");
        subcate=getIntent().getExtras().getString("subcat");
        btn_save.setOnClickListener(this);

        mHelper=new DbHelper(this);
         
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
         
        int day = today.monthDay;
        int mon = today.month+1;
        String selMonth = null,selDay = null;
         
        doc_date.setInputType(InputType.TYPE_NULL);
        pono.setInputType(InputType.TYPE_NULL);
        item.setInputType(InputType.TYPE_NULL);
        uom.setInputType(InputType.TYPE_NULL);
        packtype.setInputType(InputType.TYPE_NULL);
        isUpdate=getIntent().getExtras().getBoolean("update");
         
        if(isUpdate)
        {
		 dataBase = mHelper.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + DbHelper.Table_lot + " WHERE " + DbHelper.Lot_ID + " = " + id;

        Cursor c = dataBase.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
 				doc_date.setText( ( c.getString( c.getColumnIndex(DbHelper.Lot_date))));
 				receipttime.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_TIME))));
 				pono.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_PONO)))); 				
 				lorryno.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_LORRYNO))));
 				packtype.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_PACKTYPE))));	    
 				uom.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_UNIT))));
 				opwt.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_OPWT))));
 				clwt.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_CLWT))));
 				rqty.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_TOTQTY))));
 				nosqty.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_TQTYNOS))));
 				item.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_ITEM))));
				nosbags.setText( ( c.getString( c.getColumnIndex(DbHelper.LOT_NOOFBAGS))));

 				
			} while (c.moveToNext());
 		}   
		
         }
         
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
         
         pono.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				searchCitiesList();
			}
		});
         
         item.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				searchprod();
			}
		});
         
         database=new DbHelper(AddActivity.this);
         
 		dataBase = mHelper.getWritableDatabase();
 		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
 				+ DbHelper.TABLE_NAME+" WHERE "+DbHelper.KEY_SINO+"="+id  , null);

 		if (mCursor.moveToFirst()) {
 			do {
 				 pono.setText(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_PONO)));
 				lorryno.setText(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_lorryno)));
 				uom.setText(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_UNIT)));
 				DISPID.setText(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_DISPID)));
 				item.setText(mCursor.getString(mCursor.getColumnIndex(DbHelper.KEY_ITEMID)));
 				packtype.setText(mCursor.getString(mCursor.getColumnIndex(DbHelper.LOT_PACKTYPE)));
				nosbags.setText(mCursor.getString(mCursor.getColumnIndex(DbHelper.LOT_NOOFBAGS)));
			} while (mCursor.moveToNext());
 		}
 	
     clwt.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			 
			final DecimalFormat df = new DecimalFormat("#.000");
	    	  
	    	   if(!(clwt.getText().toString().equals("")))
            {	    	   
	    	   String clw = clwt.getText().toString().trim();
	    	   String opw = opwt.getText().toString().trim();
	    	   qty= Float.valueOf( opw)-Float.valueOf( clw) ;
	    	   rqty.setText(String.valueOf(df.format(qty)));
	    	   if(uom.getText().toString().trim().equals("MTS"))
	    	   {
	    		   nosqty.setText(String.valueOf(df.format(qty)));
	    		   //Qtyact.setEnabled(false);
	    		   nosqty.setVisibility(View.GONE);
	    	   }
	    	   }
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	});
    }  

	protected void searchprod() {

		ARE_ANAME.clear();
        final Dialog dialog = new Dialog(AddActivity.this);
        dialog.setContentView(R.layout.area);
        dialog.setTitle("Select Item");
        listView = (ListView) dialog.findViewById(R.id.list);
        inputsearch =  (EditText) dialog.findViewById(R.id.editText1);      
        database=new DbHelper(AddActivity.this);    
        ordno=pono.getText().toString().trim();
    		dataBase = mHelper.getWritableDatabase();
    		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
    	   + DbHelper.Table_Item+ " WHERE "+ DbHelper.Item_ID + " IN ('"+ordno+"')"   , null);   		
    	    		
    		if (mCursor.moveToFirst()) {
    			do {
    				ARE_ANAME.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.Item_Name)));
   				
   			} while (mCursor.moveToNext());
    		}   		
    		
        dialog.show();
        
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ARE_ANAME); 
     
        listView.setAdapter(adapter);        
        
        listView.setTextFilterEnabled(true);        
        
       listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView.getItemAtPosition(position);
                item.setText(itemValue);                
                dialog.cancel();
                setuom();
            }
        });
       
        
       inputsearch.addTextChangedListener(new TextWatcher() {
           @Override
           public void onTextChanged(CharSequence s, int start, int before,int count) {
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

	private void setuom() {
        database=new DbHelper(AddActivity.this);    
        ordno=pono.getText().toString().trim();
    		dataBase = mHelper.getWritableDatabase();
    		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
    	   + DbHelper.Table_Item+ " WHERE "+ DbHelper.Item_ID + " IN ('"+ordno+"')"   , null);
    	    		
    		if (mCursor.moveToFirst()) {
    			do {
    				uom.setText(mCursor.getString(mCursor.getColumnIndex(DbHelper.disunit)));
   			} while (mCursor.moveToNext());
    		}
	}

	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
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
	public void onClick(View v) {
		recdate=doc_date.getText().toString().trim();
		rectime=receipttime.getText().toString().trim();
		recpono= pono.getText().toString().trim(); 
		lorry=lorryno.getText().toString().trim();		
		packing=packtype.getText().toString().trim();		
		produom=uom.getText().toString().trim();
		openingwt=opwt.getText().toString().trim();
		closingwt=clwt.getText().toString().trim();		
		recqty=rqty.getText().toString().trim();
		qtyno=nosqty.getText().toString().trim();
		dispbasicid=DISPID.getText().toString().trim();
		product=item.getText().toString().trim();
		sbags=nosbags.getText().toString().trim();
		
		isInternetPresent = cd.isConnectingToInternet();
		
		if(recdate.length()>0 && rectime.length()>0 && recpono.length()>0 && lorry.length()>0  && packing.length()>0 && produom.length()>0 && openingwt.length()>0  && closingwt.length()>0 && recqty.length()>0 && qtyno.length()>0 && isInternetPresent )
		{
			saveData();
		}
		else
		{
			AlertDialog.Builder alertBuilder=new AlertDialog.Builder(AddActivity.this);
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

	private void saveData(){

		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String IMEINO=telephonyManager.getDeviceId();
		
	  long rowcnt = 0;
		dataBase=mHelper.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put(DbHelper.Lot_date,recdate);
		values.put(DbHelper.LOT_TIME,rectime );
		values.put(DbHelper.LOT_PONO,recpono );
		values.put(DbHelper.LOT_LORRYNO,lorry );
		values.put(DbHelper.LOT_PACKTYPE,packing );
		values.put(DbHelper.LOT_UNIT,produom );
		values.put(DbHelper.LOT_OPWT,openingwt );
		values.put(DbHelper.LOT_CLWT,closingwt );
		values.put(DbHelper.LOT_TOTQTY,qty );
		values.put(DbHelper.LOT_ENTRYDATE,TODAYDAT );
		values.put(DbHelper.LOT_ITEM,product );
		values.put(DbHelper.LOT_TQTYNOS,qtyno );
		values.put(DbHelper.LOT_NOOFBAGS ,sbags );
		
if(isUpdate)
{	
dataBase.update(DbHelper.Table_lot, values, DbHelper.Lot_ID+"="+id, null);

lorry=lorry.replace(" ", "%20");
packing=packing.replace(" ", "%20");
product=product.replace(" ", "%20");
 
 InputStream webs = null;
	try
	{
		HttpClient httpclient = new DefaultHttpClient();
		String surl= "http://223.30.82.99:8080/worldwide/Receiptupdate.php?MOBID="+id+"&imeino="+IMEINO+"&PONO="+recpono+"&LORRYNO="+lorry+"&PACKINGTYPE="+packing+"&RECEIPTDATE="+recdate+"&UOM="+produom+"&OPENINGWT="+openingwt+"&CLOSINGWT="+closingwt+"&RECEIPTQTYMT="+recqty+"&RECEIPTQTY="+qtyno+"&ITEM="+product+"&RECEIPTBAGS="+sbags;
		HttpPost httppost = new HttpPost(surl);			
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		webs = entity.getContent();
		
	}
	catch (Exception e)
	{		 	  
   
	}
}
else
    {
        dataBase.insert(DbHelper.Table_lot, null, values);
		rowcnt=	(mHelper.LOT_getrowid());
		String formattedMonth = null;
		InputStream webs = null;
		
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			String surl = "http://223.30.82.99:8080/Worldwide/dispupdate.php?var1="+dispbasicid;				
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
			lorry=lorry.replace(" ", "%20");
			packing=packing.replace(" ", "%20");
			product=product.replace(" ", "%20");			
			rowcnt=	(mHelper.LOT_getrowid());			
			HttpClient httpclient = new DefaultHttpClient();
			String surl = "http://223.30.82.99:8080/worldwide/Receiptadd.php?PONO="+recpono+"&LORRYNO="+lorry+"&PACKINGTYPE="+packing+"&RECEIPTDATE="+recdate+"&UOM="+produom+"&OPENINGWT="+openingwt+"&CLOSINGWT="+closingwt+"&RECEIPTQTYMT="+recqty+"&RECEIPTQTY="+qtyno+"&imeino="+IMEINO+"&MOBID="+rowcnt+"&ITEM="+product+"&RECEIPTBAGS="+sbags;
			HttpPost httppost = new HttpPost(surl);			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			webs = entity.getContent();	
		}
		catch (Exception e)
		{		 	  
	   
		}
		if(subcate.equals("Coir") || subcate.equals("Pith") || subcate.equals(""))
		{
			dataBase=mHelper.getWritableDatabase();
			ContentValues val=new ContentValues();			
			val.put(DbHelper.LOT_STAT,"RECEIVED");		
			
			dataBase.update(DbHelper.TABLE_NAME, val, DbHelper.KEY_DISPID+"='"+dispbasicid+"'", null);
		}	
			}	
dataBase.close();
		finish();
}
	
	public void searchCitiesList() {
		ARE_ANAME.clear();
        final Dialog dialog = new Dialog(AddActivity.this);
        dialog.setContentView(R.layout.area);
        dialog.setTitle("Select P.O NO");
        listView = (ListView) dialog.findViewById(R.id.list);
        inputsearch =  (EditText) dialog.findViewById(R.id.editText1);      
        database=new DbHelper(AddActivity.this);    
      
    		dataBase = mHelper.getWritableDatabase();
    		Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
    				+ DbHelper.Table_Item , null);
    		
    		if (mCursor.moveToFirst()) {
    			do {
    				ARE_ANAME.add(mCursor.getString(mCursor.getColumnIndex(DbHelper.Item_ID)));
   				
   			} while (mCursor.moveToNext());
    		}
    	
        dialog.show();
        
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ARE_ANAME);
     
        listView.setAdapter(adapter);
        
        listView.setTextFilterEnabled(true);
        
       listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView
                        .getItemAtPosition(position);
                pono.setText(itemValue);
                dialog.cancel();

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
	public boolean onOptionsItemSelected(MenuItem item) {		
	
	switch (item.getItemId()) {
	case android.R.id.home:
		
		AddActivity.this.onBackPressed();
		break;

	default:
		break;
	}
	
		return super.onOptionsItemSelected(item);
	}
	
	
}
