package com.vaighaiprotein;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DisplayAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<String> id;
	private ArrayList<String> firstName;
	private ArrayList<String> lastName;
	private ArrayList<String> freight;
	private ArrayList<String> other;
	private ArrayList<String> Branch;
	private ArrayList<String> loaddate;
	

	public DisplayAdapter(Context c, ArrayList<String> id, ArrayList<String> lname,ArrayList<String> branch,ArrayList<String> freight,ArrayList<String> other) {
		this.mContext = c;

		this.id = id;	
		this.lastName = lname;
		this.freight = freight;
		this.other = other;
		this.Branch=branch;	
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return id.size();
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
			child = layoutInflater.inflate(R.layout.listcell, null);
			mHolder = new Holder();
			mHolder.txt_id = (TextView) child.findViewById(R.id.txt_id);
			mHolder.txt_lName = (TextView) child.findViewById(R.id.txt_lName);
			mHolder.txt_freight = (TextView) child.findViewById(R.id.txt_freight);
			mHolder.txt_other = (TextView) child.findViewById(R.id.txt_other);
			mHolder.txt_branch=(TextView) child.findViewById(R.id.txt_branch);
			child.setTag(mHolder);
		} else {
			mHolder = (Holder) child.getTag();
		}
		mHolder.txt_id.setText(id.get(pos));
		mHolder.txt_lName.setText(lastName.get(pos));
		mHolder.txt_freight.setText(freight.get(pos));
		mHolder.txt_other.setText(other.get(pos));
		mHolder.txt_branch.setText(Branch.get(pos));	
		return child;
	}

	public class Holder {
		TextView txt_id;
		TextView txt_fName;
		TextView txt_lName;
		TextView txt_freight;
		TextView txt_other;
		TextView txt_branch;
		TextView txt_date;
	}
	


}

