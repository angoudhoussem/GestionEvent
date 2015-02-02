package com.isitcom.friendsevent;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.isitcom.freindsevent.bean.NotificationUser;
import com.isitcom.freindsevent.bean.UserListSearchView;
import com.isitcom.freindsevent.constant.Constant;
import com.isitcom.friendsevent.slidemenu.MainMenuActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class Service extends android.app.Service {

	JSONObject usersJsonObject;
	JSONArray data;




	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		new LoadeId().execute();

		return Service.START_NOT_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	private final void createNotification(String pseudo){
		Intent intent = new Intent(this, MainMenuActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		// build notification
		// the addAction re-use the same intent to keep the example short
		Notification n  = new NotificationCompat.Builder(this)
		.setContentTitle("New invitation from "+pseudo)
		.setContentText("Subject")
		.setSmallIcon(R.drawable.icon)
		.setContentIntent(pIntent)
		.setAutoCancel(true)
		.build();

		n.defaults |= Notification.DEFAULT_SOUND;
		n.defaults |= Notification.DEFAULT_VIBRATE;

		NotificationManager notificationManager = 
				(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		notificationManager.notify(0, n); 
	}

	class LoadeId extends AsyncTask<String, String, String> {
		String	result ;
		String obj;
		JSONObject usersJsonObject;
		JSONArray data;
		ArrayList<NotificationUser> alluser;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}


		@Override
		protected String doInBackground(String... arg0) {

			SharedPreferences prfs = getSharedPreferences("ID", Context.MODE_PRIVATE);
			String user = prfs.getString("id_user",null);

			System.out.println("id"+user);
			alluser=new ArrayList<NotificationUser>();
			String result=null;
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(Constant.URL_VERIFY_INVITATION+user);
			httpGet.setHeader("Content-Type","application/json");

			try {

				HttpResponse response = httpClient.execute(httpGet);

				result = EntityUtils.toString(response.getEntity());
				System.out.println("result"+result);
				JSONArray a=new JSONArray(result);
				usersJsonObject=new JSONObject();
				usersJsonObject.put("data", a);
				data = usersJsonObject.getJSONArray("data");
				
				NotificationUser notUser;

				for (int t=0; t<data.length(); t++) {

					notUser=new NotificationUser();
					JSONObject JObject = data.getJSONObject(t);

					notUser.setPseudo(JObject.getString("pseudo"));

					alluser.add(notUser);

				}
			}
			catch(Exception e ){

			}
			return null;
		}

		protected void onPostExecute(String file_url) {

			if(!alluser.isEmpty()){
				for(int i=0;i<alluser.size();i++){
					createNotification(alluser.get(i).getPseudo());
				}

			}
		}
	}

	@Override
	public void onCreate() {
		
		super.onCreate();
	}
}
