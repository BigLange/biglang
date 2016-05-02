package com.lang.big.biglang.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.lang.big.biglang.R;

/**
 * Created by Administrator on 2016/4/3.
 */
public class CircleView extends ImageView {


    private Paint mPaint;

    private Bitmap src;

    private Bitmap mask;



    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
       mPaint = new Paint();
        // 设置抗混叠
        mPaint.setAntiAlias(true);
        // 拿到原型图
        src = BitmapFactory.decodeResource(getResources(), R.drawable.yxy);
        }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 图片的遮罩，为什么要在这里面初始化遮罩层呢？因为在这个方法里Width()和Height()才被初始化了
        mask = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_4444);
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置画布的颜色为透明
        canvas.drawColor(Color.TRANSPARENT);
        //画一个背景圆
//        mPaint.setColor(Color.BLACK);
//        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getWidth()/2-10,mPaint);
        // 划出你要显示的圆
        Canvas cc = new Canvas(mask);
        cc.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getWidth() / 2 , mPaint);
//        Canvas cc2 = new Canvas(mask);
//        cc2.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, getWidth() / 2+10, mPaint);
        // 这个方法相当于PS新建图层，下面你要做的事就在这个图层上操作
        canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);
        // 先绘制遮罩层
        canvas.drawBitmap(mask, 0, 0, mPaint);
        // 设置混合模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 再绘制src源图
        canvas.drawBitmap(src, null, new Rect(0,0,getMeasuredWidth(),getMeasuredHeight()), mPaint);
        // 还原混合模式
        mPaint.setXfermode(null);
        // 还原画布，相当于Ps的合并图层
        canvas.restore();
        }


    public void setImage(Bitmap bitmap){
        if(bitmap!=null) {
            src = bitmap;
        }
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        src = bm;
    }

    @Override
    public void setImageResource(int resId) {
        src = BitmapFactory.decodeResource(getResources(),resId);
    }
}