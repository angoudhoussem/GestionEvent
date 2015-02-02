/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.isitcom.friendsevent;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.isitcom.freindsevent.constant.Constant;
import com.isitcom.friendsevent.dbsqlite.DatabaseHelper;
import com.isitcom.friendsevent.slidemenu.MainMenuActivity;



public class MainActivity extends Activity {
	TextView register;
	EditText login,password;
	Button btn_login;
	DatabaseHelper dbHelper;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		register=(TextView) findViewById(R.id.to_register);
		login=(EditText)findViewById(R.id.login_value);
		password=(EditText)findViewById(R.id.password_value);  
		btn_login=(Button) findViewById(R.id.button_login);

		dbHelper=new DatabaseHelper(this);
		btn_login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				if((login.getText().toString().length()!=0) && (password.getText().toString().length()!=0)){
					dbHelper.open();

					Cursor cur=dbHelper.fetchUser(login.getText().toString(), password.getText().toString());

					if(cur.getCount()!=0){
						Intent intent=new Intent(getApplicationContext(),MainMenuActivity.class);
						startActivity(intent);
						SharedPreferences preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_WORLD_WRITEABLE);
						SharedPreferences.Editor  editor = preferences.edit();
						editor.putString("user",login.getText().toString());
						editor.putString("password",password.getText().toString());
						editor.commit();
						Constant.ACTUAL_USER=login.getText().toString();
						Constant.ACTUAL_PASSWORD=password.getText().toString();
						login.setText("");
						password.setText("");
						dbHelper.close();
					}
					else
						Toast.makeText(getApplicationContext(), "Entrer login et mot de passe valides", Toast.LENGTH_SHORT).show();

				}

				else {
					Toast.makeText(getApplicationContext(), "Entrer login et mot de passe valides", Toast.LENGTH_SHORT).show();
				}

			}
		});
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), Registration.class));

			}
		});
	}

	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:
			moveTaskToBack(true);
			return true;
		
		}
		
		return super.onKeyDown(keyCode, event);
	}

}