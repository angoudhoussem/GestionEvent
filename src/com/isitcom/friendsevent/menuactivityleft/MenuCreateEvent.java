package com.isitcom.friendsevent.menuactivityleft;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.isitcom.freindsevent.constant.Constant;
import com.isitcom.friendsevent.R;

public class MenuCreateEvent extends Fragment {

	ImageView iconEvent,DateChoise,timeChoise;
	EditText titleEvent,adresseEvent;
	Button next;
	TextView date,time;

	private static int RESULT_LOAD_IMAGE = 1;
	static final int DATE_DIALOG_ID = 0;
	static final int HOUR_DIALOG_ID = 1;
	Uri selectedImage=null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.menu_create_event_1, container, false);

		iconEvent=(ImageView)view.findViewById(R.id.imageView1);
		DateChoise=(ImageView)view.findViewById(R.id.img_date_event);
		timeChoise=(ImageView)view.findViewById(R.id.img_time_event);
		titleEvent=(EditText)view.findViewById(R.id.title_event);
		adresseEvent=(EditText)view.findViewById(R.id.place_event);
		next=(Button)view.findViewById(R.id.btn_next);
		date=(TextView)view.findViewById(R.id.text_date);
		time=(TextView)view.findViewById(R.id.text_time);


		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				BitmapDrawable drawable = (BitmapDrawable) iconEvent.getDrawable();
				Bitmap bitmap = drawable.getBitmap();
				
				System.out.println(getEncodeData(bitmap));
				SharedPreferences preferences = getActivity().getSharedPreferences("CREATE_EVENT", Context.MODE_WORLD_WRITEABLE);
				SharedPreferences.Editor  editor = preferences.edit();
				editor.putString("icon",getEncodeData(bitmap));
				editor.putString("title",titleEvent.getText().toString());
				editor.putString("adresse",adresseEvent.getText().toString());
				editor.putString("date",date.getText().toString());
				editor.putString("time",time.getText().toString());
				editor.commit();

				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, new MenuCreateEvent2()).commit();

			}
		});


		DateChoise.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				showDatePicker();

			}
		});
		iconEvent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);

			}
		});

		timeChoise.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showTimePicker();

			}
		});


		return view ;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getActivity();
		if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
			selectedImage = data.getData();
			Constant.ACTUAL_IMAGE_PATH=selectedImage;

			try{
				iconEvent.setImageBitmap(scaleImage(getActivity(),selectedImage));
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

	@SuppressLint("ValidFragment")
	public class DatePickerFragment extends DialogFragment {

		OnDateSetListener ondateSet;
		public DatePickerFragment() {
		}

		public void setCallBack(OnDateSetListener ondate) {
			ondateSet = ondate;
		}

		private int year, month, day;

		@Override
		public void setArguments(Bundle args) {
			super.setArguments(args);
			year = args.getInt("year");
			month = args.getInt("month");
			day = args.getInt("day");
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
		}

	}  

	private void showDatePicker() {
		DatePickerFragment date = new DatePickerFragment();
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);
		date.setCallBack(ondate);
		date.show(getFragmentManager(), "Date Picker");
	}

	OnDateSetListener ondate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			date.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
		}
	};

	@SuppressLint("ValidFragment")
	public class TimepickerFragment extends DialogFragment{

		OnTimeSetListener ontimeSet;
		public TimepickerFragment() {
		}

		public void setCallBack(OnTimeSetListener ondate) {
			ontimeSet = ondate;
		}

		private int houre, minute;

		@Override
		public void setArguments(Bundle args) {
			super.setArguments(args);
			houre = args.getInt("houre");
			minute = args.getInt("minute");

		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			return new TimePickerDialog(getActivity(), ontimeSet, houre, minute,true);
		}
	}

	private void showTimePicker() {
		TimepickerFragment time = new TimepickerFragment();
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("houre", calender.get(Calendar.HOUR_OF_DAY));
		args.putInt("minute", calender.get(Calendar.MINUTE));
		time.setArguments(args);
		time.setCallBack(ontime);
		time.show(getFragmentManager(), "Time Picker");
	}

	OnTimeSetListener ontime = new OnTimeSetListener() {



		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			time.setText(hourOfDay+":"+minute);
		}
	};
	
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

}