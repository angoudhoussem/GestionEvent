package com.isitcom.friendsevent.dbsqlite;

import java.util.ArrayList;
import java.util.List;

import com.isitcom.freindsevent.bean.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DatabaseHelper extends SQLiteOpenHelper{
	SQLiteDatabase db;

	static final String dbname ="FriendsEventtDatabase";

	public static final String USER_TABLE="user";
	public static final String COL_ID="userID";
	public final static String COL_LOGIN="userLogin";
	public static final String COL_PASSWORD="userPassword";
	public final static String COL_PSEUDO="userName";
	public final static String COL_EMAIL="email";
	public final static String COL_PHOTO="photo";

	public DatabaseHelper(Context context) {
		super(context, dbname, null,1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE IF NOT EXISTS "+USER_TABLE+" ("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_PSEUDO+" TEXT NOT NULL, "+COL_LOGIN+" TEXT NOT NULL, "+COL_PASSWORD+" TEXT NOT NULL, "+COL_EMAIL+" TEXT, "+COL_PHOTO+" TEXT);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE);
		onCreate(db);

	}

	public void open() throws SQLiteException{

		db = this.getWritableDatabase();

	}
	public void close(){

		db.close();
	}

	public void AddUser(User usr){

		ContentValues cv=new ContentValues();

		cv.put(COL_PSEUDO, usr.getPseudo());
		cv.put(COL_LOGIN, usr.getLogin());
		cv.put(COL_PASSWORD, usr.getPassword());
		cv.put(COL_EMAIL, usr.getEmail());
		cv.put(COL_PHOTO, usr.getImage());

		db.insert(USER_TABLE, null, cv);
		db.close();

	}

	public int getUserCount(){

		Cursor cs=db.rawQuery("select * from "+USER_TABLE, null);
		int count=cs.getCount();
		cs.close();
		return count;
	}

	public Cursor getAllUser(){

		Cursor cs=db.rawQuery("select * from "+USER_TABLE, null);
		return cs;
	}

	public String getImageUrl(String login,String password){
		Cursor cursor=fetchUser(login, password);
		User user=buildUserFromCursor(cursor);
		return user.getImage();
	}

	public int UpdateUser(int id,String pseudo,String login,String password,String email,String image){


		ContentValues cv=new ContentValues();
		cv.put(COL_PSEUDO,pseudo);
		cv.put(COL_LOGIN, login);
		cv.put(COL_PASSWORD, password);
		cv.put(COL_EMAIL, email);
		cv.put(COL_PHOTO, image);

		int i=db.update(USER_TABLE, cv, COL_ID + "=" + id, null);

		return i;

	}

	public int updateUser(int id,final User usr){

		ContentValues cv = new ContentValues();
		cv.put(COL_PSEUDO, usr.getPseudo());
		cv.put(COL_LOGIN, usr.getLogin());
		cv.put(COL_PASSWORD, usr.getPassword());
		cv.put(COL_EMAIL, usr.getEmail());
		cv.put(COL_PHOTO, usr.getImage());

		return db.update(USER_TABLE, cv, COL_ID + " = "+id,null);

	}

	public boolean DeleteEmployee(int id){
		boolean etat=false;

		etat =db.delete(USER_TABLE, COL_ID + "=" +id, null)>0;
		Log.d("etat",String.valueOf(etat));

		return etat;

	}

	public List<String> selection(){
		final List user=new ArrayList<String>();
		final Cursor cursor=db.query(USER_TABLE, new String[]{COL_ID, COL_LOGIN}, null, null, null, null, null);
		cursor.moveToFirst();
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			user.add(cursor.getString(1));


		}

		return user;
	}
	public Cursor fetchUser(String login, String motdepasse){
		Cursor cursor = null;

		cursor=db.query(USER_TABLE, new String[]{COL_ID, COL_PSEUDO, COL_LOGIN, COL_PASSWORD,COL_EMAIL,COL_PHOTO}, COL_LOGIN + "='" + login + "' AND " + COL_PASSWORD + "='" + motdepasse + "'", null, null, null, null);

		if(cursor != null){
			cursor.moveToFirst();
		}


		return cursor;


	}


	public User buildUserFromCursor(final Cursor cursor){
		User user=new User();
		user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
		user.setPseudo(cursor.getString(cursor.getColumnIndexOrThrow(COL_PSEUDO)));
		user.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(COL_LOGIN)));
		user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)));
		user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
		user.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COL_PHOTO)));

		return user;

	}

}


