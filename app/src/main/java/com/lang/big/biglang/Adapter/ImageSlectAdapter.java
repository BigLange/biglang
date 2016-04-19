package com.lang.big.biglang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lang.big.biglang.R;
import com.lang.big.biglang.utils.ImageLoad;

import java.nio.charset.spi.CharsetProvider;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/4/17.
 */
public class ImageSlectAdapter extends BaseAdapter {

    private Context context;
    private int resouce;
    private List<String> mDatas;
    private String dirPath;

    private Set<Integer> positions = new HashSet<>();


    public ImageSlectAdapter(Context context,int resouce,List<String> mDatas,String dirPath){
        this.context = context;
        this.resouce = resouce;
        //图片名称
        this.mDatas = mDatas;
        //图片父文件夹地址
        this.dirPath = dirPath;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        MyClick mClick = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(resouce,parent,false);
            mHolder = new ViewHolder();
            mClick = new MyClick();
            mHolder.mImg = (ImageView)convertView.findViewById(R.id.img_select_grid_item_img);
            mHolder.mSelect = (ImageButton)convertView.findViewById(R.id.img_select_xuanze_btn);
            mHolder.mImg.setOnClickListener(mClick);
//            mHolder.mImg.setTag(mClick);
            mHolder.mSelect.setTag(mClick);
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder)convertView.getTag();
            mClick = (MyClick)mHolder.mSelect.getTag();
        }
        //重置状态
        mHolder.mImg.setImageResource(R.drawable.pictures_no);
        mHolder.mSelect.setImageResource(R.drawable.picture_unselected);
        mHolder.mImg.setColorFilter(null);

        //这句话就让图片加载了
        ImageLoad.getImageLoad().loadImage(dirPath + "/" + getItem(position), mHolder.mImg);
        mClick.setPosition(position,mHolder.mImg,mHolder.mSelect);
        if(positions.contains(position)){
            positions.add(position);
            mHolder.mImg.setColorFilter(Color.parseColor("#90000000"));
            mHolder.mSelect.setImageResource(R.drawable.pictures_selected);

        }
        return convertView;
    }


    private class ViewHolder{
        ImageView mImg;
        ImageButton mSelect;
    }
    class MyClick implements View.OnClickListener{
        int position;
        ImageView mImg;
        ImageButton mSelect;

        public void setPosition(int position,ImageView mImg,ImageButton mSelect){
            this.position = position;
            this.mImg = mImg;
            this.mSelect = mSelect;
        }


        @Override
        public void onClick(View v) {
            if(positions.contains(position)){
                positions.remove(position);
                mImg.setColorFilter(null);
                mSelect.setImageResource(R.drawable.picture_unselected);
            }else{
                positions.add(position);
                mImg.setColorFilter(Color.parseColor("#90000000"));
                mSelect.setImageResource(R.drawable.pictures_selected);
            }
        }
    }
}
