package com.isitcom.friendsevent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.isitcom.freindsevent.bean.User;
import com.isitcom.freindsevent.constant.Constant;
import com.isitcom.friendsevent.dbsqlite.DatabaseHelper;
import com.isitcom.friendsevent.slidemenu.MainMenuActivity;

public class Registration extends Activity {
	EditText pseudo,login,password,email,adresse;
	Button actionLogin;
	ProgressDialog pDialog;
	SQLiteDatabase db;
	DatabaseHelper dbHelper;
	ImageView icon;
	boolean etat=false;
	private static int RESULT_LOAD_IMAGE = 1;
	String picturePath;
	Uri selectedImage=null;
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		actionLogin=(Button)findViewById(R.id.btn_register);
		pseudo=(EditText)findViewById(R.id.registration_pseudo);
		login=(EditText)findViewById(R.id.registration_login);
		password=(EditText)findViewById(R.id.registration_password);
		email=(EditText)findViewById(R.id.registration_email);
		adresse=(EditText)findViewById(R.id.registration_adresse);
		icon=(ImageView)findViewById(R.id.registration_title_icon);
		dbHelper=new DatabaseHelper(this);


		icon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});


		actionLogin.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {

				if((login.getText().toString().length()!=0) & (password.getText().toString().length()!=0)){
					new Loade().execute();
				}
				else
					Toast.makeText(getApplicationContext(), "login et mot de passe sont obligatoires", Toast.LENGTH_LONG).show();

			}
		});
	}


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
			selectedImage = data.getData();
			Constant.ACTUAL_IMAGE_PATH=selectedImage;

			try{
				icon.setImageBitmap(scaleImage(this,selectedImage));
			}
			catch(Exception e){

			}
		}

	}

	public static Bitmap scaleImage(Context context, Uri photoUri) throws IOException {
		InputStream is = context.getContentResolver().openInputStream(photoUri);
		BitmapFactory.Options dbo = new BitmapFactory.Options();
		dbo.inJustDecodeBounds = true;
		int MAX_IMAGE_DIMENSION=140;
		BitmapFactory.decodeStream(is, null, dbo);
		is.close();

		int rotatedWidth, rotatedHeight;
		int orientation = getOrientation(context, photoUri);

		if (orientation == 90 || orientation == 270) {
			rotatedWidth = dbo.outHeight;
			rotatedHeight = dbo.outWidth;
		} else {
			rotatedWidth = dbo.outWidth;
			rotatedHeight = dbo.outHeight;
		}

		Bitmap srcBitmap;
		is = context.getContentResolver().openInputStream(photoUri);
		if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
			float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
			float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
			float maxRatio = Math.max(widthRatio, heightRatio);

			// Create the bitmap from file
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = (int) maxRatio;
			srcBitmap = BitmapFactory.decodeStream(is, null, options);
		} else {
			srcBitmap = BitmapFactory.decodeStream(is);
		}
		is.close();

		if (orientation > 0) {
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);

			srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
					srcBitmap.getHeight(), matrix, true);
		}

		String type = context.getContentResolver().getType(photoUri);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (type.equals("image/png")) {
			srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		} else if (type.equals("image/jpg") || type.equals("image/jpeg")) {
			srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		}
		byte[] bMapArray = baos.toByteArray();
		baos.close();
		return BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);
	}


	private String getEncodeData(Bitmap img) {
		String encodedimage1 = null;
		if (img != null) {
			try {

				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
				img.compress(Bitmap.CompressFormat.JPEG, 10, baos); //bm is the bitmap object 
				byte[] b = baos.toByteArray();
				encodedimage1= Base64.encodeToString(b, Base64.DEFAULT);
			} catch (Exception e) {
				System.out
				.println("Exception: In getEncodeData" + e.toString());
			}
		}
		return encodedimage1;
	}

	public static int getOrientation(Context context, Uri photoUri) {
		/* it's on the external media. */
		Cursor cursor = context.getContentResolver().query(photoUri,
				new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

		if (cursor.getCount() != 1) {
			return -1;
		}

		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	class Loade extends AsyncTask<String, String, String> {
		String	result ;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Registration.this);
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}


		@Override
		protected String doInBackground(String... arg0) {
			String codeImage=null;
			try{
				codeImage=getEncodeData(scaleImage(getApplicationContext(), selectedImage));
				codeImage=URLEncoder.encode(codeImage, "UTF-8"); 
				System.out.println(codeImage);
			}
			catch(Exception e){

			}
			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);

			HttpGet httpGet = new HttpGet(Constant.URL_REGISTER+pseudo.getText().toString()+"/"+login.getText().toString()+"/"+password.getText().toString()+"/"+email.getText().toString()+"/"+adresse.getText().toString()+"/"+codeImage);
			httpGet.setHeader("Content-Type","text/plain");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());


			} catch (Exception e) {

			}

			if(result.equals("yes")){
				dbHelper.open();
				User user=new User( pseudo.getText().toString(),login.getText().toString(), password.getText().toString(), email.getText().toString(),selectedImage.toString());
				dbHelper.AddUser(user);
				dbHelper.close();
				pDialog.cancel();
				etat=true;
			}
			else {
				etat=false;
			}

			return null;
		}

		protected void onPostExecute(String file_url) {

			System.out.println("moha"+result);
			if(etat){

				Constant.ACTUAL_USER=login.getText().toString();
				Constant.ACTUAL_PASSWORD=password.getText().toString();
				Toast.makeText(getApplicationContext(),"inscription réussi", Toast.LENGTH_LONG).show();
				startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
			}
			else {
				pDialog.cancel();
				Toast.makeText(getApplicationContext(), "Ce login est utilisé", Toast.LENGTH_LONG).show();
			}
		}
	}
}
