package com.lang.big.biglang.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/22.
 */
public class MyOkHttp_util {

    private OkHttpClient mOkHttpClient;
    private static MyOkHttp_util myOkHttp_util;
    private Handler mHandler;
    private Context context;
    private Gson mGson;
    public final static String ServicePath = "http://192.168.2.101:8082/LangTaoSha/";

    private MyOkHttp_util() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    public static MyOkHttp_util getMyOkHttp() {
        if (myOkHttp_util == null) {
            synchronized (MyOkHttp_util.class) {
                if (myOkHttp_util == null) {
                    myOkHttp_util = new MyOkHttp_util();
                }
            }
        }
        return myOkHttp_util;
    }

    public interface MyHttpCallBack {
        void doError(Request request, IOException e);

        void onResponse(Response response) throws IOException;
    }

    private Call createCall(String url) {
        Request request = new Request.Builder().url(url).build();
        return mOkHttpClient.newCall(request);
    }

    public void doGetAsyn(String url, final MyHttpCallBack myHttpCallBack) {
        Call call = createCall(url);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                myHttpCallBack.doError(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                myHttpCallBack.onResponse(response);
            }
        });
    }

    public void doPost(String url,HashMap<String,Object> mMap,Callback callback){

        FormEncodingBuilder builder = new FormEncodingBuilder();
        for(String key:mMap.keySet()) {
            builder.add(key,mMap.get(key).toString());
        }
        sengRequest(url,builder.build(),callback);
    }


    public void doFileDownLoad(String url, final String dirPath, final Handler mHandler) {
        Call call = createCall(url);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                InputStream is = response.body().byteStream();
                outputFileToRoot(dirPath, is);
                mHandler.sendEmptyMessage(0x110);
            }
        });
    }


    private void outputFileToRoot(String dirPath, InputStream is) throws IOException {
        byte[] b = new byte[2048];
        int len = -1;
        FileOutputStream fos = new FileOutputStream(dirPath);
        while ((len = is.read(b)) != -1) {
            fos.write(b, 0, len);
        }
        fos.close();
    }


    public void upLoadComm(String url, HashMap<String, Object> parameter, final ArrayList<Bitmap> bitmaps, final Callback imgcallback) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (String key : parameter.keySet()) {
            builder.add(key, parameter.get(key).toString());
        }
        sengRequest(url, builder.build(), imgcallback);
    }


    //这个表示上传的类型为图片类型
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public void upLoadImg(String url, ArrayList<Bitmap> bitmaps,String cid) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        builder.addFormDataPart("cid", null, RequestBody.create(MEDIA_TYPE_PNG, cid));

        for (Bitmap bitmap : bitmaps) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] b = bos.toByteArray();
            builder.addFormDataPart("upLoadImage", null, RequestBody.create(MEDIA_TYPE_PNG, b));
        }
        RequestBody requestBody = builder.build();
        sengRequest(url, requestBody, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }
            @Override
            public void onResponse(Response response) throws IOException {
            }
        });
    }

    private void sengRequest(String url, RequestBody requestBody, Callback callback) {
        Request request = new Request.Builder().url(url).post(requestBody).build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }


}
