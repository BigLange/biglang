package com.lang.big.biglang.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.charon.pulltorefreshlistview.PullToRefreshListView;
import com.lang.big.biglang.Adapter.IndexShopAdapter;
import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.ShopData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TuiJianFragment extends Fragment implements PullToRefreshListView.OnRefreshListener{

    //实现下拉刷新
    private PullToRefreshListView listView;
    private ArrayList<ShopData> arrayList = new ArrayList<>();


    public TuiJianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadShopDta();
        View v = inflater.inflate(R.layout.fragment_tui_jian, container, false);
        listView = (PullToRefreshListView)v.findViewById(R.id.index_list_shop_show);
        IndexShopAdapter indexShopAdapter = new IndexShopAdapter(getContext(),R.layout.index_shop_show_moban,arrayList);
        listView.setAdapter(indexShopAdapter);
        //刷新监听的接口回调
        listView.setOnRefreshListener(this);
        return v;
    }

    private void loadShopDta() {
        ShopData shopData = new ShopData("海盗船键盘",null,500,"长沙|雨花","这个键盘到底是有多屌？",2,1,null);
        ShopData shopData2 = new ShopData("海盗船K70",null,700,"长沙|望城","这个歌键盘不是RGB的，简陋的别来",9,2,null);
        ShopData shopData3 = new ShopData("海盗船K70",null,700,"长沙|望城","这个歌键盘不是RGB的，简陋的别来",9,2,null);
        arrayList.add(shopData);
        arrayList.add(shopData2);
        arrayList.add(shopData3);
    }


    @Override
    public void onRefresh() {
        //暂时模拟刷新数据
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                //模拟加载数据用时两秒
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ShopData shopData3 = new ShopData("自装发烧级主机",null,5000,"长沙|望城","i74790k的U，970显卡",9,2,null);
                arrayList.add(shopData3);
                //请求处理完成，调用PullToRefreshListView隐藏掉顶部的刷新的样式
                listView.onRefreshComplete();
            }
        }.execute();
    }
}
