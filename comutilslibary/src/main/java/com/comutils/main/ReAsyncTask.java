package com.comutils.main;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.comutils.net.HttpUtil;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ReAsyncTask {
    private boolean isrun = false;
    public ReAsyncTask() {
        isrun = false;
    }
    public interface FinishCallback {
        public void taskFinish(int code,String result);
        //public void taskFinish(int code,String errmsg,String result = null);
    }
    public interface CUFinishCallback {
        public void taskFinish(int code,String errmsg,String result);
    }
    public void setRun(boolean isRun){
        isrun = isRun;
    }
    public boolean isRun(){
        return isrun;
    }
    @SuppressLint("HandlerLeak")
    public void cuLoadData(final String mUrl,final List<NameValuePair> paramsList ,final CUFinishCallback finishCallback){
        isrun = true;
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                int code = 200;String errmsg =null;String result =null;
                String requery = (String) message.obj;
                if(requery == "601"){
                    code = 601;
                    errmsg = "接口调用异常，无法连通，请检查网络或接口！";
                }
                JSONObject jobj = null;
                try {
                    jobj = new JSONObject(requery);
                    if(jobj.getInt("state") == 0){
                        code = jobj.getInt("code");
                        errmsg = jobj.getString("msg");
                    }else{
                        //result = jobj.getString("data");
                        result = requery;
                    }
                } catch (JSONException e) {}
                finishCallback.taskFinish(code,errmsg,result);
            }
        };
        //final JSONObject jobj = null;
        new Thread() {
            @Override
            public void run() {
                String requery = HttpUtil.queryStringForPost(mUrl,paramsList);

                Message message = handler.obtainMessage(0, requery);
                handler.sendMessage(message);
            }
        }.start();
    }
    @SuppressLint("HandlerLeak")
    public void loadData(final String mUrl,final List<NameValuePair> paramsList ,final FinishCallback finishCallback){
        isrun = true;
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                finishCallback.taskFinish(message.what,(String) message.obj);
            }
        };
        //final JSONObject jobj = null;
        new Thread() {
            @Override
            public void run() {

                int code = 200;String result =null;
                String requery = HttpUtil.queryStringForPost(mUrl,paramsList);
//            	Log.i("soso", "tag ssss 000 == "+requery);
                if(requery.equals("601")){
                    code = 601;
                }
                JSONObject jobj = null;
                try {
                    jobj = new JSONObject(requery);
                    if(jobj.getInt("state") == 0){
                        code = jobj.getInt("code");
                        result = requery;
                    }else{
                        //result = jobj.getString("data");
                        result = requery;
                    }
                    //jobj = new JSONObject(jobj.getString("data"));

                } catch (JSONException e) {}

                Message message = handler.obtainMessage(code, result);
                handler.sendMessage(message);
            }
        }.start();
    }
    public void loadData2(final String mUrl,final JSONObject paramsobj ,final FinishCallback finishCallback){
        isrun = true;
        final Handler handler = new Handler() {
            public void handleMessage(Message message) {
                finishCallback.taskFinish(message.what,(String) message.obj);
            }
        };
        //final JSONObject jobj = null;
        new Thread() {
            @Override
            public void run() {

                int code = 200;String result =null;
                //String requery = HttpUtil.queryStringForPost3(mUrl,paramsobj);
                String requery = HttpUtil.queryStringForPost4(mUrl,paramsobj);
                if(requery == "601"){
                    code = 601;
                }
                JSONObject jobj = null;
                try {
                    jobj = new JSONObject(requery);
                    if(jobj.getInt("state") == 0){
                        code = jobj.getInt("code");
                    }else{
                        //result = jobj.getString("data");
                        result = requery;
                    }
                    //jobj = new JSONObject(jobj.getString("data"));

                } catch (JSONException e) {}

                Message message = handler.obtainMessage(code, result);
                handler.sendMessage(message);
            }
        }.start();
    }
}
