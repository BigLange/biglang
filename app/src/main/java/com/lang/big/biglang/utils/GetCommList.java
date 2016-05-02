package com.lang.big.biglang.utils;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.lang.big.biglang.bean.Commodity;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/4/30.
 */
public class GetCommList {

    public static final int CCOMMLOAD_OK = 8800;
    public static final int COMMLOAD_ERROR = 8801;

    private static GetCommList getCommList;

    private Handler mHandler;


    private GetCommList(){

    }


    public static GetCommList getGetCommList(Handler handler){
        if(getCommList==null){
            synchronized (GetCommList.class){
                if(getCommList==null){
                    getCommList = new GetCommList();
                }
            }
        }
        getCommList.mHandler = handler;
        return getCommList;
    }



    public void tjValues(String url,final int whoFragment){
        MyOkHttp_util.getMyOkHttp().doGetAsyn(url, new MyOkHttp_util.MyHttpCallBack() {
            @Override
            public void doError(Request request, IOException e) {
                mHandler.sendEmptyMessage(COMMLOAD_ERROR);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String result = response.body().string();
                    System.out.println(result);
                    JSONObject jsonObj = new JSONObject(result);
                    int stateCode = jsonObj.getInt("stateCode");
                    if(stateCode==200){
                        ArrayList<Commodity> comm = analysisJson(jsonObj.getJSONArray("result").toString());
                        Message msg = Message.obtain();
                        msg.what = CCOMMLOAD_OK;
                        msg.arg1 = whoFragment;
                        msg.obj = comm;
                        mHandler.sendMessage(msg);
                    }else{
                        sendMsgThe(whoFragment, COMMLOAD_ERROR);
                    }
                } catch (JSONException e) {
                    sendMsgThe(whoFragment,COMMLOAD_ERROR);
                    e.printStackTrace();
                }
            }
        });
    }


    private void  sendMsgThe(int fragmentCode,int stateCode){
        Message msg = Message.obtain();
        msg.what = stateCode;
        msg.arg1 = fragmentCode;
        mHandler.sendMessage(msg);
    }

    private ArrayList<Commodity> analysisJson(String jsonStr) throws JSONException {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Commodity>>(){}.getType();
        ArrayList<Commodity> arr = gson.fromJson(jsonStr, type);
        return arr;
    }
}
