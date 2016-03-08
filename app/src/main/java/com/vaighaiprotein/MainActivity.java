package com.vaighaiprotein;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Character.UnicodeBlock;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {
	String result = null;
	String result1 = null;
	String result2 = null;
	String result3 = null;
	private SQLiteDatabase dataBase;
	private DbHelper mHelper;	
	String party,PACKINGTYPE,item,itemmaster,partyname,areaname,subcat,city,area,lorryno,pono,itemmasterid,itemid,partymastid,partyid,category,imeino,dispatchbasicid,dispqty,sino,unit,stat,psino,ppono,plorryno,pqtymt,pqtynos,pitem,puom,pdstat,produnit,ITEMMAS,ITEMNAME,CFSREC,CONTRACTNO,consig,conprod,consubcat,conpacktype,conu,contnoid,containeno,packingtype,contqty,palletno,palletqty,PACK,contDocdate;
	int j;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
	
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		 mHelper=new DbHelper(this);
		setContentView(R.layout.splash);
		getActionBar().hide();
		StrictMode.enableDefaults();	
		InputStream webs = null;	
		
		mHelper.deleteAll();
		mHelper.itemdeleteAll();		
		mHelper.catdeleteall();
		mHelper.DISPDELETEALL();
		mHelper.PENDELETEALL();
		mHelper.PENDCONT();
		mHelper.PENDCONTAINERS();
		mHelper.PENDPALLET();
		mHelper.PENDDISP();
		
			
	
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			String surl = "http://223.30.82.99:8080/Worldwide/itemcat.php";
		
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
	 	String[] SUBCATEGORY = new  String[jArray.length()];
	 	 
	 	   for (  j = 0; j < jArray.length(); j++) { 	
	 	JSONObject Json = jArray.getJSONObject(j);
	 	SUBCATEGORY[j] = Json.getString("SUBCATEGORY");
	 	subcat=Json.getString("SUBCATEGORY");
	 		 
	 	insertdata();
	 	   }
	 	   
		 }
	 		   catch(Exception e)
				  {
				  }	
		 
		 
		 InputStream webs1 = null;	
			
			
			try
			{
				HttpClient httpclient1 = new DefaultHttpClient();
				String surl1 = "http://223.30.82.99:8080/Worldwide/dispatchdet.php";							
				HttpPost httppost1 = new HttpPost(surl1);			
				HttpResponse response1 = httpclient1.execute(httppost1);			
				HttpEntity entity1 = response1.getEntity();
				webs1 = entity1.getContent();	
				   
			}
			catch (Exception e)
			{
			}
			
			try
			{
			BufferedReader br1 = new BufferedReader(new InputStreamReader(webs1,"iso.8859-1"),8);
			StringBuilder sb1 = new StringBuilder();
			String line1 = null;
			while((line1=br1.readLine())!=null)
			{
			sb1.append(line1+"/n");	
			}
			
			webs1.close();
			result1=sb1.toString() ;
			}				
			catch (Exception e)
			{
				
			}
			
			
			

			 try {	   
		 	 JSONArray jArray1 = new JSONArray(result1);	 	
		 	String[] LORRYNO = new  String[jArray1.length()];
		 	String[] PONO = new  String[jArray1.length()];
		 	String[] ITEMMASTERID = new  String[jArray1.length()];
		 	String[] ITEMID = new  String[jArray1.length()];
		 	String[] PARTYMASTID = new  String[jArray1.length()];
		 	String[] PARTYID = new  String[jArray1.length()];
		 	String[] CATEGORY = new  String[jArray1.length()];
		 	String[] DISPQTY = new  String[jArray1.length()];
		 	String[] IMEINO = new  String[jArray1.length()];
		 	String[] DISPATCHBASICID = new  String[jArray1.length()];
		 	String[] UNITVA = new  String[jArray1.length()];
		 	String[] SINO = new  String[jArray1.length()];
		 	String[] STAT = new  String[jArray1.length()];
		 	for (  j = 0; j < jArray1.length(); j++) { 	
		 	JSONObject Json1 = jArray1.getJSONObject(j);
		 	LORRYNO[j] = Json1.getString("LORRYNO");
		 	PONO[j] = Json1.getString("PONO");
		 	ITEMMASTERID[j] = Json1.getString("ITEMMASTERID");
		 	ITEMID[j] = Json1.getString("ITEMID");
		 	PARTYMASTID[j] = Json1.getString("PARTYMASTID");
		 	PARTYID[j] = Json1.getString("PARTYID");
		 	CATEGORY[j] = Json1.getString("CATEGORY");
		 	DISPQTY[j] = Json1.getString("DISPQTY");
		 	IMEINO[j] = Json1.getString("IMEINO");
		 	DISPATCHBASICID[j] = Json1.getString("DISPATCHBASICID");
		 	SINO[j] = Json1.getString("SINO");
		 	UNITVA[j]=Json1.getString("UNIT");
		 	STAT[j]=Json1.getString("STATUS");
		 	lorryno = Json1.getString("LORRYNO");
		 	pono = Json1.getString("PONO");
		 	itemmasterid = Json1.getString("ITEMMASTERID");
		 	itemid   = Json1.getString("ITEMID");
		 	partymastid = Json1.getString("PARTYMASTID");
		 	partyid  = Json1.getString("PARTYID");
		 	category = Json1.getString("CATEGORY");
		 	dispqty  = Json1.getString("DISPQTY");
		 	imeino = Json1.getString("IMEINO");
		 	dispatchbasicid = Json1.getString("DISPATCHBASICID");
		 	sino=Json1.getString("SINO");
		 	unit=Json1.getString("UNIT");
		 	stat=Json1.getString("STATUS");
		 	PACKINGTYPE=Json1.getString("PACKINGTYPE");
		 	insertitemdata();		 	
		 	   }		 			
			 }
			 
		 		   catch(Exception e)
					  {
					  }	
			  
			 
			 InputStream webs3 = null;	
				
				
				try
				{
					HttpClient httpclient3 = new DefaultHttpClient();
					String surl3 = "http://223.30.82.99:8080/Worldwide/RECEIPTPEND.php";							
					HttpPost httppost3 = new HttpPost(surl3);			
					HttpResponse response3 = httpclient3.execute(httppost3);			
					HttpEntity entity3 = response3.getEntity();
					webs3 = entity3.getContent();	
					   
				}
				catch (Exception e)
				{
				}
				
				try
				{
				BufferedReader br3 = new BufferedReader(new InputStreamReader(webs3,"iso.8859-1"),8);
				StringBuilder sb3 = new StringBuilder();
				String line3 = null;
				while((line3=br3.readLine())!=null)
				{
				sb3.append(line3+"/n");	
				}
				
				webs3.close();
				result3=sb3.toString() ;
				}				
				catch (Exception e)
				{
					
				}			 
				 try {	   
					 
			 	 JSONArray jArray3 = new JSONArray(result3);	 	
			 	String[] PENPONO = new  String[jArray3.length()];
			 	String[] PENSINO = new  String[jArray3.length()];
			 	String[] PENLORRYNO = new  String[jArray3.length()];
			 	String[] PENQTYMT = new  String[jArray3.length()];
			 	String[] PENQTYNOS = new  String[jArray3.length()];
			 	String[] ITEM = new  String[jArray3.length()];
			 	String[] UOM = new  String[jArray3.length()];			 	
			 	String[] DISPSTATUS = new  String[jArray3.length()];		
				String[] CFSRECEIPTBASICID = new  String[jArray3.length()];	
			 	for (  j = 0; j < jArray3.length(); j++) { 	
			 	JSONObject Json1 = jArray3.getJSONObject(j);
			 	PENPONO[j] = Json1.getString("PONO");
			 	PENSINO[j] = Json1.getString("SINO");
			 	PENLORRYNO[j] = Json1.getString("LORRYNO");
			 	PENQTYMT[j] = Json1.getString("RECEIPTQTYMT");
			 	PENQTYNOS[j] = Json1.getString("RECEIPTQTY");
			 	ITEM[j] = Json1.getString("ITEM");
			 	UOM[j] = Json1.getString("UOM");
			 	DISPSTATUS[j] = Json1.getString("DISPSTATUS");
			 	CFSRECEIPTBASICID[j] = Json1.getString("CFSRECEIPTBASICID");			 
			 	psino = Json1.getString("SINO");
			 	ppono = Json1.getString("PONO");
			 	plorryno = Json1.getString("LORRYNO");
			 	pqtymt   = Json1.getString("RECEIPTQTYMT");
			 	pqtynos = Json1.getString("RECEIPTQTY");			 
			 	pitem = Json1.getString("ITEM");
			 	puom  = Json1.getString("UOM");
			 	pdstat= Json1.getString("DISPSTATUS");			 	
			 	CFSREC= Json1.getString("CFSRECEIPTBASICID");
			 	PACK= Json1.getString("PACKINGTYPE");
			 	
			 	insertpenstat();		 	
			 	   }		 			
				 }
				 
			 		   catch(Exception e)
						  {
						  }
				 
				 
					
					try
					{
						HttpClient httpclient1 = new DefaultHttpClient();
						String surl1 = "http://223.30.82.99:8080/Worldwide/Item.php";							
						HttpPost httppost1 = new HttpPost(surl1);			
						HttpResponse response1 = httpclient1.execute(httppost1);			
						HttpEntity entity1 = response1.getEntity();
						webs1 = entity1.getContent();	
						   
					}
					catch (Exception e)
					{
					}
					
					try
					{
					BufferedReader br1 = new BufferedReader(new InputStreamReader(webs1,"iso.8859-1"),8);
					StringBuilder sb1 = new StringBuilder();
					String line1 = null;
					while((line1=br1.readLine())!=null)
					{
					sb1.append(line1+"/n");	
					}
					
					webs1.close();
					result1=sb1.toString() ;
					}				
					catch (Exception e)
					{
						
					}
					 try {	   
				 	    JSONArray jArray1 = new JSONArray(result1);	 	
				 	   String[] ITEMMASTERID = new  String[jArray1.length()];
				 	   String[] ITEMID = new  String[jArray1.length()];
				 	  String[] UNIT = new  String[jArray1.length()];		 	  
				 	   for (  j = 0; j < jArray1.length(); j++) { 	
				 	   JSONObject Json1 = jArray1.getJSONObject(j);		 	
				 	   ITEMMASTERID[j] = Json1.getString("ITEMMASTERID");
				 	   ITEMID[j] = Json1.getString("ITEMID");		
				 	  UNIT[j]=Json1.getString("UNIT");
				 	 ITEMMAS=Json1.getString("ITEMMASTERID");		 	   
				 	ITEMNAME=Json1.getString("ITEMID");		 	  
				 	   produnit=Json1.getString("UNIT");
				 	    
				 	insertpenpo();		 	
				 	   }
				 			
					 }
					 
					  
				 		   catch(Exception e)
							  {
							  }	
					 
					 
					 try
						{
							HttpClient httpclient = new DefaultHttpClient();
							String surl = "http://223.30.82.99:8080/Worldwide/contract.php";
						
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
					 	String[] CONTNO = new  String[jArray.length()];
					 	String[] CONSIGNEE = new  String[jArray.length()];
					 	String[] PRODUCT = new  String[jArray.length()];
					 	String[] SUBCATEGORY = new  String[jArray.length()];
					 	String[] PTYPE = new  String[jArray.length()];
					 	String[] CONUOM = new  String[jArray.length()];
					 	 
					 	   for (  j = 0; j < jArray.length(); j++) { 	
					 	JSONObject Json = jArray.getJSONObject(j);
					 	CONTNO[j] = Json.getString("docid");
					 	CONSIGNEE[j] = Json.getString("CONSIGNEE");
					 	PRODUCT[j] = Json.getString("PRODUCT");
					 	SUBCATEGORY[j] = Json.getString("SUBCATEGORY");
					 	PTYPE[j] = Json.getString("PTYPE");
					 	CONUOM[j] = Json.getString("UOM");					 	
					 	
					 	CONTRACTNO=Json.getString("docid");
					 	consig=Json.getString("CONSIGNEE");
					 	conprod=Json.getString("PRODUCT");
					 	consubcat=Json.getString("SUBCATEGORY");
					 	conpacktype=Json.getString("PTYPE");
					 	conu=Json.getString("UOM");
					 		 
					 	inserconitem();
					 	   }
					 	   
					 	  try
							{
								HttpClient httpclient = new DefaultHttpClient();
								String surl = "http://223.30.82.99:8080/Worldwide/PENDINGDISP.php";
							
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
							
							JSONArray jArra = new JSONArray(result);	 	
						 
						 	   for (  j = 0; j < jArra.length(); j++) { 	
						 	JSONObject Json = jArra.getJSONObject(j);
						 				 	
						 	
						 	contnoid=Json.getString("CFSDISPATCHBASICID");
						 	containeno=Json.getString("CONTNO");
						 	packingtype=Json.getString("PACKINGTYPE");
						 	contqty=Json.getString("CONTAINERNO");
							contDocdate=Json.getString("DOCDATE");
						 	
						 	insertpencont();
						 	   }
					 	   
						 }
					 		   catch(Exception e)
								  {
								  }	
						
						 
						 try
							{
								HttpClient httpclient = new DefaultHttpClient();
								String surl = "http://223.30.82.99:8080/Worldwide/pendpallet.php";
							
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
						 	
						 	   for (  j = 0; j < jArray.length(); j++) { 	
						 	JSONObject Json = jArray.getJSONObject(j);
						 	
						 	palletno=Json.getString("PALLETNO");
						 	palletqty=Json.getString("QTY");
						 		 
						 	insertpallet();
						 	   }
						 	   
							 }
						 		   catch(Exception e)
									  {
									  }	
							 
		 Intent It=new Intent(this,Login.class);
		 startActivity(It); 
}
			
				private void insertpallet() {	
					
										
					dataBase=mHelper.getWritableDatabase();
					ContentValues values=new ContentValues();				
					values.put(DbHelper.pen_PALLETNO,palletno);
					values.put(DbHelper.PEN_PALLETQTY, palletqty );
					
					dataBase.insert(DbHelper.Table_PENDPALLET, null, values);
					dataBase.close();
					//finish();
						
				}
				
				

				private void insertpencont() {
					
					dataBase=mHelper.getWritableDatabase();
					ContentValues values=new ContentValues();				
					values.put(DbHelper.pen_contID,contnoid);
					values.put(DbHelper.pen_containerno, containeno );
					values.put(DbHelper.pen_contDATE,packingtype );	
					values.put(DbHelper.pen_QTY,contqty );
					values.put(DbHelper.pen_DOCDATE,contDocdate );
					dataBase.insert(DbHelper.Table_PENCONT, null, values);
					dataBase.close();
					//finish();
		
	}

				private void inserconitem() {
					
					dataBase=mHelper.getWritableDatabase();
					ContentValues values=new ContentValues();				
					values.put(DbHelper.ecno,CONTRACTNO);
					values.put(DbHelper.ecconsignee, consig );
					values.put(DbHelper.ecproduct,conprod );	
					values.put(DbHelper.ecsubcat,consubcat );
					values.put(DbHelper.ecptype,conpacktype );
					values.put(DbHelper.ecUOM,conu );	
						
					dataBase.insert(DbHelper.Table_Contract, null, values);					
				
					dataBase.close();
					//finish();
	
		
	}

				private void insertpenpo() {
					
					dataBase=mHelper.getWritableDatabase();
					ContentValues values=new ContentValues();				
					values.put(DbHelper.Item_ID,ITEMMAS);
					values.put(DbHelper.Item_Name, ITEMNAME );
					values.put(DbHelper.disunit,produnit );					
						
					dataBase.insert(DbHelper.Table_Item, null, values);					
				
					dataBase.close();
					//finish();
	
		
	}

				private void insertpenstat() {
					

					dataBase=mHelper.getWritableDatabase();
					ContentValues values=new ContentValues();				
					values.put(DbHelper.PENSINO,psino);
					values.put(DbHelper.PENPONO, ppono);
					values.put(DbHelper.PENLORRYNO,plorryno);
					values.put(DbHelper.PENRECEIPTQTYMT,pqtymt);
					values.put(DbHelper.PENRECEIPTQTY,pqtynos);
					values.put(DbHelper.PENITEM,pitem );
					values.put(DbHelper.PENUOM,puom );					
					values.put(DbHelper.PENDISPSTATUS,pdstat );
					values.put(DbHelper.Pal_CFSREC,CFSREC );
					values.put(DbHelper.LOT_PACKTYPE,PACK );
						
					dataBase.insert(DbHelper.Table_penpall, null, values);					
				
					dataBase.close();
					//finish();
	

				
				}

				private void insertitemdata() {
					dataBase=mHelper.getWritableDatabase();
					ContentValues values=new ContentValues();				
					values.put(DbHelper.KEY_lorryno,lorryno);
					values.put(DbHelper.KEY_ITEMMASTERID,itemmasterid );
					values.put(DbHelper.KEY_PONO,pono );
					values.put(DbHelper.KEY_ITEMID,itemid );
					values.put(DbHelper.KEY_PARTYMASTID,partymastid );
					values.put(DbHelper.KEY_PARTYID,partyid );
					values.put(DbHelper.KEY_CATEGORY,category );
					values.put(DbHelper.KEY_DISPQTY,dispqty );					
					values.put(DbHelper.KEY_IMEINO,imeino );
					values.put(DbHelper.KEY_DISPID,dispatchbasicid );
					values.put(DbHelper.KEY_SINO,sino );
					values.put(DbHelper.KEY_UNIT,unit );
					values.put(DbHelper.LOT_STAT ,stat );
					values.put(DbHelper.LOT_PACKTYPE ,PACKINGTYPE );
					
					dataBase.insert(DbHelper.TABLE_NAME, null, values);					
				
					dataBase.close();
					//finish();
	}
				private void insertdata() {
				dataBase=mHelper.getWritableDatabase();
				ContentValues values=new ContentValues();
			
				values.put(DbHelper.CAT_PROD,subcat);
				
			
			dataBase.insert(DbHelper.TABLE_CAT, null, values);
			
				dataBase.close();
				//finish();
				
				
			}


}

