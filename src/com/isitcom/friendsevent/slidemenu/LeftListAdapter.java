package com.isitcom.friendsevent.slidemenu;

import java.util.ArrayList;

import com.isitcom.friendsevent.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LeftListAdapter extends BaseAdapter{
	Context context;
	int[] mIcon;
	LayoutInflater inflater;
	private ArrayList<String> TextValue;

	public LeftListAdapter(Context context, ArrayList<String> TextValue,int[] icon) {
		 this.context = context;
		    this.TextValue = TextValue;
		    this.mIcon = icon;

		    inflater = (LayoutInflater) context
		            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}


	@Override
	public View getView(int position, View coverView, ViewGroup parent) {
		 TextView txtTitle;
		    ImageView imgIcon;
		    View itemView = inflater.inflate(R.layout.drawer_list_item, parent,
		            false);

		    // Locate the TextViews in drawer_list_item.xml
		    txtTitle = (TextView) itemView.findViewById(R.id.textView1);
		   

		    // Locate the ImageView in drawer_list_item.xml
		    imgIcon = (ImageView) itemView.findViewById(R.id.img1);

		    // Set the results into TextViews
		    txtTitle.setText(TextValue.get(position));
		

		    // Set the results into ImageView
		    imgIcon.setImageResource(mIcon[position]);

		    return itemView;

	}


	@Override
	public int getViewTypeCount() {
	    return super.getViewTypeCount();
	}

	@Override
	public int getItemViewType(int position) {
	    return super.getItemViewType(position);
	}

	@Override
	public int getCount() {
	    return TextValue.size();
	}

	@Override
	public Object getItem(int position) {
	    return TextValue.get(position);
	}

	@Override
	public long getItemId(int position) {
	    return position;
	}
}
