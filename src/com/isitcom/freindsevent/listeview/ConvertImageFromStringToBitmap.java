package com.isitcom.freindsevent.listeview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class ConvertImageFromStringToBitmap {
	
	public static Bitmap convert(String img){
		byte[] b = Base64.decode(img, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}
	
	
}
