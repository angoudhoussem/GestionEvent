package com.isitcom.freindsevent.constant;

import android.net.Uri;

public class Constant {
	public static final String IP_WEB_SERVICE="http://192.168.42.237:13493/";
	public static final String URL_REGISTER=IP_WEB_SERVICE+"RestWebService/resources/v1/friendsevent/register/";
	public static final String URL_GET_ID=IP_WEB_SERVICE+"RestWebService/resources/v1/friendsevent/idUser/";
	public static final String URL_IMAGE=IP_WEB_SERVICE+"RestWebService/resources/v1/friendsevent/imageUser";
	public static final String URL_SEARCH=IP_WEB_SERVICE+"RestWebService/resources/v1/friendsevent/findall";
	public static final String URL_SEND_FRIENDS_INVITATION=IP_WEB_SERVICE+"RestWebService/resources/v1/friendsevent/friends/sendInvitation/";
	public static final String URL_VERIFY_INVITATION=IP_WEB_SERVICE+"RestWebService/resources/v1/friendsevent/friends/verifyInvition/";
	public static String ACTUAL_USER=null;
	public static String ID_USER;
	public static  String ACTUAL_PASSWORD=null;
	public static Uri ACTUAL_IMAGE_PATH=null;
}
