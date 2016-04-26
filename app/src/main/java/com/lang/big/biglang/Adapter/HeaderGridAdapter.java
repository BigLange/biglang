package com.lang.big.biglang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lang.big.biglang.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/14.
 */
public class HeaderGridAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private int[] imgs;
    private String[] imgNames;

   public HeaderGridAdapter(Context context,int resource,int[] imgs,String[] imgNames){
       this.context = context;
       this.resource = resource;
       this.imgs = imgs;
       this.imgNames = imgNames;
   }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return imgNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int imga = imgs[position];
        String name = imgNames[position];
        Hodler mHodler = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.cc_head_img_grid_moban,parent,false);
            mHodler = new Hodler();
            mHodler.mImageView = (ImageView)convertView.findViewById(R.id.hear_grid_item_img);
            mHodler.mImageName = (TextView)convertView.findViewById(R.id.hear_grid_item_name);
            convertView.setTag(mHodler);
        }else{
            mHodler = (Hodler)convertView.getTag();
        }
        System.out.println("数据一直在解析！！！");
        mHodler.mImageView.setImageResource(imga);
        mHodler.mImageName.setText(name);
        return convertView;
    }

    class Hodler{
        ImageView mImageView;
        TextView mImageName;
    }
}
