package com.lang.big.biglang.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lang.big.biglang.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReleaseFragment extends Fragment {

    ListView listView;
    View head;
    ArrayList<HashMap<String,Object>> mValues = new ArrayList<>();
    String[] option = new String[]{"我发布的","我卖出的","我买到的",
            "我想要的","我的好友","我的账单",
            "红包","帮助中心"};
    int[] imgs = new int[]{R.drawable.fabu,R.drawable.maidaode,R.drawable.maichude,
            R.drawable.bxihuan,R.drawable.myhaoyou,R.drawable.zhangdan,
            R.drawable.hongbao,R.drawable.bangzhu};

    SimpleAdapter sa;


    public ReleaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_release, container, false);
        getHead(inflater);
        setListView(v);
        return v;
    }

    private void getHead(LayoutInflater inflater) {
        head = inflater.inflate(R.layout.rele_head_moban,null);
        //这里设置头部的属性
    }


    private void setListView(View v) {
        listView = (ListView)v.findViewById(R.id.rele_mian_listView);
        listView.addHeaderView(head);
        getValues();
        String[] from = new String[]{"img","option","number"};
        int[] to = new int[]{R.id.rele_list_item_img,R.id.rele_list_item_option,R.id.rele_list_item_number};
        sa = new SimpleAdapter(getContext(),mValues,R.layout.rele_list_item_moban,from,to);
        listView.setAdapter(sa);
    }

    private void getValues() {
        //这里设置好最开始就能显示的选项名称 以及图片，但是选项后面的数组 是通过网络请求得来的
        //所以下面有一个方法专门设置后面的数字的
        for (int i=0;i<imgs.length;i++) {
            HashMap<String,Object> mMap = new HashMap<>();
            mMap.put("img",imgs[i]);
            mMap.put("option",option[i]);
            mValues.add(mMap);
        }
    }

     public void setnumber(int position,int number){
         HashMap<String,Object> mMap = mValues.get(position);
         mMap.put("number",number);
         sa.notifyDataSetChanged();
     }
}
