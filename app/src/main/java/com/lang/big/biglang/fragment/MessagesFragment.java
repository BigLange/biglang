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
public class MessagesFragment extends Fragment {

    ListView listView;
    SimpleAdapter sa;
    ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();


    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_messages, container, false);
        intoList(v);
        return v;
    }

    private void intoList(View v) {
        listView = (ListView)v.findViewById(R.id.mes_main_listView);
        loadValues();
        String[] from = new String[]{"img","title","content"};
        int[] to = new int[]{R.id.msg_item_img,R.id.msg_item_title,R.id.msg_item_content};
        sa = new SimpleAdapter(getContext(),arrayList,R.layout.msg_main_list_item_moban,from,to);
        listView.setAdapter(sa);
    }

    private void loadValues() {
        HashMap<String,Object> hashMap1 = new HashMap<>();
        hashMap1.put("img",R.drawable.msg_liuyan);
        hashMap1.put("title","留言");
        hashMap1.put("content","这里就先随便写点东西");
        HashMap<String,Object> hashMap2 = new HashMap<>();
        hashMap2.put("img",R.drawable.msg_sixin);
        hashMap2.put("title","私信");
        hashMap2.put("content","这里也是随便写点");
        HashMap<String,Object> hashMap3 = new HashMap<>();
        hashMap3.put("img",R.drawable.msg_dingdan);
        hashMap3.put("title","订单消息");
        hashMap3.put("content","支付宝超时交易已经关闭");
        HashMap<String,Object> hashMap4 = new HashMap<>();
        hashMap4.put("img",R.drawable.msg_xitong);
        hashMap4.put("title","系统消息");
        hashMap4.put("content", "温馨提示");
        arrayList.add(hashMap1);
        arrayList.add(hashMap2);
        arrayList.add(hashMap3);
        arrayList.add(hashMap4);
    }


}
