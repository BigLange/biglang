package com.lang.big.biglang.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.text.method.DigitsKeyListener;
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
    private EditText yuan_price_edt;
    private EditText franking_edt;

    private View franking_layout;

    private ImageView if_BagFranking;

    private Button area_btn;
    private Button up_next;
    private Button down_next;
    private boolean isBagFranking;

    private String area;


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
        yuan_price_edt = (EditText) v.findViewById(R.id.mrele_frag3_yuan_price_edt);
        franking_edt = (EditText) v.findViewById(R.id.mrele_frag3_franking_edt);
        franking_layout = v.findViewById(R.id.mrele_frag3_franking_layout);
        if_BagFranking = (ImageView) v.findViewById(R.id.mrele_frag3_if_bagfranking_img);
        area_btn = (Button) v.findViewById(R.id.mrele_frag3_area_btn);
        up_next = (Button) v.findViewById(R.id.mrele_fragment3_up_next_btn);
        down_next = (Button) v.findViewById(R.id.mrele_fragment3_down_next_btn);
    }

    private void initEvent() {
        price_edt.setKeyListener(new DigitsKeyListener(false, true));
        yuan_price_edt.setKeyListener(new DigitsKeyListener(false, true));
        franking_edt.setKeyListener(new DigitsKeyListener(false, true));
        if_BagFranking.setOnClickListener(this);
        area_btn.setOnClickListener(this);
        up_next.setOnClickListener(this);
        down_next.setOnClickListener(this);
    }


    private UpNextBtnOnClickListener upNextBtnOnClickListener;
    private DownNextBtnOnClickListener downNextBtnOnClickListener;
    private AreaSelectBtnClickListener areaSelectBtnClickListener;

    //上一步按钮被点击回调接口
    public interface UpNextBtnOnClickListener {
        void upNextBtnOnClick();
    }

    //下一步按钮被点击的接口的回调
    public interface DownNextBtnOnClickListener {
        void downNextBtnOnClick(int price, int yuan_price, int fragking, String area);
    }

    public interface AreaSelectBtnClickListener{
        void areaSelectBtnClick();
    }

    public void setUpNextBtnOnClickListener(UpNextBtnOnClickListener up) {
        upNextBtnOnClickListener = up;
    }

    public void setDownNextBtnOnClickListener(DownNextBtnOnClickListener down) {
        downNextBtnOnClickListener = down;
    }

    public void setAreaSelectBtnClickListener(AreaSelectBtnClickListener area) {
        areaSelectBtnClickListener = area;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mrele_frag3_if_bagfranking_img:
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
               if(areaSelectBtnClickListener!=null){
                   areaSelectBtnClickListener.areaSelectBtnClick();
               }
                break;
            case R.id.mrele_fragment3_up_next_btn:
                if (upNextBtnOnClickListener != null) {
                    upNextBtnOnClickListener.upNextBtnOnClick();
                }
                break;
            case R.id.mrele_fragment3_down_next_btn:
                int price_int = -1;
                int yuan_price_int = -1;
                int franking_int = -1;
                String price = price_edt.getText().toString();
                if (price.length() > 0) {
                    int i = Integer.parseInt(price);
                    if (i <= 0) {
                        Toast.makeText(getActivity(), "商品的价格必须大于0元噢", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (i >= 999999) {
                        Toast.makeText(getActivity(), "商品的价格必须小于9999999元哦", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    price_int = i;
                }
                String yuan_price = yuan_price_edt.getText().toString();
                if (yuan_price.length() > 0) {
                    int i = Integer.parseInt(yuan_price);
                    if (i <= 0) {
                        Toast.makeText(getActivity(), "商品的原价格必须大于0元噢", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (i >= 999999) {
                        Toast.makeText(getActivity(), "商品的原价格必须小于9999999元哦", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    yuan_price_int = i;
                }
                if (!isBagFranking) {
                    String fragking_price = franking_edt.getText().toString();
                    if (fragking_price.length() > 0) {
                        int i = Integer.parseInt(fragking_price);
                        if (i < 0) {
                            Toast.makeText(getActivity(), "邮费必须大于等于0元噢", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (i >= 999) {
                            Toast.makeText(getActivity(), "邮费必须小于999元哦", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        franking_int = i;
                    }
                }
                if (downNextBtnOnClickListener != null) {
                    downNextBtnOnClickListener.downNextBtnOnClick(price_int, yuan_price_int, franking_int, "湖南&长沙&雨花");
                }
                break;

        }
    }
    public void setAreaBtnText(String area){
        area_btn.setText(area);
        this.area = area;
    }


}
