package com.lang.big.biglang.utils;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lang.big.biglang.activity.LoginActivity;
import com.lang.big.biglang.activity.MyApplication;
import com.lang.big.biglang.activity.homepage;
import com.lang.big.biglang.bean.User;

/**
 * Created by Administrator on 2016/4/30.
 */
public class Login_util {

    private static SharedPreferences pref;//用于记录登录字段，记住登录用户信息
    private SharedPreferences.Editor editor;
    //用于保存登录成功的用户信息！

    public static Boolean isLogin(final Context context){
        MyApplication myApp=(MyApplication)context.getApplicationContext();
        pref= PreferenceManager.getDefaultSharedPreferences(context);
        if (pref.getBoolean("judge",true)){
            AlertDialog.Builder dialog= new AlertDialog.Builder(context);
            dialog.setTitle("系统提示！");
            dialog.setMessage("您尚未登录,请登录后使用!");
            dialog.setNegativeButton("取消", null);
            dialog.setPositiveButton("确定", new
                    DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent it = new Intent(context, LoginActivity.class);
                            context.startActivity(it);
                        }
                    });
            dialog.show();
        return true;
    }else{
        return false;
    }

}

}
