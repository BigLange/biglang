package com.lang.big.biglang.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lang.big.biglang.R;
import com.lang.big.biglang.utils.ImageLoad;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/20.
 */
public class CommShowViewPageAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<String> dirPath = new ArrayList<>();

    public CommShowViewPageAdapter(Context context, ArrayList<String> mDirPaths){
        this.context =context;
        dirPath = mDirPaths;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context).load(dirPath.get(position)).placeholder(R.drawable.pictures_no).error(R.drawable.imgloaderror).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //系统会直接帮我们将那个界面的对象(这里的话就是那个imageView对象)变成Object的形式传进来
        //然后我们的就直接强转一下类型，然后删除掉就好了。
        container.removeView((View) object);
//                mImageViews.remove(position);
    }

    @Override
    public int getCount() {
        return dirPath.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
