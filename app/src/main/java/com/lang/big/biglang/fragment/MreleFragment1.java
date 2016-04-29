package com.lang.big.biglang.fragment;


import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.lang.big.biglang.Adapter.Mrelefrag1ImgGridAdapter;
import com.lang.big.biglang.R;
import com.lang.big.biglang.activity.MReleaseActivity;
import com.lang.big.biglang.utils.MyAnimation;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 */
public class MreleFragment1 extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener,
        Mrelefrag1ImgGridAdapter.OnAddImgBtnClickListener{

    private View addImgLayout;
    private View tiJiaoroof;
    private ListView addImgList;
    private GridView showImgGrid;
    private EditText setShopName;
    private Button btn_next;

    private ArrayAdapter<String> aAdapter;
    private String[] addlistoptions = new String[]{"拍照","从手机相册获取","取消"};

    private MReleaseActivity mActivity;

    private Mrelefrag1ImgGridAdapter m1igada;

    private ArrayList<String> mDatas = new ArrayList<>();

    private boolean isTiJiao;



    public MreleFragment1() {
        // Required empty public constructor
    }


    public void setmDatas(ArrayList<String> mDatas) {
        this.mDatas = mDatas;
        gridViewSetAdapter();
    }

    public interface StartImgPreListener{
        void startImgPre(int index);
    }

    private StartImgPreListener startImgPreListener;

    public void setStartImgPreListener(StartImgPreListener startImgPreListener) {
        this.startImgPreListener = startImgPreListener;
    }

    private void gridViewSetAdapter(){
        m1igada = new Mrelefrag1ImgGridAdapter(getActivity(),mDatas);
        m1igada.setOnAddImgClickListener(this);
        showImgGrid.setAdapter(m1igada);
        showImgGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (startImgPreListener != null) {
                    startImgPreListener.startImgPre(position);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mrele_fragment1, container, false);
        mActivity = (MReleaseActivity) getActivity();
        intoView(v);
        return v;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden&&isTiJiao){
            tiJiaoroof.setVisibility(View.VISIBLE);
        }
    }

    private void intoView(View v) {
        addImgLayout = v.findViewById(R.id.mrele_fragment1_add_img_layout);
        tiJiaoroof = v.findViewById(R.id.mrele_frag1_tijiao_roof_layout);
        addImgList = (ListView)v.findViewById(R.id.mrele_fragment1_add_img_options);
        setShopName = (EditText)v.findViewById(R.id.mrele_fragment1_edt_name);
        aAdapter = new ArrayAdapter<String>(getActivity(),R.layout.mrele_addimg_options_item_moban,R.id.addImg_potions_item_txt,addlistoptions);
        addImgList.setAdapter(aAdapter);
        addImgList.setOnItemClickListener(this);
        showImgGrid = (GridView)v.findViewById(R.id.mrele_fragment1_grid_show_img);
        gridViewSetAdapter();
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
        manimation.setViewTranslate(1000,true,0,0,120,0,addImgList);
        mActivity.isAddImgstae = true;
    }

    //设置添加图片的菜单隐藏
    public void hideAddImgOptions(){
        addImgLayout.clearAnimation();
        addImgLayout.setVisibility(View.GONE);
    }

    public boolean getIsTijiao(){
        return isTiJiao;
    }

    //给予Activity的回调接口
    public interface Frag1ToFrag2Listener{
        void nextFragment(String shopName);
    }

    private Frag1ToFrag2Listener nextFragment;

    public void setNextFragment(Frag1ToFrag2Listener nextFragment) {
        this.nextFragment = nextFragment;
    }

    @Override
    public void onClick(View v) {
        String shopName = setShopName.getText().toString();
        if(!(shopName.length() >0)){
            Toast.makeText(getActivity(), "先给商品起个名字吧", Toast.LENGTH_SHORT).show();
            return;
        }
        if(shopName.length()>15){
            Toast.makeText(getActivity(), "商品名字过长了", Toast.LENGTH_SHORT).show();
            return;
        }
        if(nextFragment!=null){
            nextFragment.nextFragment(shopName);
            isTiJiao = true;
        }
    }
    //接口的回调，回调点击拍照的主界面的处理
    public interface OnCaptureBtnClickListeren{
        void onCaptureBtnClick();
    }

    //接口的回调，回调点击相册选择的界面处理
    public interface OnAlbumBtnClickListeren{
        void onAlbumBtnClick();
    }

    private OnCaptureBtnClickListeren captureClick;
    private OnAlbumBtnClickListeren albumClick;

    public void setCaptureClick(OnCaptureBtnClickListeren captureClick){
        this.captureClick = captureClick;
    }

    public void setAlbumClick(OnAlbumBtnClickListeren albumClick){
        this.albumClick = albumClick;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                if(captureClick!=null){
                    captureClick.onCaptureBtnClick();
                }
                break;
            case 1:
                if(albumClick!=null){
                    albumClick.onAlbumBtnClick();
                }
                break;
        }
        hideAddImgOptions();
    }


    @Override
    public void onClick() {
        //这个方法是对适配器那边添加图片按钮按下后的回调
        showAddImgOptions();
    }
}
