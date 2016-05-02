package com.lang.big.biglang.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListView;

import com.lang.big.biglang.Adapter.CCShopClassAdapter;
import com.lang.big.biglang.Adapter.HeaderGridAdapter;
import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.ShopClass;
import com.lang.big.biglang.utils.MyAnimation;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CassificationFragment extends Fragment implements AbsListView.OnScrollListener{

    private ListView listView;
    private View handView;
    private ArrayList<ShopClass> arrayList = new ArrayList<>();

    private boolean btn_isShow;

    private View btn_layout;

    private MyAnimation animation;
    private ShopClass mShopClass2;


    public CassificationFragment(ShopClass shopClass2) {
        // Required empty public constructor
        mShopClass2 = shopClass2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cassification, container, false);
        intoView(v, container);
        return v;
    }

    private void intoView(View v, ViewGroup container) {
        listView = (ListView) v.findViewById(R.id.cc_main_listView);
        handView = setHeadView(container);
        btn_layout = v.findViewById(R.id.cc_select_btn_layout);
        animation = MyAnimation.getMyAnimation();
        listView.addHeaderView(handView);
//        getValues();
        CCShopClassAdapter ca = new CCShopClassAdapter(getContext(), R.layout.cc_main_list_view_moban, mShopClass2,handView);
        System.out.println(listView + ":" + ca);
        listView.setAdapter(ca);
        listView.setOnScrollListener(this);
    }

//    private void getValues() {
//        ShopClass sc1 = new ShopClass("手机", new String[]{"华为", "苹果", "魅族", "VIVO"}, "", "苹果不再需要肾");
//        ShopClass sc2 = new ShopClass("电脑", new String[]{"戴尔", "苹果", "联想", "华硕"}, "", "笔记本也有春天");
//        ShopClass sc3 = new ShopClass("数码", new String[]{"单反", "mp4", "耳机", "相机"}, "", "单反不再穷三代");
//        ShopClass sc4 = new ShopClass("家居", new String[]{"桌子", "凳子", "家电", "床铺"}, "", "旧得用得更顺手");
//        ShopClass sc5 = new ShopClass("家居", new String[]{"桌子", "凳子", "家电", "床铺"}, "", "旧得用得更顺手");
//        ShopClass sc6 = new ShopClass("家居", new String[]{"桌子", "凳子", "家电", "床铺"}, "", "旧得用得更顺手");
//        ShopClass sc7 = new ShopClass("家居", new String[]{"桌子", "凳子", "家电", "床铺"}, "", "旧得用得更顺手");
//
//        arrayList.add(sc1);
//        arrayList.add(sc2);
//        arrayList.add(sc3);
//        arrayList.add(sc4);
//        arrayList.add(sc5);
//        arrayList.add(sc6);
//        arrayList.add(sc7);
//    }

    private GridView mGridView;

    private View setHeadView(ViewGroup container) {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.cc_head_moban, null);
        mGridView = (GridView) mView.findViewById(R.id.cc_head_img_grid);
        int[] imgs = new int[]{R.drawable.mphone, R.drawable.mshuma, R.drawable.mjiadian,
                R.drawable.mjiaotong, R.drawable.mmuying, R.drawable.mgfuzhuang,
                R.drawable.mjiaju, R.drawable.mdiannao};
        String[] imgNames = new String[]{"手机", "数码", "家用电器",
                "代步工具", "母婴用品", "服装鞋帽",
                "家具家居", "电脑"};
        HeaderGridAdapter hga = new HeaderGridAdapter(getContext(),R.layout.cc_head_img_grid_moban,imgs,imgNames);
        mGridView.setAdapter(hga);
        //这里可以设置我们头部的值
        return mView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(visibleItemCount>=4){
            if(!btn_isShow){
                btn_layout.setVisibility(View.VISIBLE);
                btn_isShow = true;
                animation.setLayoutAlpha(1000,true,0,1,btn_layout);
            }
        }else if(visibleItemCount<3){
            if(btn_isShow){
                btn_layout.clearAnimation();
                btn_layout.setVisibility(View.GONE);
                btn_isShow = false;
            }
        }
    }
}
