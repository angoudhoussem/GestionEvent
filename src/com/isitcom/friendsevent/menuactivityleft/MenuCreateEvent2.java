package com.isitcom.friendsevent.menuactivityleft;

import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.isitcom.freindsevent.bean.UserListSearchView;
import com.isitcom.freindsevent.constant.Constant;
import com.isitcom.freindsevent.listeview.CustomListFriendsAdabter;
import com.isitcom.friendsevent.R;

public class MenuCreateEvent2 extends Fragment {
	
	ProgressDialog pDialog;
	ListView listfriends;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
      
        View view = inflater.inflate(R.layout.menu_create_event_2, container, false);
        listfriends=(ListView)view.findViewById(R.id.list_add);
        
        new  Loade().execute();
		return view ;
	}
	
	class Loade extends AsyncTask<String, String, String> {
		String	result ;
		String obj;
		JSONObject usersJsonObject;
		JSONArray data;
		ArrayList<UserListSearchView> alluser=new ArrayList<UserListSearchView>();
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}


		@Override
		protected String doInBackground(String... arg0) {

			UserListSearchView userListeView;
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(Constant.URL_SEARCH);
			httpGet.setHeader("Content-Type","application/json");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());

				JSONArray a=new JSONArray(result);
				usersJsonObject=new JSONObject();
				usersJsonObject.put("data", a);
				data = usersJsonObject.getJSONArray("data");

				for (int t=0; t<data.length(); t++) {

					userListeView=new UserListSearchView();
					JSONObject JObject = data.getJSONObject(t);
					userListeView.setId(JObject.getInt("iduser"));
					userListeView.setPseudo(JObject.getString("pseudouser"));
					userListeView.setAdresse(JObject.getString("adresseuser"));
					userListeView.setImage(JObject.getString("imageuser"));
					alluser.add(userListeView);

				}
			} catch (Exception e) {

			}


			return null;
		}

		protected void onPostExecute(String file_url) {

			pDialog.dismiss();
			listfriends.setAdapter(new CustomListFriendsAdabter(getActivity(), alluser));

			System.out.println(usersJsonObject);
		}


	}

	
}