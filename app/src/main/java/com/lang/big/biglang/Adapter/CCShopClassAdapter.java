package com.lang.big.biglang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.ShopClass;
import com.lang.big.biglang.utils.MyOkHttp_util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/13.
 */
public class CCShopClassAdapter extends BaseAdapter {

    private Context context;
    private int resource;
    private ArrayList<String> titles;
    private HashMap<String,ArrayList<String>> items;
    private HashMap<String,String> imgPaths;
    private View handeView;


    public CCShopClassAdapter(Context context, int resource, ShopClass shopClass2,View handeView) {
        this.context = context;
        this.resource = resource;
        this.handeView = handeView;
        titles = shopClass2.getTitles();
        imgPaths = shopClass2.getImgPath();
        items = shopClass2.getItems();
//        System.out.println(context);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if(position==0){
//            return handeView;
//        }
        Hodler mHodler = null;
        int mColor = Color.parseColor("#FE4D2C");
        String title = getItem(position).toString();
        ArrayList<String> versions= items.get(title);
        String imgpath = MyOkHttp_util.ServicePath+imgPaths.get(title);
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
        mHodler.className.setText(title);
        Picasso.with(context).load(imgpath).placeholder(R.drawable.pictures_no).error(R.drawable.imgloaderror).into(mHodler.classImg);
//        mHodler.introduce.setText(sc.getBiaoti());
        if(versions.size()>4){
            ArrayList<String> arrayList = new ArrayList<>();
            for (int i=1;i<5;i++){
                arrayList.add(versions.get(i));
            }
            versions = arrayList;
        }
        ArrayAdapter<String> ada = new ArrayAdapter<String>(context,R.layout.cc_listview_item_grid_moban,R.id.cc_grid_item,versions);
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
