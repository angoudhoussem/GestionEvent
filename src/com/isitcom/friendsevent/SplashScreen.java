package com.isitcom.friendsevent;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.isitcom.friendsevent.dbsqlite.DatabaseHelper;
import com.isitcom.friendsevent.slidemenu.MainMenuActivity;

public class SplashScreen extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
		final String user = prfs.getString("user", null);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			public void run() {

				if(!(user == null)){
				Intent intent=new Intent(getApplicationContext(), MainMenuActivity.class);
				startActivity(intent);
				}
				else {
					Intent intent=new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
				}
			}

		},5000);
		
		
	
	}
}
