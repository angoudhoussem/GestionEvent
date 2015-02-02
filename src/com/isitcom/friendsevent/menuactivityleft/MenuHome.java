package com.isitcom.friendsevent.menuactivityleft;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.isitcom.friendsevent.R;

public class MenuHome extends Fragment {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.menu_home_event, container, false);
		view.setKeepScreenOn(true);
		
		
		ImageView img1=(ImageView)view.findViewById(R.id.img1);
		ImageView img11=(ImageView)view.findViewById(R.id.img11);
		ImageView img2=(ImageView)view.findViewById(R.id.img2);
		ImageView img22=(ImageView)view.findViewById(R.id.img22);
		ImageView img3=(ImageView)view.findViewById(R.id.img3);
		ImageView img4=(ImageView)view.findViewById(R.id.userimg);
		
		ImageView f1=(ImageView)view.findViewById(R.id.imageView1);
		ImageView f2=(ImageView)view.findViewById(R.id.imageView5);
		ImageView f3=(ImageView)view.findViewById(R.id.imageView2);
		ImageView face1=(ImageView)view.findViewById(R.id.imageView3);
		ImageView face2=(ImageView)view.findViewById(R.id.imageView6);
		ImageView face3=(ImageView)view.findViewById(R.id.imageView4);
		
		ImageView in1=(ImageView)view.findViewById(R.id.imageView7);
		ImageView in2=(ImageView)view.findViewById(R.id.imageView8);
		ImageView in3=(ImageView)view.findViewById(R.id.imageView9);
		
		ImageView mapstext=(ImageView)view.findViewById(R.id.imageView10);
		ImageView maps_icon=(ImageView)view.findViewById(R.id.imageView11);	
		
		ImageView marq1=(ImageView)view.findViewById(R.id.imageView12);
		ImageView marq2=(ImageView)view.findViewById(R.id.imageView13);
		ImageView marq3=(ImageView)view.findViewById(R.id.imageView14);
		
		ImageView welcome=(ImageView)view.findViewById(R.id.imageView15);

		Animation localAnimation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate1);
		localAnimation1.reset();		
		img1.startAnimation(localAnimation1);

		Animation localAnimation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate1);
		localAnimation2.reset();
		img11.startAnimation(localAnimation2);

		Animation localAnimation3 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate2);
		localAnimation3.reset();
		localAnimation3.setStartOffset(2000);
		img2.startAnimation(localAnimation3);

		Animation localAnimation4 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate2);	
		localAnimation4.reset();
		localAnimation4.setStartOffset(2000);
		img22.startAnimation(localAnimation4);

		Animation localAnimation5 = AnimationUtils.loadAnimation(getActivity(), R.anim.translateout);
		localAnimation5.reset();
		localAnimation5.setStartOffset(4000);
		img3.startAnimation(localAnimation5);
		
		Animation localAnimation6 = AnimationUtils.loadAnimation(getActivity(), R.anim.no_anim);
		localAnimation6.reset();
		localAnimation6.setStartOffset(7000);
		img4.startAnimation(localAnimation6);
		
		Animation localAnimation7 = AnimationUtils.loadAnimation(getActivity(), R.anim.no_anim1);
		localAnimation7.reset();
		localAnimation7.setStartOffset(9000);
		f1.startAnimation(localAnimation7);
		face1.startAnimation(localAnimation7);
		
		Animation localAnimation8 = AnimationUtils.loadAnimation(getActivity(), R.anim.no_anim2);
		localAnimation8.reset();
		localAnimation8.setStartOffset(10000);
		f2.startAnimation(localAnimation8);
		face2.startAnimation(localAnimation8);
		
		Animation localAnimation9 = AnimationUtils.loadAnimation(getActivity(), R.anim.no_anim3);
		localAnimation9.reset();
		localAnimation9.setStartOffset(11000);
		f3.startAnimation(localAnimation9);
		face3.startAnimation(localAnimation9);
		
		Animation localAnimation10 = AnimationUtils.loadAnimation(getActivity(), R.anim.no_anim4);
		localAnimation10.reset();
		localAnimation10.setStartOffset(12000);
		in1.startAnimation(localAnimation10);
		in2.startAnimation(localAnimation10);
		in3.startAnimation(localAnimation10);
		
		
		
		Animation localAnimation11 = AnimationUtils.loadAnimation(getActivity(), R.anim.translateoutnew);
		localAnimation11.reset();
		localAnimation11.setStartOffset(13500);
		mapstext.startAnimation(localAnimation11);
		
		
		Animation localAnimation15 = AnimationUtils.loadAnimation(getActivity(), R.anim.map_anim);
		localAnimation15.reset();
		localAnimation15.setStartOffset(16000);
		maps_icon.startAnimation(localAnimation15);
		
		
		
		Animation localAnimation12 = AnimationUtils.loadAnimation(getActivity(), R.anim.marqeur1);
		localAnimation12.reset();
		localAnimation12.setStartOffset(17000);
		marq1.startAnimation(localAnimation12);
		
		
		Animation localAnimation13 = AnimationUtils.loadAnimation(getActivity(), R.anim.marqeur2);
		localAnimation13.reset();
		localAnimation13.setStartOffset(19000);
		marq2.startAnimation(localAnimation13);
	
		
		Animation localAnimation14 = AnimationUtils.loadAnimation(getActivity(), R.anim.marqeur3);
		localAnimation14.reset();
		localAnimation14.setStartOffset(21000);
		marq3.startAnimation(localAnimation14);
		
		Animation localAnimation16 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate_welcome);
		localAnimation16.reset();
		localAnimation16.setStartOffset(23500);
		welcome.startAnimation(localAnimation16);
	
		return view ;
	}
}

