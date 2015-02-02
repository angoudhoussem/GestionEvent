package com.isitcom.freindsevent.listeview;


import java.util.ArrayList;

import com.isitcom.freindsevent.bean.UserListSearchView;
import com.isitcom.friendsevent.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class CustomListFriendsForEventAdabter extends BaseAdapter {

	private ArrayList<UserListSearchView> listData;

	private LayoutInflater layoutInflater;

	public CustomListFriendsForEventAdabter(Context context, ArrayList<UserListSearchView> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.template_listview_event, null);
			holder = new ViewHolder();
			holder.pseudo = (TextView) convertView.findViewById(R.id.textView1);
			holder.adresse = (TextView) convertView.findViewById(R.id.textView2);
			holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		UserListSearchView newsItem = (UserListSearchView) listData.get(position);

		holder.pseudo.setText(newsItem.getPseudo());
		holder.adresse.setText(newsItem.getAdresse());
		holder.image.setImageBitmap(ConvertImageFromStringToBitmap.convert(newsItem.getImage()));

		return convertView;
	}

	static class ViewHolder {
		TextView pseudo;
		TextView adresse;
		ImageView image;

	}
}
