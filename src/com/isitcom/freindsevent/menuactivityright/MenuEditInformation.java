package com.isitcom.freindsevent.menuactivityright;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.isitcom.freindsevent.bean.User;
import com.isitcom.freindsevent.constant.Constant;
import com.isitcom.friendsevent.R;
import com.isitcom.friendsevent.dbsqlite.DatabaseHelper;
import com.isitcom.friendsevent.slidemenu.MainMenuActivity;

public class MenuEditInformation extends Fragment{

	String login_txt,password_txt;
	DatabaseHelper dbHelper;
	Cursor cursor;
	User user;
	EditText login,pseudo,password,email;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.menu_edit_information, container, false);

		login=(EditText)view.findViewById(R.id.settings_login);
		pseudo=(EditText)view.findViewById(R.id.settings_pseudo);
		password=(EditText)view.findViewById(R.id.settings_password);
		email=(EditText)view.findViewById(R.id.settings_email);
		Button save=(Button)view.findViewById(R.id.ne);

		login_txt=Constant.ACTUAL_USER;
		password_txt=Constant.ACTUAL_PASSWORD;

		dbHelper=new DatabaseHelper(getActivity());
		dbHelper.open();
		cursor=dbHelper.fetchUser(login_txt, password_txt);
		user=dbHelper.buildUserFromCursor(cursor);
		dbHelper.close();

		login.setText(user.getLogin());
		pseudo.setText(user.getPseudo());
		password.setText(user.getPassword());
		email.setText(user.getEmail());

		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {


				boolean etat=true;
				String loginUpdate=login.getText().toString();	
				String passwordUpdate=password.getText().toString();
				String pseudoUpdate=pseudo.getText().toString();
				String emailUpdate=email.getText().toString();


				Constant.ACTUAL_USER=loginUpdate;
				Constant.ACTUAL_PASSWORD=passwordUpdate;

				dbHelper.open();

				if(password.getText().toString().equals(Constant.ACTUAL_PASSWORD) && login.getText().toString().equals(Constant.ACTUAL_USER)){
					User userNew=new User(pseudoUpdate,loginUpdate,passwordUpdate,emailUpdate);
					int i=dbHelper.updateUser(user.getId(),userNew);
					Toast.makeText(getActivity(),"modification reussi", Toast.LENGTH_LONG).show();
					startActivity(new Intent(getActivity(),MainMenuActivity.class));

				}

				else if((login.getText().toString().length()!=0) & (password.getText().toString().length()!=0)){

					if(dbHelper.selection().size()>0){

						for(int i=0;i<dbHelper.selection().size();i++){
							if(dbHelper.selection().get(i).toString().compareTo(login.getText().toString())==0 & i==dbHelper.selection().size()-1){
								Toast.makeText(getActivity(), "Ce login est utilise", Toast.LENGTH_LONG).show();
								etat=false;
							}
						}	
						if(etat) {
							User userNew=new User(pseudoUpdate,loginUpdate,passwordUpdate,emailUpdate);
							int i=dbHelper.updateUser(user.getId(),userNew);
							Toast.makeText(getActivity(),"modification reussi", Toast.LENGTH_LONG).show();
							startActivity(new Intent(getActivity(),MainMenuActivity.class));

						}
					}

					else {

						User userNew=new User(pseudoUpdate,loginUpdate,passwordUpdate,emailUpdate);
						int i=dbHelper.updateUser(user.getId(),userNew);
						Toast.makeText(getActivity(),"modification reussi", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getActivity(),MainMenuActivity.class));
					}	


				}
				else
					Toast.makeText(getActivity(), "login et mot de passe sont obligatoires", Toast.LENGTH_LONG).show();

				dbHelper.close();




			}
		});


		return view ;
	}
}
