package com.isitcom.friendsevent.menuactivityleft;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isitcom.friendsevent.R;

public class MenuEditEvent extends Fragment {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
      
        View view = inflater.inflate(R.layout.menu_edit_event, container, false);

		return view ;
	}
}
