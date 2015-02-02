package com.isitcom.friendsevent.slidemenu;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.isitcom.freindsevent.constant.Constant;
import com.isitcom.freindsevent.listeview.ConvertImageFromStringToBitmap;
import com.isitcom.freindsevent.menuactivityright.MenuAddFriends;
import com.isitcom.freindsevent.menuactivityright.MenuEditInformation;
import com.isitcom.freindsevent.menuactivityright.MenuListFriend;
import com.isitcom.friendsevent.MainActivity;
import com.isitcom.friendsevent.R;
import com.isitcom.friendsevent.menuactivityleft.MenuCreateEvent;
import com.isitcom.friendsevent.menuactivityleft.MenuEditEvent;
import com.isitcom.friendsevent.menuactivityleft.MenuHome;
import com.isitcom.friendsevent.menuactivityleft.MenuShowInvitation;

public class MainMenuActivity extends Activity {

	int x=0;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ListView mDrawerListRight;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	int[] iconLeft;
	int[] iconRight;
	private ArrayList<String> TextValueLeft;
	private ArrayList<String> TextValueRight;
	String image,img;
	JSONObject usersJsonObject;
	JSONArray data;
	Drawable d;
	private boolean back_flag = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		new Loade().execute();

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerListRight= (ListView) findViewById(R.id.right_drawer);

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// set up the drawer's list view with items and click listener
		TextValueLeft=new ArrayList<String>();
		TextValueLeft.add("Home");
		TextValueLeft.add("Create Event");
		TextValueLeft.add("Edit Event");
		TextValueLeft.add("Show Invitation");



		TextValueRight=new ArrayList<String>();
		TextValueRight.add("List Friends");
		TextValueRight.add("Add Friends");
		TextValueRight.add("Settings");
		TextValueRight.add("Logout");

		iconLeft = new int[] { R.drawable.menu_alerts, R.drawable.menu_charts,
				R.drawable.menu_home,R.drawable.menu_settings };

		iconRight= new int[] { R.drawable.ic_quicksettings, R.drawable.ic_settings,
				R.drawable.ic_movetodefault, R.drawable.ic_movetodefault};

		mDrawerList.setAdapter(new LeftListAdapter(this, TextValueLeft, iconLeft));
		mDrawerListRight.setAdapter(new RightListAdapter(this, TextValueRight, iconRight));

		mDrawerList.setOnItemClickListener(new DrawerLeftItemClickListener());
		mDrawerListRight.setOnItemClickListener(new DrawerRightItemClickListener());
		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.menu_home,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {

				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			selectLeftItem(0);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		MenuItem item=menu.findItem(R.id.action_websearch);

		try{



			item.setIcon(d);
		}
		catch(Exception e){
		}
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view


		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			mDrawerLayout.closeDrawer(Gravity.END);
			return true;
		}

		switch(item.getItemId()) {
		case R.id.action_websearch:
			if(x==0){

				mDrawerLayout.openDrawer(Gravity.END);
				mDrawerLayout.closeDrawer(Gravity.START);
				x=1;
			}
			else if(x==1){

				mDrawerLayout.closeDrawer(Gravity.END);
				x=0;
			}	
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}



	}


	private class DrawerLeftItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectLeftItem(position);
		}
	}

	private void selectLeftItem(int position) {


		FragmentManager fragmentManager = getFragmentManager();


		switch(position){

		case 0:
			fragmentManager.beginTransaction().replace(R.id.content_frame, new MenuHome()).commit();

			break;
		case 1:
			fragmentManager.beginTransaction().replace(R.id.content_frame, new MenuCreateEvent()).commit();

			break;
		case 2:
			fragmentManager.beginTransaction().replace(R.id.content_frame, new MenuEditEvent()).commit();

			break;
		case 3:
			fragmentManager.beginTransaction().replace(R.id.content_frame, new MenuShowInvitation()).commit();

			break;


		}

		// update selected item and title, then close the drawe
		mDrawerList.setItemChecked(position, true);
		setTitle(TextValueLeft.get(position));
		mDrawerLayout.closeDrawer(mDrawerList);

	}



	private class DrawerRightItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectRightItem(position);
		}
	}

	private void selectRightItem(int position) {


		FragmentManager fragmentManager = getFragmentManager();


		switch(position){

		case 0:
			fragmentManager.beginTransaction().replace(R.id.content_frame, new MenuListFriend()).commit();

			break;
		case 1:
			new LoadeId().execute();
			fragmentManager.beginTransaction().replace(R.id.content_frame, new MenuAddFriends()).commit();

			break;
		case 2:
			fragmentManager.beginTransaction().replace(R.id.content_frame, new MenuEditInformation()).commit();

			break;
		case 3:
			SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
			Editor editor = prfs.edit();
			editor.clear();
			editor.commit();
			startActivity(new Intent(getApplicationContext(),MainActivity.class));

			break;
		}

		// update selected item and title, then close the drawe
		mDrawerList.setItemChecked(position, true);
		setTitle(TextValueRight.get(position));
		mDrawerLayout.closeDrawer(mDrawerListRight);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}



	class Loade extends AsyncTask<String, String, String> {
		String	result ;
		String obj;
		JSONObject usersJsonObject;
		JSONArray data;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainMenuActivity.this);
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}


		@Override
		protected String doInBackground(String... arg0) {

			SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
			String user = prfs.getString("user",null);
			System.out.println("vvv"+user);
			String password = prfs.getString("password",null);
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(Constant.URL_IMAGE+"/"+user);


			httpGet.setHeader("Content-Type","application/json");

			try {
				HttpResponse response = httpClient.execute(httpGet);
				image = EntityUtils.toString(response.getEntity());	

			} catch (Exception e) {}

			Bitmap b=ConvertImageFromStringToBitmap.convert(image);			
			d =new BitmapDrawable(MainMenuActivity.this.getResources(),b);	



			return null;
		}

		protected void onPostExecute(String file_url) {

			pDialog.dismiss();

		}


	}


	class LoadeId extends AsyncTask<String, String, String> {
		String	result ;
		String obj;
		JSONObject usersJsonObject;
		JSONArray data;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}


		@Override
		protected String doInBackground(String... arg0) {

			SharedPreferences prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
			String user = prfs.getString("user",null);
			String password = prfs.getString("password",null);
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet1 = new HttpGet(Constant.URL_GET_ID+user+"/"+password);


			httpGet1.setHeader("Content-Type","application/json");
			try {

				HttpResponse response1 = httpClient.execute(httpGet1);
				String idS=EntityUtils.toString(response1.getEntity());
				SharedPreferences preferences = getSharedPreferences("ID", Context.MODE_WORLD_WRITEABLE);
				SharedPreferences.Editor  editor = preferences.edit();
				editor.putString("id_user",idS);
				editor.commit();
				Constant.ID_USER=idS;

			} catch (Exception e) {}

			return null;
		}

		protected void onPostExecute(String file_url) {


		}


	}
	@SuppressWarnings("deprecation")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode){
		case KeyEvent.KEYCODE_BACK:

			AlertDialog cancelAlert=new AlertDialog.Builder(MainMenuActivity.this).create();
			cancelAlert.setTitle("Déconnection");
			cancelAlert.setMessage("Voulez vous se deconnecter ?");
			cancelAlert.setIcon(R.drawable.ic_cancel);

			cancelAlert.setButton("Oui", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					Intent intent=new Intent(getApplicationContext(),MainActivity.class);
					startActivity(intent);

				}
			});

			cancelAlert.setButton2("Non", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();

				}
			});
			cancelAlert.show();

		}


		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {

			System.out.println("back");
		

			Intent myIntent = new Intent(MainMenuActivity.this,com.isitcom.friendsevent.Service.class);

			PendingIntent pendingIntent = PendingIntent.getService(MainMenuActivity.this, 0, myIntent, 0);
			AlarmManager alarmManager = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);

			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 5*1000,pendingIntent);

		
		super.onPause();
	}
	@Override
	protected void onResume() {
		Toast.makeText(getApplicationContext(), "resume", Toast.LENGTH_LONG).show();
		super.onResume();
	}


}