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
import com.lang.big.biglang.bean.ShopData;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class IndexShopAdapter extends ArrayAdapter<ShopData>{

    private Context context;
    private int resource;


    public IndexShopAdapter(Context context, int resource, List<ShopData> lists) {
        super(context, resource, lists);
        this.context = context;
        this.resource = resource;
        System.out.println(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler mHodler = null;
        MyOnClick mClick = null;
        ShopData shopData= getItem(position);
        if(convertView==null){
            convertView =  LayoutInflater.from(context).inflate(resource,parent,false);
            mHodler = new Hodler();
            mClick = new MyOnClick();
            mHodler.headView = (CircleView)convertView.findViewById(R.id.sp_hand_img);
            mHodler.shopName = (TextView)convertView.findViewById(R.id.sp_name_txt);
            mHodler.sp_img_1 = (ImageView)convertView.findViewById(R.id.index_shop_show_img1);
            mHodler.sp_img_2 = (ImageView)convertView.findViewById(R.id.index_shop_show_img2);
            mHodler.sp_img_3 = (ImageView)convertView.findViewById(R.id.index_shop_show_img3);
            mHodler.price = (TextView)convertView.findViewById(R.id.sp_price_txt);
            mHodler.introduce = (TextView)convertView.findViewById(R.id.sp_introduce_txt);
            mHodler.address = (TextView)convertView.findViewById(R.id.sp_address_txt);
            mHodler.comments = (TextView)convertView.findViewById(R.id.sp_ly_txt);
            mHodler.xiangYao = (TextView)convertView.findViewById(R.id.sp_xy_txt);
            mHodler.xiangYaoImg = (ImageView)convertView.findViewById(R.id.sp_xy_img);
            System.out.println( mHodler.xiangYaoImg+":"+mHodler.comments+":"+mHodler.sp_img_1);
            mHodler.xiangYaoImg.setOnClickListener(mClick);
            mHodler.xiangYaoImg.setTag(mClick);
            convertView.setTag(mHodler);
        }else{
            mHodler = (Hodler)convertView.getTag();
        }
        mHodler.shopName.setText(shopData.getShopName());
        mHodler.price.setText(shopData.getPrice()+"");
        mHodler.introduce.setText(shopData.getIntroduce());
        mHodler.address.setText(shopData.getAddress());
        mHodler.comments.setText(shopData.getComments()+"");
        mHodler.xiangYao.setText(shopData.getXiangYao()+"");
        ImageView[] sp_img = new ImageView[]{mHodler.sp_img_1,mHodler.sp_img_2,mHodler.sp_img_3};
        return convertView;
    }

    class MyOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sp_xy_img:
                    break;
            }
        }
    }
    class Hodler{
        CircleView headView;
        TextView shopName;
        ImageView sp_img_1;
        ImageView sp_img_2;
        ImageView sp_img_3;
        TextView price;//价格
        TextView introduce;//商品介绍
        TextView address;//卖家地址
        TextView comments;//评论条数
        TextView xiangYao;
        ImageView xiangYaoImg;
    }
}
