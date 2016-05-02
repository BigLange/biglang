package com.lang.big.biglang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.lang.big.biglang.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/20.
 */
public class PersonalMgrAdapter extends BaseAdapter{
    ArrayList<HashMap<String,Object>> arydatas=new ArrayList<>();
            Context mcontext;
    LayoutInflater inflater;
    ArrayAdapter adapter=null;
    final int VIEW_TYPE = 3;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;

    public PersonalMgrAdapter( Context context,ArrayList<HashMap<String,Object>> arydatas) {
        this.arydatas = arydatas;
        this.mcontext = context;
        inflater=LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return 3;
    }
    //每个convert view都会调用此方法，获得当前所需要的view样式
            @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        int p = position%6;
        if(p == 0)
        return TYPE_1;
        else if(p < 2)
        return TYPE_2;
        else if(p < 3)
        return TYPE_3;
        else
        return TYPE_3;
        }

    @Override
    public int getViewTypeCount() {
       // TODO Auto-generated method stub
        return 3;
        }

    @Override
    public Object getItem(int position) {
        return arydatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //每次创建视图都会调用这个方法
        viewHolder1 holeder1=null;
        viewHolder2 holeder2=new viewHolder2();
        viewHolder3 holeder3=null;

        int type = getItemViewType(position);

        if(convertView == null)
        {
            //按当前所需的样式，确定new的布局
            switch(type)
            {
                    case TYPE_1:
                    convertView = inflater.inflate(R.layout.row1_personal_detail, parent, false);
                    holeder1 = new viewHolder1();
                    convertView.setTag(holeder1);
                    break;

                    case TYPE_2:
                    convertView = inflater.inflate(R.layout.row2_personal_detail,parent,false);
                    convertView.setTag(holeder2);
                    break;

                case TYPE_3:
                    convertView = inflater.inflate(R.layout.row3_personal_detail,parent,false);
                    holeder3= new viewHolder3();
                    convertView.setTag(holeder3);
                    break;
               }
            }else{
           //有convertView，按样式，取得不用的布局
            switch(type)
            {
                case TYPE_1:
                    holeder1 = (viewHolder1) convertView.getTag();

                    break;
                case TYPE_2:
                    holeder2 = (viewHolder2) convertView.getTag();

                    break;
                case TYPE_3:
                    holeder3=(viewHolder3)convertView.getTag();
                }
            }

        return convertView;
    }

    class viewHolder1{
        TextView tv_show_test;

    }
    class viewHolder2{
        TextView tv_show_test;
        GridView comment_gridview;
    }
    class viewHolder3{
        TextView tv_show_test;

    }

}
