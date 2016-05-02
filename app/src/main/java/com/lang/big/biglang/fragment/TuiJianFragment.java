package com.lang.big.biglang.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.charon.pulltorefreshlistview.PullRefreshAndLoadMoreListView;
import com.charon.pulltorefreshlistview.PullToRefreshListView;
import com.lang.big.biglang.Adapter.IndexShopAdapter;
import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.Commodity;
import com.lang.big.biglang.utils.GetCommList;
import com.lang.big.biglang.utils.MyOkHttp_util;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TuiJianFragment extends Fragment
        implements PullRefreshAndLoadMoreListView.OnLoadMoreListener,PullToRefreshListView.OnRefreshListener {

    //实现下拉刷新
    private PullRefreshAndLoadMoreListView listView;
    private ArrayList<Commodity> comms;


    private int currentIndex = 0;

    private GetCommList getCommList;

    private Handler mHandler;

    public static final int UPPULL = 3321;
    public static final int DOWNPULL = 3322;

    private boolean ifLoad = true;


    public TuiJianFragment(ArrayList<Commodity> comms) {
        // Required empty public constructor
        this.comms = comms;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == GetCommList.CCOMMLOAD_OK) {
                    if (msg.arg1 == DOWNPULL) {
                        ArrayList<Commodity> arr = (ArrayList<Commodity>) msg.obj;
                        if (arr.size() < 3)
                            ifLoad = false;
                        TuiJianFragment.this.comms.addAll(arr);
                        listView.onLoadMoreComplete();
                    }else if(msg.arg1 == UPPULL){
                        ArrayList<Commodity> arr = (ArrayList<Commodity>) msg.obj;
                        TuiJianFragment.this.comms.clear();
                        TuiJianFragment.this.comms.addAll(arr);
                        Toast.makeText(getActivity(), "数据刷新啦!", Toast.LENGTH_SHORT).show();
                        listView.onRefreshComplete();
                        ifLoad = true;
                    }
                } else if (msg.what == GetCommList.COMMLOAD_ERROR) {
                    if (msg.arg1 ==DOWNPULL) {
                        Toast.makeText(getActivity(), "数据加载失败，请检查网络状况", Toast.LENGTH_SHORT).show();
                    }else if(msg.arg1 == UPPULL){
                        Toast.makeText(getActivity(), "数据刷新失败，请检查网络状况", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        getCommList = GetCommList.getGetCommList(mHandler);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        loadShopDta();
        View v = inflater.inflate(R.layout.fragment_tui_jian, container, false);
        listView = (PullRefreshAndLoadMoreListView) v.findViewById(R.id.index_list_shop_show);
        IndexShopAdapter indexShopAdapter = new IndexShopAdapter(getContext(), R.layout.index_shop_show_moban, comms);
        listView.setAdapter(indexShopAdapter);
        //刷新监听的接口回调
        listView.setOnLoadMoreListener(this);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(tuiJianItemClick!=null){
                    tuiJianItemClick.onItemClick(position);
                }
            }
        });
        return v;
    }

//    private void loadShopDta() {
//        ShopData shopData = new ShopData("海盗船键盘",null,500,"长沙|雨花","这个键盘到底是有多屌？",2,1,null);
//        ShopData shopData2 = new ShopData("海盗船K70",null,700,"长沙|望城","这个歌键盘不是RGB的，简陋的别来",9,2,null);
//        ShopData shopData3 = new ShopData("海盗船K70",null,700,"长沙|望城","这个歌键盘不是RGB的，简陋的别来",9,2,null);
//        arrayList.add(shopData);
//        arrayList.add(shopData2);
//        arrayList.add(shopData3);
//    }


    //下拉不会再加载数据后，往后的下拉次数的计数
    private int downLoadMore = 3;
    //刷新后，往后的刷新次数的计数
    private int upnRefresh = 2;

    @Override
    public void onLoadMore() {
        if (ifLoad) {
            currentIndex += 3;
            String url = MyOkHttp_util.ServicePath + "getComm.do?uid=-1&number=" + currentIndex;
            getCommList.tjValues(url, DOWNPULL);
            return;
        }
        downLoadMore++;
        if (downLoadMore % 3 == 0) {
            Toast.makeText(getActivity(), "没有更多数据啦!", Toast.LENGTH_SHORT).show();
        }
        listView.onLoadMoreComplete();
    }

    @Override
    public void onRefresh() {
        currentIndex = 0;
        if (upnRefresh % 2 == 0) {
            String url = MyOkHttp_util.ServicePath + "getComm.do?uid=-1&number=" + currentIndex;
            getCommList.tjValues(url, UPPULL);
        }else{
            listView.onRefreshComplete();
        }
        upnRefresh++;
    }


    public interface TuiJianListItemClickListener{
        void onItemClick(int postion);
    }

    private TuiJianListItemClickListener tuiJianItemClick;

    public void setTuiJianItemClick(TuiJianListItemClickListener tuiJianItemClick){
        this.tuiJianItemClick = tuiJianItemClick;
    }
}
