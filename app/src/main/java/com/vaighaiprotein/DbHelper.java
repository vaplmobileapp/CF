package com.vaighaiprotein;



import org.w3c.dom.Text;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	static String DATABASE_NAME="SOURCE";
	public static final String TABLE_NAME="CFS_disp";
	public static final String TABLE_CAT="CFS_CAT";
	public static final String CAT_PROD="CATPROD";
	public static final String TABLE_visit="CFS_visit";
	public static final String Table_Area="CFS_area";
	public static final String Table_lot="CFS_Lot";
	public static final String Table_Item="CFS_Item";
	public static final String Table_Party="CFS_party";
	public static final String Table_rownum="CFS_rnum";
	public static final String Table_penpall="CFS_Pallet";
	public static final String Table_pallet="Pallet";
	public static final String Table_cont="containerrec";
	public static final String Table_Contract="contract";
	public static final String Table_PENCONT="PENDINGCONTAINER";
	public static final String Table_PENDPALLET="PENDPALLET";
	public static final String Table_pall_dis="disppallet";
	public static final String Pal_no="Palletno";
	public static final String Pallet_typ="pallettype";
	public static final String Pal_Entdate="Palletdate";
	public static final String Pal_Qty="PalletQty";
	public static final String Pal_Status="Palletstatus";
	public static final String Pal_CFSREC="CFSRECID";
	public static final String KEY_lorryno="lorryno";	
	public static final String KEY_PONO="PONO";
	public static final String KEY_ITEMMASTERID="DISPITEMMASTERID";
	public static final String KEY_ITEMID="DISPITEMID";
	public static final String KEY_PARTYMASTID="DISPPARTYMASTID";
	public static final String KEY_PARTYID="DISPPARTYID";
	public static final String KEY_CATEGORY="DISPCATEGORY";
	public static final String KEY_ID="id";
	public static final String KEY_DISPQTY="DISPQTY";
	public static final String KEY_IMEINO="DISPIMEINO";
	public static final String KEY_DISPID="DISPIBASICID";
	public static final String KEY_docdate="Docdate";
	public static final String KEY_SINO="SINO";
	public static final String KEY_UNIT="TUNIT";
	public static final String LOT_STAT="LOTSTAT";	
	public static final String Ar_ID = "arid";
	public static final String Item_ID = "itid";
	public static final String Item_Name = "itemname";
	public static final String Ar_City = "city";
	public static final String Ar_Name = "area";
	public static final String Lot_ID="lotno";
	public static final String Lot_date="RECEIPTDATE";
	public static final String LOT_TIME="RECEIPTTIME";
	public static final String LOT_PONO="RECPONO";
	public static final String LOT_LORRYNO="RLORRYNO";
	public static final String LOT_PACKTYPE="RPACKINGTYPE";
	public static final String LOT_UNIT="RUNIT";
	public static final String LOT_OPWT="ROPWT";	
	public static final String LOT_CLWT="RCLWT";
	public static final String LOT_TOTQTY="RQTY";
	public static final String LOT_TQTYNOS="RQTYNOS";
	public static final String LOT_ENTRYDATE="RENTDATE";
	public static final String LOT_ITEM="PRODUCT";
	public static final String LOT_DESPSTATUS="DISPATCHSTATUS";
	public static final String LOT_NOOFBAGS="NOOFBAGS";
	public static final String disunit="unit";
	public static final String party="partyname";
	public static final String visit_mill="Millname";
	public static final String visit_contact="viscontact";
	public static final String Visit_stock="visstock";
	public static final String visit_othparty1="visparty1";
	public static final String visit_othparty2="visparty2";
	public static final String visit_othparty3="visparty3";
	public static final String visit_othparty4="visparty4";
	public static final String visit_othparty5="visparty5";
	public static final String visit_othqty1="visqty1";
	public static final String visit_othqty2="visqty2";
	public static final String visit_othqty3="visqty3";
	public static final String visit_othqty4="visqty4";
	public static final String visit_othqty5="visqty5";
	public static final String visit_area="areaname";
	public static final String visit_docdate="docdate";
	public static final String visit_rate1="rate1";
	public static final String visit_rate2="rate2";
	public static final String visit_rate3="rate3";
	public static final String visit_rate4="rate4";
	public static final String visit_rate5="rate5";
	public static final String visit_reason="reason";
	public static final String PENSINO="SINO";
	public static final String PENPONO="PONO";
	public static final String PENDOCDATE="PENDOCDATE";
	public static final String paldocdate="paldoc";
	public static final String PENLORRYNO="LORRYNO";
	public static final String PENRECEIPTQTYMT="RECEIPTQTYMT";
	public static final String PENRECEIPTQTY="RECEIPTQTY";
	public static final String PENITEM="ITEM";
	public static final String PENUOM="UOM";
	public static final String PENDISPSTATUS="DISPSTATUS";
	public static final String CONT_docdate="CONTDOCDATE";
	public static final String CONT_CONTNO="contno";
	public static final String cont_consignee="consignee";
	public static final String cont_product="product";
	public static final String cont_packtype="packingtype";
	public static final String cont_containerno="Containerno";
	public static final String cont_contype="conttype";
	public static final String conttarewt="tarewt";
	public static final String ecno="contno";
	public static final String ecconsignee="consignee";
	public static final String ecproduct="product";
	public static final String ecsubcat="subcategory";
	public static final String ecptype="PTYPE";
	public static final String ecUOM="UOM";
	public static final String contqty="qtt";
	public static final String contSTATUS="STATUS";
	public static final String pen_contID="CONTID";
	public static final String pen_containerno="CONTNO";
	public static final String pen_contDATE="CONTDATE";
	public static final String pen_QTY="CONTQTY";
	public static final String pen_DOCDATE="DOCDATE";
	public static final String pen_PALLETNO="PALLETNO";
	public static final String PEN_PALLETQTY="PALLETQTY";
	public static final String Pallet_nos="PALLNOS";
	public static final String Pallet_nosblocks="nosblocks";
	public static final String Pallet_empwt="PALLNOSEMPTYWT";

	

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null,1 );
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
			
		String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY autoincrement, "+KEY_lorryno+" TEXT, "+KEY_PONO+" TEXT , "+KEY_ITEMMASTERID+" TEXT, "+KEY_ITEMID+" TEXT, "+KEY_PARTYMASTID+" TEXT, "+KEY_PARTYID+" TEXT, "+KEY_CATEGORY+" TEXT, "+KEY_DISPQTY+" INTEGER, "+KEY_IMEINO+" TEXT ,"+KEY_DISPID+" TEXT, "+KEY_SINO+" TEXT, "+KEY_UNIT+" TEXT, "+LOT_STAT+" TEXT, "+LOT_PACKTYPE+" TEXT, "+LOT_NOOFBAGS+" TEXT "+ ")";
		db.execSQL(CREATE_TABLE);
		
		String CREATE_Area = "CREATE TABLE " + TABLE_CAT
				+ "(" + CAT_PROD + " TEXT " + ")";
		db.execSQL(CREATE_Area);
		
		String CREATE_CAT = "CREATE TABLE " + Table_Area
				+ "(" + Ar_ID + " TEXT ," + Ar_City + " TEXT,"
				+ Ar_Name + " TEXT" + ")";
		db.execSQL(CREATE_CAT);
		
		String CREATE_CPENCONT = "CREATE TABLE " + Table_PENCONT
				+ "(" + pen_contID + " TEXT ," + pen_containerno + " TEXT,"
				+ pen_contDATE + " TEXT, " +pen_QTY+" TEXT, "+pen_DOCDATE+" TEXT "+ ")";
		db.execSQL(CREATE_CPENCONT);
		
		String CREATE_CPEN = "CREATE TABLE " + Table_PENDPALLET
				+ "(" + pen_PALLETNO + " TEXT ," + PEN_PALLETQTY + " TEXT "+ ")";
		db.execSQL(CREATE_CPEN);
		
		String CREATE_PALL = "CREATE TABLE " + Table_penpall
				+ "(" + PENSINO + " TEXT ," + PENPONO + " TEXT,"+ PENDOCDATE + " TEXT,"
				+ PENLORRYNO + " TEXT, "+ PENRECEIPTQTYMT + " TEXT, "+ PENRECEIPTQTY + " TEXT, "+ PENITEM + " TEXT, "+ PENUOM + " TEXT, "+ PENDISPSTATUS + " TEXT, "+ Pal_CFSREC + " TEXT, " + LOT_PACKTYPE + " TEXT " + ")";
		db.execSQL(CREATE_PALL);
		
		String CREATE_pencontract = "CREATE TABLE " + Table_Contract
				+ "(" + ecno + " TEXT ," + ecconsignee + " TEXT,"+ ecproduct + " TEXT,"
				+ ecsubcat + " TEXT, "+ ecUOM + " TEXT, "+ecptype+" TEXT "+ ")";
		db.execSQL(CREATE_pencontract); 
		
		String CREATE_CONTAINER = "CREATE TABLE " + Table_cont
				+ "(" + Lot_ID + " INTEGER PRIMARY KEY autoincrement," + CONT_docdate + " TEXT ," + CONT_CONTNO + " TEXT,"+ cont_consignee + " TEXT,"
				+ cont_product + " TEXT, "+ ecptype + " TEXT, "+ contqty + " TEXT, "+contSTATUS+" TEXT, "+cont_packtype+" TEXT, "+cont_containerno+" Text "+ ")";
		db.execSQL(CREATE_CONTAINER); 
		
		String CREATE_LOT = "CREATE TABLE " + Table_lot
				+ "(" + Lot_ID + " INTEGER PRIMARY KEY autoincrement," +KEY_ID +" INTEGER,"+ Lot_date + " TEXT,"
				+ LOT_TIME + " TEXT," + LOT_PONO + " TEXT," + LOT_LORRYNO +" TEXT,"+LOT_PACKTYPE+" TEXT,"+LOT_UNIT+" TEXT,"+
				LOT_OPWT+" INTEGER,"+LOT_CLWT+" INTEGER,"+LOT_TOTQTY+" INTEGER,"+LOT_TQTYNOS+" INTEGER,"+
				LOT_ENTRYDATE+" TEXT, "+LOT_ITEM+" TEXT, "+LOT_DESPSTATUS+" TEXT " +")";
		db.execSQL(CREATE_LOT);
		
		String CREATE_VISIT="CREATE TABLE "+TABLE_visit+" ("+KEY_ID+" INTEGER PRIMARY KEY autoincrement, "+visit_mill+" TEXT, "+visit_contact+" TEXT , "+Visit_stock+" TEXT, "+visit_othparty1+" TEXT, "+visit_othparty2+" TEXT, "+visit_othparty3+" TEXT,"
		+visit_othparty4+" TEXT,"+visit_othparty5+" TEXT,"+visit_othqty1+" TEXT,"+visit_othqty2+" TEXT,"+visit_othqty3+" TEXT,"+visit_othqty4+" TEXT,"+visit_othqty5+" TEXT,"+visit_area+" TEXT,"+visit_docdate+" TEXT,"+visit_rate1+" INTEGER,"+visit_rate2+" INTEGER,"+visit_rate3+" INTEGER,"+visit_rate4+" INTEGER,"+visit_rate5+" INTEGER,"+visit_reason+" TEXT"+")";
		db.execSQL(CREATE_VISIT);		
		
		String CREATE_Item = "CREATE TABLE " + Table_Item
				+ "(" + Item_ID + " TEXT, " + Item_Name + " TEXT, "+disunit+" TEXT "+")";
		db.execSQL(CREATE_Item);
		
		String CREATE_Party = "CREATE TABLE " + Table_Party
				+ "(" + party + " TEXT, " + Ar_Name+" TEXT "+ ")";
		db.execSQL(CREATE_Party);

		String CREATE_pallet = "CREATE TABLE " + Table_pallet
				+ "(" + Pal_no + " TEXT, " + Pal_Entdate+" TEXT, "+Pal_Qty+" TEXT, "+Pal_Status+" TEXT, "+Pal_CFSREC+" TEXT, "+paldocdate+" TEXT, "+Pallet_typ+" TEXT, " +Pallet_nos+" TEXT, "+Pallet_nosblocks+" TEXT, "+Pallet_empwt+" TEXT " +")";
		db.execSQL(CREATE_pallet);
		
		String CREATE_DISPPALLET = "CREATE TABLE " + Table_pall_dis
				+ "(" + Pal_no + " TEXT, " + Pal_Entdate+" TEXT, "+Pal_CFSREC+" TEXT, "+paldocdate+" TEXT "+")";
		db.execSQL(CREATE_DISPPALLET);
		
		
//	onUpgrade(db, 1, 2);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {		
		
		//db.execSQL("ALTER TABLE "+Table_lot+" ADD "+ LOT_stockqty+" TEXT ");
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS "+Table_Area);
		db.execSQL("DROP TABLE IF EXISTS "+Table_lot);
		db.execSQL("DROP TABLE IF EXISTS "+Table_Item);
		db.execSQL("DROP TABLE IF EXISTS "+Table_Party);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_visit);
		onCreate(db);
	}

public void deleteAll()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(Table_Area, null, null);
    db.close();
}

public void catdeleteall()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(TABLE_CAT, null, null);
    db.close();
}

public void itemdeleteAll()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(Table_Item, null, null);
    db.close();
}

public void partyeleteAll()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(Table_Party, null, null);
    db.close();
}

public void DISPDELETEALL()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(TABLE_NAME, null, null);
    db.close();
}

public void PENDELETEALL()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(Table_penpall, null, null);
    db.delete(Table_PENDPALLET, null, null);    
    
    db.close();
}

public void PENDCONTAINERS()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(Table_PENCONT, null, null);
    db.close();
}

public void PENDPALLET()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(Table_pallet, null, null);
    
    db.close();
}

public void PENDCONT()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(Table_Contract, null, null);
    db.close();
}

public void PENDDISP()
{
	SQLiteDatabase db = this.getWritableDatabase();
  
    db.delete(Table_cont, null, null);
    db.close();
}



public int getloadcount() {
	String countQuery = "SELECT  * FROM " + TABLE_NAME;
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor cursor = db.rawQuery(countQuery, null);

	int count = cursor.getCount();
	cursor.close();
	 db.close();
	// return count
	return count;
}

public long load_getrowid() {
	long  lastId = 0;
	String query = "SELECT "+KEY_ID+" from "+ TABLE_NAME+" order by "+KEY_ID+" DESC limit 1";
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor c = db.rawQuery(query,null);
	if (c != null && c.moveToFirst()) {
		lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
	}
	 db.close();
	return lastId;
}

public long LOT_getrowid() {
	long  lastId = 0;
	String query = "SELECT "+Lot_ID+" from "+ Table_lot+" order by "+Lot_ID+" DESC limit 1";
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor c = db.rawQuery(query,null);
	if (c != null && c.moveToFirst()) {
		lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
	}
	 db.close();
	return lastId;
}

public long VISIT_getrowid() {
	long  lastId = 0;
	String query = "SELECT "+KEY_ID+" from "+ TABLE_visit+" order by "+KEY_ID+" DESC limit 1";
	SQLiteDatabase db = this.getReadableDatabase();
	Cursor c = db.rawQuery(query,null);
	if (c != null && c.moveToFirst()) {
		lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
	}
	 db.close();
	return lastId;
}

}


