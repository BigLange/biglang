package com.lang.big.biglang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lang.big.biglang.MyView.CircleView;
import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.Commodity;
import com.lang.big.biglang.bean.ShopData;
import com.lang.big.biglang.utils.MyOkHttp_util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class IndexShopFuJinAdapter extends ArrayAdapter<Commodity>{

    private Context context;
    private int resource;


    public IndexShopFuJinAdapter(Context context, int resource, ArrayList<Commodity> lists) {
        super(context, resource, lists);
        this.context = context;
        this.resource = resource;
        System.out.println(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler mHodler = null;
        Commodity comm= getItem(position);
        if(convertView==null){
            convertView =  LayoutInflater.from(context).inflate(resource,parent,false);
            mHodler = new Hodler();
            mHodler.headView = (CircleView)convertView.findViewById(R.id.index_shopfujin_touxiang);
            mHodler.userName = (TextView)convertView.findViewById(R.id.index_shopfujin_uname);
            mHodler.sp_img_1 = (ImageView)convertView.findViewById(R.id.index_shopfujin_show);
            mHodler.price = (TextView)convertView.findViewById(R.id.index_shopfujin_yuanjia);
            mHodler.YuanJia = (TextView)convertView.findViewById(R.id.index_shopfujin_yuanjia);
            mHodler.introduce = (TextView)convertView.findViewById(R.id.index_shopfujin_jieshao);
            mHodler.address = (TextView)convertView.findViewById(R.id.index_shopfujin_address);
            convertView.setTag(mHodler);
        }else{
            mHodler = (Hodler)convertView.getTag();
        }
        mHodler.price.setText(comm.getCommPRICE()+"");
        mHodler.introduce.setText(comm.getDescribe());
        mHodler.address.setText(comm.getCommArea());
        String[] imgPaths = comm.getImgPaths();
        String url = MyOkHttp_util.ServicePath+"Myimg/"+comm.getCommId()+"/"+imgPaths[0];
        Picasso.with(context).load(url).placeholder(R.drawable.pictures_no).error(R.drawable.imgloaderror).into(mHodler.sp_img_1);
        mHodler.YuanJia.setText(comm.getCommCOST() + "");
        mHodler.userName.setText(comm.getUser().getUserName());
        return convertView;
    }

    class Hodler{
        CircleView headView;
        TextView userName;
        ImageView sp_img_1;
        TextView price;//价格
        TextView YuanJia;
        TextView introduce;//商品介绍
        TextView address;//卖家地址
    }
}
