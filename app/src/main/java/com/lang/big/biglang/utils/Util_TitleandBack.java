package com.lang.big.biglang.utils;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lang.big.biglang.R;

/**
 * Created by Administrator on 2016/3/16.
 */
public class Util_TitleAndBack extends LinearLayout{
    ImageView util_back=null;
    TextView util_title=null;
    TextView util_right=null;
    public Util_TitleAndBack(Context context) {
        super(context);
    }
    public Util_TitleAndBack(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.util_titleandback, this);
        util_back=(ImageView)findViewById(R.id.util_back);
        util_title=(TextView)findViewById(R.id.util_title);
        util_right= (TextView) findViewById(R.id.util_right);
//        util_back.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "返回按钮被点击了！", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    public void backVisibility(int Visibility){
        util_back.setVisibility(Visibility);
    }
    public void setTitle(String str){
    this.util_title.setText(str);
    }
    public void setbackListener( OnClickListener listener){
       util_back.setOnClickListener(listener);
    }
    public void settingRight(int Visibility){
        util_right.setVisibility(Visibility);
    }
    public void settingText(String text){
        util_right.setText(text);
    }

}
