package com.vaighaiprotein;

import java.util.ArrayList;

import com.vaighaiprotein.DisplayAdapter.Holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Displaylot extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> Lot_id;
	private ArrayList<String> partyname;
	private ArrayList<String> Qty;
	private ArrayList<String> purrate;
	


	public Displaylot(Context c, ArrayList<String> Lot_id, ArrayList<String> partyname,ArrayList<String> Qty,ArrayList<String> purrate) {
		this.mContext = c;
		this.Lot_id = Lot_id;	
		this.partyname = partyname;
		this.Qty = Qty;
		this.purrate = purrate;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return Lot_id.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int pos, View child, ViewGroup parent) {
		Holder mHolder;
		LayoutInflater layoutInflater;
		if (child == null) {
			layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			child = layoutInflater.inflate(R.layout.lotdisp, null);
			mHolder = new Holder();
			mHolder.Lot_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.paryname = (TextView) child.findViewById(R.id.txt_lName);
			mHolder.Qty = (TextView) child.findViewById(R.id.txt_branch);
			mHolder.purrate = (TextView) child.findViewById(R.id.txt_Qty);
			
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.Lot_id.setText(Lot_id.get(pos));	
		mHolder.paryname.setText(partyname.get(pos));
		mHolder.Qty.setText(Qty.get(pos));
		mHolder.purrate.setText(purrate.get(pos));		
		return child;
	}

	public class Holder {
		TextView Lot_id;
		TextView paryname;
		TextView Qty;
		TextView purrate;
		
	}

}
