package com.lang.big.biglang.fragment;


import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.charon.pulltorefreshlistview.PullRefreshAndLoadMoreListView;
import com.lang.big.biglang.Adapter.IndexShopFuJinAdapter;
import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.ShopData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuJinFragment extends Fragment implements PullRefreshAndLoadMoreListView.OnLoadMoreListener{
    private PullRefreshAndLoadMoreListView listView;
    private List<ShopData> arrlist = new ArrayList<>();

    public FuJinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fu_jin, container, false);
        intoView(v);
        return v;
    }

    private void intoView(View v) {
        listView = (PullRefreshAndLoadMoreListView)v.findViewById(R.id.index_shopfujin_list);
        getContent();
        IndexShopFuJinAdapter indexShopFuJinAdapter = new IndexShopFuJinAdapter(getContext(),R.layout.index_shopfujin_show_moban,arrlist);
        listView.setAdapter(indexShopFuJinAdapter);
        //设置上啦刷新
        listView.setOnLoadMoreListener(this);
    }

    private void getContent() {
        ShopData shopData = new ShopData("海盗船键盘",null,500,"长沙|雨花","这个键盘到底是有多屌？",2,1,null);
        ShopData shopData2 = new ShopData("海盗船K70",null,700,"长沙|望城","这个歌键盘不是RGB的，简陋的别来",9,2,null);
        ShopData shopData3 = new ShopData("海盗船K70",null,700,"长沙|望城","这个歌键盘不是RGB的，简陋的别来",9,2,null);
        arrlist.add(shopData);
        arrlist.add(shopData2);
        arrlist.add(shopData3);
        arrlist.add(shopData3);
        arrlist.add(shopData3);
    }


    @Override
    public void onLoadMore() {
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
                arrlist.add(shopData3);
                //请求处理完成，调用PullToRefreshListView隐藏掉顶部的刷新的样式
                listView.onLoadMoreComplete();
            }
        }.execute();
    }
}
