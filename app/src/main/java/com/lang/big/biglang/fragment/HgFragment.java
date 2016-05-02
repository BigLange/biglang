package com.lang.big.biglang.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.lang.big.biglang.R;
import com.lang.big.biglang.activity.homepage;
import com.lang.big.biglang.bean.Commodity;
import com.lang.big.biglang.utils.GetCommList;
import com.lang.big.biglang.utils.MyOkHttp_util;

import java.util.ArrayList;

public class HgFragment extends Fragment implements View.OnClickListener {

    private Button btn_tuijian;
    private Button btn_fujin;


    private TuiJianFragment tj_fragment;
    private FuJinFragment fj_fragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private ArrayList<Commodity> comms;

    private Fragment currentFragment;
    public static final int TUIJIAN = 2212;
    public static final int FUJING = 2213;

    //显示正在加载的时候的画面
    private IngLoadFragment ingLoad;

    private int tuiJianFrgamentStateCode = homepage.NO_LOAD;
    private int fuJinFrgamentStateCode = homepage.NO_LOAD;

    private TuiJianFragment.TuiJianListItemClickListener tuiJianListItemClickListener;





    private GetCommList getCommList;
    private Handler mHandler;

    //当前正在显示的fragment的对应的代码
    private int currentFragmentCode;

    public HgFragment(TuiJianFragment.TuiJianListItemClickListener tuiJianListItemClickListener) {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == GetCommList.CCOMMLOAD_OK) {
                    switch (msg.arg1) {
                        case TUIJIAN:
                            comms = (ArrayList<Commodity>) msg.obj;
                            loadTuiJianFragment();
                            tuiJianFrgamentStateCode = homepage.LOADED;
                            break;
                        case FUJING:
                            comms = (ArrayList<Commodity>) msg.obj;
                            loadFuJinFragment();
                            fuJinFrgamentStateCode = homepage.LOADED;
                            break;
                    }
                } else if (msg.what == GetCommList.COMMLOAD_ERROR) {
                    switch (msg.arg1) {
                        case TUIJIAN:
                            tuiJianFrgamentStateCode = homepage.FAILED_TO_LOAD;
                            break;
                        case FUJING:
                            fuJinFrgamentStateCode = homepage.FAILED_TO_LOAD;
                            break;
                    }
                }
            }
        };
        this.tuiJianListItemClickListener = tuiJianListItemClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hg, container, false);
        intoView(v);
        intoEvent();
        intoFrgament();
        return v;
    }

    private void intoFrgament() {
        currentFragmentCode = TUIJIAN;
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ingLoad = new IngLoadFragment();
        fragmentTransaction.add(R.id.hg_tj_or_fj, ingLoad);
        fragmentTransaction.commit();
        currentFragment = ingLoad;
        getCommList = GetCommList.getGetCommList(mHandler);
        String url = MyOkHttp_util.ServicePath + "getComm.do?number=0&uid=-1";
        getCommList.tjValues(url,TUIJIAN);
        tuiJianFrgamentStateCode = homepage.BEGING_LOADED;
    }

    /**
     * 专门用于加载推荐 这个页面的fragment
     */
    private void loadTuiJianFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        tj_fragment = new TuiJianFragment(comms);
        tj_fragment.setTuiJianItemClick(tuiJianListItemClickListener);
        fragmentTransaction.add(R.id.hg_tj_or_fj, tj_fragment);
        if (currentFragmentCode != TUIJIAN) {
            fragmentTransaction.hide(tj_fragment);
            fragmentTransaction.commit();
            return;
        }
        setFragmentShowAndHide(tj_fragment);
    }

    private void loadFuJinFragment(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fj_fragment = new FuJinFragment(comms);
        fragmentTransaction.add(R.id.hg_tj_or_fj, fj_fragment);
        if(currentFragmentCode!=FUJING){
            fragmentTransaction.hide(fj_fragment);
            fragmentTransaction.commit();
            return;
        }
        setFragmentShowAndHide(fj_fragment);
    }

    private void intoEvent() {
        btn_tuijian.setOnClickListener(this);
        btn_fujin.setOnClickListener(this);
    }

    private void intoView(View v) {
        btn_tuijian = (Button) v.findViewById(R.id.btn_tuijian);
        btn_fujin = (Button) v.findViewById(R.id.btn_fujin);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tuijian:
                if (currentFragmentCode != TUIJIAN) {
                    currentFragmentCode = TUIJIAN;
                    btn_tuijian.setBackgroundResource(R.drawable.fragment_hg_top_btn_style);
                    btn_fujin.setBackgroundColor(0x00000000);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if (tuiJianFrgamentStateCode == homepage.LOADED) {
                        setFragmentShowAndHide(tj_fragment);
                    }
                    if (tuiJianFrgamentStateCode == homepage.BEGING_LOADED) {
                        if (currentFragment != ingLoad)
                            setFragmentShowAndHide(ingLoad);
                        return;
                    }
                    if (tuiJianFrgamentStateCode == homepage.FAILED_TO_LOAD) {
                        Toast.makeText(getActivity(), "网络请求出现错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            case R.id.btn_fujin:
                if (currentFragmentCode != FUJING) {
//                    Toast.makeText(getContext(), "界面显示了", Toast.LENGTH_SHORT).show();
                    currentFragmentCode = FUJING;
                    btn_fujin.setBackgroundResource(R.drawable.fragment_hg_top_btn_style);
                    btn_tuijian.setBackgroundColor(0x00000000);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if (fuJinFrgamentStateCode == homepage.LOADED) {
                        setFragmentShowAndHide(fj_fragment);
                    }
                    if(fuJinFrgamentStateCode == homepage.NO_LOAD){
                        String url = MyOkHttp_util.ServicePath+"getComm.do?uid=-1&number=0&area=雨花";
                        getCommList = GetCommList.getGetCommList(mHandler);
                        getCommList.tjValues(url,FUJING);
                        fuJinFrgamentStateCode = homepage.BEGING_LOADED;
                        if (currentFragment != ingLoad)
                            setFragmentShowAndHide(ingLoad);
                        return;
                    }
                    if (fuJinFrgamentStateCode == homepage.BEGING_LOADED) {
                        if (currentFragment != ingLoad)
                            setFragmentShowAndHide(ingLoad);
                        return;
                    }
                    if (fuJinFrgamentStateCode == homepage.FAILED_TO_LOAD) {
                        Toast.makeText(getActivity(), "网络请求出现错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
            default:
                break;
        }
    }




    private void setFragmentShowAndHide(Fragment fragment) {
        fragmentTransaction.hide(currentFragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
        currentFragment = fragment;
    }


}
