package com.comutils.main.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferences {
	private SharedPreferences sp;
	public SharePreferences(Context context) {
		//mContext = context;
		sp = context.getSharedPreferences("globalconfig", 0);
	}
	/** 获取全局配置文件对象 **/
	public SharedPreferences getSp(){
		return sp;
	}
	/** 更新全局配置文件键值对 **/
	public  boolean updateSp(String key, Object value){
		SharedPreferences.Editor editor = sp.edit();
		if(value instanceof String){
			editor.putString(key, value.toString());
		}else if(value instanceof Integer){
			editor.putInt(key, ((Integer)value).intValue());
		}else if(value instanceof Boolean){
			editor.putBoolean(key, ((Boolean)value).booleanValue());
		}else if(value instanceof Long){
			editor.putLong(key, ((Long)value).longValue());
		}else if(value instanceof Float){
			editor.putFloat(key, ((Float)value).floatValue());
		 
		}else{
			editor.putString(key, value.toString());
		}
		 
		return editor.commit();
	}
	/** 设置默认语言
	public void setLocalLanguage(){
		String location = sp.getString("defaultLanguage", "zh");
		Resources resources = mContext.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if(location.equals("zh")){//简体中文
        	config.locale = Locale.SIMPLIFIED_CHINESE;
        }else if(location.equals("en")){//英语
        	config.locale = Locale.ENGLISH;	
        }
        resources.updateConfiguration(config, dm);
	} **/
}
