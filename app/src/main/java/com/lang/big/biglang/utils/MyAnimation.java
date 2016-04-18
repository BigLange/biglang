package com.lang.big.biglang.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Administrator on 2016/4/12.
 */
public class MyAnimation {
    private static MyAnimation ma;
    private Animation animation;

    public static MyAnimation getMyAnimation(){
        if(ma==null){
            ma = new MyAnimation();
        }
        return ma;
    }

    public void setLayoutAlpha(long time,boolean fillAfter,float from,float to,View v){
        animation = new AlphaAnimation(from,to);
        animation.setDuration(time);
        animation.setFillAfter(fillAfter);
        v.setAnimation(animation);
    }

    public void setViewTranslate(long time,boolean fillAfter,float xFrom,float xTo,float yFrom,float yTo,View v){

        animation = new TranslateAnimation(xFrom,xTo,yFrom,yTo);
        animation.setDuration(time);
        animation.setFillAfter(fillAfter);
        v.setAnimation(animation);

    }

}
