package com.lang.big.biglang.fragment;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.lang.big.biglang.R;
import com.lang.big.biglang.activity.MReleaseActivity;
import com.lang.big.biglang.utils.MyAnimation;

/**
 * A simple {@link Fragment} subclass.
 */
public class MreleFragment1 extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{

    private View addImgLayout;
    private ListView addImgList;
    private GridView showImgGrid;
    private EditText setShopName;
    private Button btn_next;

    private ArrayAdapter<String> aAdapter;
    private String[] addlistoptions = new String[]{"拍照","从手机相册获取","取消"};

    private MReleaseActivity mActivity;


    public MreleFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mrele_fragment1, container, false);
        mActivity = (MReleaseActivity)getActivity();
        intoView(v);
        return v;
    }

    private void intoView(View v) {
        addImgLayout = v.findViewById(R.id.mrele_fragment1_add_img_layout);
        addImgList = (ListView)v.findViewById(R.id.mrele_fragment1_add_img_options);
        aAdapter = new ArrayAdapter<String>(getActivity(),R.layout.mrele_addimg_options_item_moban,R.id.addImg_potions_item_txt,addlistoptions);
        addImgList.setAdapter(aAdapter);
        addImgList.setOnItemClickListener(this);
        showImgGrid = (GridView)v.findViewById(R.id.mrele_fragment1_grid_show_img);
        setShopName = (EditText)v.findViewById(R.id.mrele_fragment1_edt_name);
        btn_next = (Button)v.findViewById(R.id.mrele_fragment1_next_btn);
        btn_next.setOnClickListener(this);
    }

    private MyAnimation manimation;
    //设置下面的菜单，显示并给其一定的动画
    private void showAddImgOptions(){
        addImgLayout.setVisibility(View.VISIBLE);
        if(manimation==null){
            manimation = MyAnimation.getMyAnimation();
        }
        manimation.setLayoutAlpha(1000,true,0,1,addImgLayout);
        manimation.setViewTranslate(1000,true,0,0,120,0,addImgLayout);
        mActivity.isAddImgstae = true;
    }

    //设置添加图片的菜单隐藏
    public void hideAddImgOptions(){
        addImgLayout.clearAnimation();
        addImgLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        showAddImgOptions();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                hideAddImgOptions();
                break;
        }
    }
}
