package com.lang.big.biglang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lang.big.biglang.R;
import com.lang.big.biglang.utils.ImageLoad;

import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by Administrator on 2016/4/19.
 */
public class Mrelefrag1ImgGridAdapter extends ArrayAdapter<String>{

    private ArrayList<String> mDatas = new ArrayList<>();
    private LayoutInflater inflater;
    private View mImgBtnLayout;
    private OnAddImgBtnClickListener onAddImgClick;

    public interface OnAddImgBtnClickListener{
        void onClick();
    }

    public Mrelefrag1ImgGridAdapter(Context context, ArrayList<String> datas) {
        super(context, 0);
        inflater = LayoutInflater.from(context);
        mDatas = datas;
        mImgBtnLayout = inflater.inflate(R.layout.mrele_frag1_addimg_btn_moban,null);
        ImageButton mImgBtn = (ImageButton)mImgBtnLayout.findViewById(R.id.mrele_frag1_addimg_btn);

        mImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddImgClick!=null){
                    onAddImgClick.onClick();
                }
            }
        });
    }

    public void setOnAddImgClickListener(OnAddImgBtnClickListener onAddImgClick){
        this.onAddImgClick = onAddImgClick;
    }

    @Override
    public int getCount() {
        if(mDatas.size()<16){
            return mDatas.size()+1;
        }else{
            return mDatas.size();
        }
    }

    @Override
    public String getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position<mDatas.size()) {
            ViewHolder mHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.mrele_addimg_gird_item_moban, parent, false);
                mHolder = new ViewHolder();
                mHolder.mimg = (ImageView) convertView.findViewById(R.id.mrele_frag1_grid_item_img);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
//        mHolder.mimg.setImageResource(R.drawable.yxy);
            ImageLoad.getImageLoad().loadImage(mDatas.get(position), mHolder.mimg);
        }else{
            convertView = mImgBtnLayout;
        }
        return convertView;
    }

    private class ViewHolder{
        ImageView mimg;
    }
}
