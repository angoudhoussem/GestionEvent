package com.isitcom.freindsevent.menuactivityright;

import java.util.ArrayList;
import java.util.Date;

import net.londatiga.android.ActionItem;
import net.londatiga.android.QuickAction;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.isitcom.freindsevent.bean.UserListSearchView;
import com.isitcom.freindsevent.constant.Constant;
import com.isitcom.freindsevent.listeview.CustomListFriendsAdabter;
import com.isitcom.freindsevent.menuactivityright.MenuListFriend.Loade;
import com.isitcom.freindsevent.menuactivityright.MenuListFriend.sendInvitation;
import com.isitcom.friendsevent.R;

public class MenuAddFriends extends Fragment {

	ProgressDialog pDialog;
	ListView listSearchUsers;
	private static final int ID_ADD = 1;
	private static final int ID_ACCEPT = 2;
	private static final int ID_UPLOAD = 3;
	UserListSearchView userSearch;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.menu_add_friends, container, false);
		listSearchUsers=(ListView)view.findViewById(R.id.list_add);
		listSearchUsers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		new Loade().execute();

		
		ActionItem addItem = new ActionItem(ID_ADD, "Add", getResources().getDrawable(R.drawable.ic_add));
		final QuickAction mQuickAction 	= new QuickAction(getActivity());

		mQuickAction.addActionItem(addItem);

		mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				ActionItem actionItem = quickAction.getActionItem(pos);

				if (actionId == ID_ADD) {

					new sendInvitation().execute();


				} else {
					Toast.makeText(getActivity(), actionItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
				}
			}
		});

		mQuickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
			@Override
			public void onDismiss() {

			}
		});

		listSearchUsers.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2,
					long arg3) {
				//		NewsItem position=(NewsItem) maListViewPerso.getItemAtPosition(arg2);
				userSearch=(UserListSearchView)listSearchUsers.getItemAtPosition(arg2);
				mQuickAction.show(v);


			}


		});

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
			listSearchUsers.setAdapter(new CustomListFriendsAdabter(getActivity(), alluser));

			System.out.println(usersJsonObject);
		}


	}

	class sendInvitation extends AsyncTask<String, String, String> {
		String	result ;
		String obj;
		JSONObject usersJsonObject;
		JSONArray data;


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
			String id = Constant.ID_USER;
			System.out.println(id);
			Date currentDate = new Date(System.currentTimeMillis());
			String dateS=String.valueOf(currentDate);
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(Constant.URL_SEND_FRIENDS_INVITATION+id+"/"+userSearch.getId());
			httpGet.setHeader("Content-Type","text/plain; charset=utf-8");
			try {
				HttpResponse response = httpClient.execute(httpGet);

			} 
			catch (Exception e) {

			}


			return null;
		}

		protected void onPostExecute(String file_url) {

			pDialog.dismiss();
			Toast.makeText(getActivity(), "Invitation envoyer", Toast.LENGTH_LONG).show();

		}


	}

}