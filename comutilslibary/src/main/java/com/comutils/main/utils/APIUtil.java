package com.comutils.main.utils;

public class APIUtil {
	public static final  String TAG = "magpie";
	
	public static final String mMCH_ID = "mMCH_ID"; 
	public static final String mAPP_ID = "mAPP_ID"; 
	
	// APPKEY   6db900ffbf515b8ee5d57dceaa47a079
	public static final  String APP_KEY = "ba7d45a435d6a6e1354623ff0492fddf";

	public static final  String APP_KEY_STR = "appkey";
	//商户ID 
	public static final String MCH_ID = "1469610579579955aa8"; 
	// 商户设备 ID



	public static final String APP_ID = "1469610579579955aa6"; 
	public static final String SERVER_URL ="http://wx.xiquezu.com/";
	public static final String API_URL =SERVER_URL+"App/api/";












	//注册时 获取验证码 //sendRegYZCode
	public static final String REGISTER_SEND_CODE=API_URL+"sendRegYZCode";
	//注册
	public static final String REGISTER_CHECK=API_URL+"reg_check";
	//登录
	public static final String M_LOGIN = API_URL+"login";
	//忘记密码获取验证码
	public static final String FORGET_SEND_CODE = API_URL+"forget_send_code";
	//设置新密码
	public static final String FORGER_SET_PASS =API_URL+"forger_set_pass";
	//自动登录
	public static final String RE_LOGIN =API_URL+"re_login";
}
