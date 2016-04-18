package com.lang.big.biglang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lang.big.biglang.MyView.CircleView;
import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.ShopClass;
import com.lang.big.biglang.bean.ShopData;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13.
 */
public class CCShopClassAdapter extends ArrayAdapter<ShopClass>{

    private Context context;
    private int resource;


    public CCShopClassAdapter(Context context, int resource, List<ShopClass> lists) {
        super(context, resource, lists);
        this.context = context;
        this.resource = resource;
        System.out.println(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler mHodler = null;
        int mColor = Color.parseColor("#FE4D2C");
        ShopClass sc= getItem(position);
        if(convertView==null){
            convertView =  LayoutInflater.from(context).inflate(resource,parent,false);
            mHodler = new Hodler();
            mHodler.viewColor = convertView.findViewById(R.id.cc_view_color);
            mHodler.className = (TextView)convertView.findViewById(R.id.cc_item_class_name);
            mHodler.classImg = (ImageView)convertView.findViewById(R.id.cc_list_item_img);
            mHodler.introduce = (TextView)convertView.findViewById(R.id.cc_item_biaoti);
            mHodler.mGridView = (GridView )convertView.findViewById(R.id.cc_item_grid);
            convertView.setTag(mHodler);
        }else{
            mHodler = (Hodler)convertView.getTag();
        }
        if(position%2==0){
           mColor = Color.parseColor("#00d1ae");
        }
        mHodler.viewColor.setBackgroundColor(mColor);
        mHodler.className.setText(sc.getClassName());
        mHodler.introduce.setText(sc.getBiaoti());
        ArrayAdapter<String> ada = new ArrayAdapter<String>(context,R.layout.cc_listview_item_grid_moban,R.id.cc_grid_item,sc.getType());
        mHodler.mGridView.setAdapter(ada);
        return convertView;
    }

    class Hodler{
        View viewColor;//设置种类前面的颜色条的名字
        TextView className;//设置商品种类的名字
        ImageView classImg;//设置商品种类的图片
        TextView introduce;//种类的口号
        GridView mGridView;//详细的几个分类(设置为四个)
    }
}
