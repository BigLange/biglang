package com.lang.big.biglang.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lang.big.biglang.R;

import java.util.prefs.BackingStoreException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MreleFragment3 extends Fragment implements View.OnClickListener {

    private EditText price_edt;
    private EditText franking_edt;

    private View franking_layout;

    private ImageView if_BagFranking;

    private Button area_btn;
    private Button up_next;
    private Button down_next;

    private boolean isBagFranking;


    public MreleFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mrele_fragment3, container, false);
        initView(v);
        initEvent();
        return v;
    }

    private void initView(View v) {
        price_edt = (EditText) v.findViewById(R.id.mrele_frag3_price_edt);
        franking_edt = (EditText) v.findViewById(R.id.mrele_frag3_franking_edt);
        franking_layout = v.findViewById(R.id.mrele_frag3_franking_layout);
        if_BagFranking = (ImageView) v.findViewById(R.id.mrele_frag3_if_bagfranking_img);
        area_btn = (Button) v.findViewById(R.id.mrele_frag3_area_btn);
        up_next = (Button) v.findViewById(R.id.mrele_fragment3_up_next_btn);
        down_next = (Button) v.findViewById(R.id.mrele_fragment3_down_next_btn);
    }

    private void initEvent() {
        if_BagFranking.setOnClickListener(this);
        area_btn.setOnClickListener(this);
        up_next.setOnClickListener(this);
        down_next.setOnClickListener(this);
    }


    private UpNextBtnOnClickListener upNextBtnOnClickListener;
    private DownNextBtnOnClickListener downNextBtnOnClickListener;

    //上一步按钮被点击回调接口
    public interface UpNextBtnOnClickListener{
        void upNextBtnOnClick();
    }
    //下一步按钮被点击的接口的回调
    public interface DownNextBtnOnClickListener{
        void downNextBtnOnClick();
    }

    public void setUpNextBtnOnClickListener(UpNextBtnOnClickListener up){
        upNextBtnOnClickListener = up;
    }

    public void setDownNextBtnOnClickListener(DownNextBtnOnClickListener down){
        downNextBtnOnClickListener = down;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mrele_frag3_franking_layout:
                ImageView img = (ImageView) v;
                if (isBagFranking) {
                    img.setImageResource(R.drawable.picture_unselected);
                    franking_layout.setVisibility(View.VISIBLE);
                    isBagFranking = false;
                } else {
                    img.setImageResource(R.drawable.pictures_selected);
                    franking_layout.setVisibility(View.GONE);
                    isBagFranking = true;
                }
                break;
            case R.id.mrele_frag3_area_btn:
                Toast.makeText(getActivity(), "这里去获取当前位置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mrele_fragment3_up_next_btn:
                if(upNextBtnOnClickListener!=null){
                    upNextBtnOnClickListener.upNextBtnOnClick();
                }
                break;
            case R.id.mrele_fragment3_down_next_btn:
                if(downNextBtnOnClickListener!=null){
                    downNextBtnOnClickListener.downNextBtnOnClick();
                }
                break;
        }
    }
}
