package com.lang.big.biglang.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by Administrator on 2016/4/27.
 */
public class MyMapUilts implements BDLocationListener {

    private static final String MAPKEY = "UG39ogWlOoxCjDSmcZNcZFjlKaGk3GQj";

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = null;

    private Handler mHandler;

    public static final int GETLOCATION_IS_OK = 999;
    public static final int GETLOCATION_IS_ERROR = 998;

    private String local;

    private Context context;

    public MyMapUilts(Context context,Handler mHandler) {
        this.context = context;
        this.mHandler = mHandler;
        mLocationClient = new LocationClient(context);
        myListener = this;
        initLocation();
        //注册监听
        mLocationClient.registerLocationListener(myListener);

        mLocationClient.start();
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        double lat = bdLocation.getLatitude();
        double lng = bdLocation.getLongitude();

        entypycomplie(lat, lng);
        System.out.println("city:"+lat+","+lng);
    }

    public String getLocal() {
        return local;
    }

    private void entypycomplie(double lat, double lng) {
        String url = "http://api.map.baidu.com/geocoder/v2/";
        HashMap<String,Object> mMap = new HashMap<>();
        mMap.put("ak",MAPKEY);
        mMap.put("callback","renderReverse");
        mMap.put("mcode","57:42:7C:AA:39:8A:06:EB:1E:E0:E9:94:D4:84:20:B7:5C:CC:2B:7E;com.lang.big.biglang");
        mMap.put("location",lat+","+lng);
        mMap.put("output","json");
        mMap.put("pois",1);
        MyOkHttp_util.getMyOkHttp().doPost(url, mMap, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.sendEmptyMessage(GETLOCATION_IS_ERROR);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject responseJson = new JSONObject(response.body().string());
                    JSONObject resultJson = responseJson.getJSONObject("result");
                    JSONObject localJson = resultJson.getJSONObject("addressComponent");
                    local = localJson.getString("city")+"&"+localJson.getString("district");
                    mHandler.sendEmptyMessage(GETLOCATION_IS_OK);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(GETLOCATION_IS_ERROR);
                }
            }
        });
    }
}
