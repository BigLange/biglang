package com.lang.big.biglang.bean;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.lang.big.biglang.activity.homepage;
import com.lang.big.biglang.utils.MyOkHttp_util;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/21.
 */
public class ShopClass2 {

    private static ShopClass2 shopClass2;

    private Context context;

    private String versions;

    private ArrayList<String> titles;
    private HashMap<String, ArrayList<String>> items;
    private HashMap<String, String> imgPaths;

    private final String VERSIONSXMLPATH = "LangTaoSha/Xml";
    private final String VERSIONSXMLNAME = "versions.xml";
    private Handler mHander;
    private Handler mUIHandler;


    private File dirFile;



    private final String XMLPATH = "MyXml/versions.xml";

    private ShopClass2(Context context, final Handler mUIHandler) {
        this.context = context;
        this.mUIHandler = mUIHandler;
        mHander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0x110:
                        try {
                            readXml();
                            sendUIHander(homepage.LOADED, homepage.CASSFRAGMENTLOAD);

                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 0x111:
                        //如果返回的是版本号不一致的消息的话，那么就重新从服务器请求好数据保存到本地
                        titles.clear();
                        imgPaths.clear();
                        items.clear();

                        requestXml();
                        break;
                }
            }
        };
        titles = new ArrayList<>();
        items = new HashMap<>();
        imgPaths = new HashMap<>();
        boolean b = detectionFile();
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, "SD卡未挂载", Toast.LENGTH_SHORT).show();
            return;
        }
        //创建OkHttpClient的对象
        try {
            //要是本地没有这个文件
            if (!b) {
                //直接请求到这个文件
                requestXml();
            } else {
                //如果文件的话，先解析，得到版本号，和数据
                readXml();
                //调用方法去比较服务器那边的版本号
                requestVersions();
                //如果一致，那就直接拿来
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void requestVersions() {
        MyOkHttp_util.getMyOkHttp().doGetAsyn(MyOkHttp_util.ServicePath + "Versions.do", new MyOkHttp_util.MyHttpCallBack() {
            @Override
            public void doError(Request request, IOException e) {
                sendUIHander(homepage.FAILED_TO_LOAD, homepage.CASSFRAGMENTLOAD);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String mVersions = response.body().string();
                if (!versions.equals(mVersions)) {
                    mHander.sendEmptyMessage(0x111);
                } else {
                    sendUIHander(homepage.LOADED,homepage.CASSFRAGMENTLOAD);
                }
            }
        });
    }

    private void sendUIHander(int want,int age1){
        Message msg = Message.obtain();
        msg.what = want;
        msg.arg1 = age1;
        mUIHandler.sendMessage(msg);
    }


    public ArrayList<String> getTitles() {
        return titles;
    }

    public HashMap<String, ArrayList<String>> getItems() {
        return items;
    }

    public HashMap<String, String> getImgPath() {
        return imgPaths;
    }

    private boolean detectionFile() {
        File root = Environment.getExternalStorageDirectory();
        dirFile = new File(root, VERSIONSXMLPATH);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
            return false;
        }
        File file = new File(dirFile, VERSIONSXMLNAME);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    public static ShopClass2 getShopClass2(Context context, Handler mUIHandler) {
        if (shopClass2 == null) {
            shopClass2 = new ShopClass2(context, mUIHandler);
        }else{
            shopClass2.mUIHandler = mUIHandler;
            shopClass2.sendUIHander(homepage.LOADED, homepage.CASSFRAGMENTLOAD);
        }
        return shopClass2;
    }

    private void requestXml() {
        MyOkHttp_util.getMyOkHttp().doFileDownLoad(MyOkHttp_util.ServicePath + XMLPATH, dirFile.getAbsolutePath() + "/" + VERSIONSXMLNAME, mHander);
    }

    private String tag;
    private String title;

    private void readXml() throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
        InputStream is = new FileInputStream(dirFile.getAbsolutePath() + "/" + VERSIONSXMLNAME);
        xmlPullParser.setInput(is, "utf-8");
        int state = xmlPullParser.getEventType();
        ArrayList<String> arrayList = null;
        while (state != XmlPullParser.END_DOCUMENT) {
            switch (state) {
                case XmlPullParser.START_TAG:
                    tag = xmlPullParser.getName();
                    if ((!"current".equals(tag)) && (!"item".equals(tag)) && (!"imgPath".equals(tag)) && (!"versions".equals(tag))) {
                        title = tag;
                        titles.add(tag);
                        arrayList = new ArrayList<>();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tag = xmlPullParser.getName();
                    if (tag.equals(title)) {
                        items.put(tag, arrayList);
                    }
                    tag = "";
                    break;
                case XmlPullParser.TEXT:
                    String value = xmlPullParser.getText();
                    if ("current".equals(tag)) {
                        versions = value;
                    } else if ("item".equals(tag)) {
                        arrayList.add(value);
                    } else if ("imgPath".equals(tag)) {
                        imgPaths.put(title, xmlPullParser.getText());
                    }
                    break;
            }
            state = xmlPullParser.next();
        }
        title = "";
        tag = "";
    }
}
