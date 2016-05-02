package com.lang.big.biglang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lang.big.biglang.MyView.CircleView;
import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.Commodity;
import com.lang.big.biglang.bean.ShopData;
import com.lang.big.biglang.bean.User;
import com.lang.big.biglang.utils.MyOkHttp_util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class IndexShopAdapter extends ArrayAdapter<Commodity>{

    private Context context;
    private int resource;
    //分辨率
    private float ratio;
    //100pd转换成px的值
    private int dp2px;

    public IndexShopAdapter(Context context, int resource, ArrayList<Commodity> lists) {
        super(context, resource, lists);
        this.context = context;
        this.resource = resource;
        ratio = context.getResources().getDisplayMetrics().density;
        dp2px = (int)(100*ratio-0.5f);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler mHodler = null;
        MyOnClick mClick = null;
        Commodity comm= getItem(position);
        if(convertView==null){
            convertView =  LayoutInflater.from(context).inflate(resource,parent,false);
            mHodler = new Hodler();
            mClick = new MyOnClick();
            mHodler.headView = (CircleView)convertView.findViewById(R.id.sp_hand_img);
            mHodler.shopName = (TextView)convertView.findViewById(R.id.sp_name_txt);
            mHodler.price = (TextView)convertView.findViewById(R.id.sp_price_txt);
            mHodler.spImgDisplayLayout = (LinearLayout)convertView.findViewById(R.id.sp_img_display_layout);
            mHodler.introduce = (TextView)convertView.findViewById(R.id.sp_introduce_txt);
            mHodler.address = (TextView)convertView.findViewById(R.id.sp_address_txt);
            mHodler.comments = (TextView)convertView.findViewById(R.id.sp_ly_txt);
            mHodler.xiangYao = (TextView)convertView.findViewById(R.id.sp_xy_txt);
            mHodler.xiangYaoImg = (ImageButton)convertView.findViewById(R.id.sp_xy_img_btn);
            mHodler.xiangYaoImg.setOnClickListener(mClick);
            mHodler.xiangYaoImg.setTag(mClick);
            convertView.setTag(mHodler);
        }else{
            mHodler = (Hodler)convertView.getTag();
            mClick = (MyOnClick)mHodler.xiangYaoImg.getTag();
        }
        mHodler.shopName.setText(comm.getUser().getUserName());
        mHodler.price.setText(comm.getCommPRICE()+"");
        mHodler.introduce.setText(comm.getDescribe());
        String area = comm.getCommArea();
        String[] areas = area.split("&");
        mHodler.address.setText(areas[1]+"|"+areas[2]);
        mHodler.comments.setText(comm.getCommReview()+"");
        mHodler.xiangYao.setText(comm.getLikeTheComm() + "");
        mClick.setComm(comm,mHodler.xiangYao);
        if(comm.getIsIfLike())
            mHodler.xiangYaoImg.setImageResource(R.drawable.xihuan);
//        if(mHodler.spImgDisplayLayout.getTag()!=null){
//            mHodler.spImgDisplayLayout.removeAllViews();
//        }
        mHodler.spImgDisplayLayout.removeAllViews();
        String[] imgPaths = comm.getImgPaths();
        for(int i=0;i<imgPaths.length;i++){
            ImageView imageView = new ImageView(context);
            mHodler.spImgDisplayLayout.addView(imageView,dp2px,dp2px);
            imageView.setPadding(10, 10, 10, 10);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String url = MyOkHttp_util.ServicePath+"Myimg/"+comm.getCommId()+"/"+imgPaths[i];
            Picasso.with(context).load(url).placeholder(R.drawable.pictures_no).error(R.drawable.imgloaderror).into(imageView);
        }
        String Headurl = MyOkHttp_util.ServicePath+"Myimg";
        User user = comm.getUser();
        if("n".equals(user.getHead())){
            Headurl += "/hand.jpg";
        }else {
            Headurl += "/UserHead/"+user.getId()+".jpg";
        }
//        mHodler.spImgDisplayLayout.setTag(1);
        Picasso.with(context).load(Headurl).placeholder(R.drawable.pictures_no).error(R.drawable.imgloaderror).into(mHodler.headView);
        return convertView;
    }

        class MyOnClick implements View.OnClickListener{

        Commodity comm;
        TextView textView;

        public void setComm(Commodity comm,TextView textView){
            this.comm = comm;
            this.textView = textView;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sp_xy_img_btn:
                    ImageButton xiangYaoBtn = (ImageButton)v;
                    if(comm.getIsIfLike()){
                        comm.setIfLike(false);
                        int likeTheComm = (comm.getLikeTheComm()-1);
                        comm.setLikeTheComm(likeTheComm);
                        xiangYaoBtn.setImageResource(R.drawable.xihuan_logo);
                        textView.setText(likeTheComm+"");
                    }else{
                        comm.setIfLike(true);
                        int likeTheComm = (comm.getLikeTheComm()+1);
                        comm.setLikeTheComm(likeTheComm);
                        xiangYaoBtn.setImageResource(R.drawable.xihuan);
                        textView.setText(likeTheComm+"");
                    }
                    break;
            }
        }
    }
    private class Hodler{
        CircleView headView;
        TextView shopName;
        LinearLayout spImgDisplayLayout;
        TextView price;//价格
        TextView introduce;//商品介绍
        TextView address;//卖家地址
        TextView comments;//评论条数
        TextView xiangYao;
        ImageButton xiangYaoImg;
    }
}
