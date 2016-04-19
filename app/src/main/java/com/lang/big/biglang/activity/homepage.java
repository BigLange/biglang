package com.lang.big.biglang.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.lang.big.biglang.R;
import com.lang.big.biglang.fragment.CassificationFragment;
import com.lang.big.biglang.fragment.HgFragment;
import com.lang.big.biglang.fragment.MessagesFragment;
import com.lang.big.biglang.fragment.ReleaseFragment;
import com.lang.big.biglang.utils.MyAnimation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class homepage extends FragmentActivity implements View.OnClickListener {

    private Button btn_hg;
    private Button btn_classification;
    private Button btn_dope;
    private Button btn_release;
    private ImageButton imgb_add_shop;
    private Button btn_album;
    private Button btn_cim;

    private Button currentClickBtn;

    private View layout_select_img_fg;
    private View layout_select_img_bs;

    private HgFragment fragment_hg;
    private CassificationFragment fragment_canss;
    private MessagesFragment fragment_message;
    private ReleaseFragment fragment_relese;


    private Fragment currentFragment;

    private FragmentManager mFManaage;
    private FragmentTransaction mFTransaction;

    private boolean isSelectImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        intoView();
        intoEvent();
        intoFragment();
    }

    private void intoFragment() {
        mFManaage = getSupportFragmentManager();
        mFTransaction = mFManaage.beginTransaction();
        fragment_hg = new HgFragment();
        //加载主界面
        mFTransaction.add(R.id.set_fragment, fragment_hg);
        mFTransaction.commit();
        currentFragment = fragment_hg;
    }

    private void intoEvent() {
        btn_hg.setOnClickListener(this);
        btn_classification.setOnClickListener(this);
        btn_dope.setOnClickListener(this);
        btn_release.setOnClickListener(this);
        imgb_add_shop.setOnClickListener(this);
        btn_album.setOnClickListener(this);
        btn_cim.setOnClickListener(this);

    }

    private void intoView() {
        btn_hg = (Button) findViewById(R.id.id_btn_hg);
        btn_classification = (Button) findViewById(R.id.id_btn_classification);
        btn_dope = (Button) findViewById(R.id.id_btn_dope);
        btn_release = (Button) findViewById(R.id.id_btn_release);
        btn_album = (Button) findViewById(R.id.btn_album);
        btn_cim = (Button) findViewById(R.id.btn_cim);
        imgb_add_shop = (ImageButton) findViewById(R.id.add_this_shop);

        currentClickBtn = btn_hg;
        currentClickBtn.setTextColor(0xffff9c00);
        currentClickBtn.setBackgroundColor(0xfbfbfbf);


        layout_select_img_fg = findViewById(R.id.layout_select_img_fg);
        layout_select_img_bs = findViewById(R.id.layout_select_img_bs);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_hg:
                mFTransaction = mFManaage.beginTransaction();
                if (currentFragment != fragment_hg) {
                    fragmentShowToHide(v, fragment_hg);
                }
                break;
            case R.id.id_btn_classification:
                if (currentFragment != fragment_canss) {
                    mFTransaction = mFManaage.beginTransaction();
                    if (fragment_canss == null) {
                        fragment_canss = new CassificationFragment();
                        mFTransaction.add(R.id.set_fragment, fragment_canss);
                    }
                    fragmentShowToHide(v, fragment_canss);
                }
                break;
            case R.id.id_btn_dope:
                if (currentFragment != fragment_message) {
                    mFTransaction = mFManaage.beginTransaction();
                    if (fragment_message == null) {
                        fragment_message = new MessagesFragment();
                        mFTransaction.add(R.id.set_fragment, fragment_message);
                    }
                    fragmentShowToHide(v, fragment_message);
                }
                break;
            case R.id.id_btn_release:
                if (currentFragment != fragment_relese) {
                    mFTransaction = mFManaage.beginTransaction();
                    if (fragment_relese == null) {
                        fragment_relese = new ReleaseFragment();
                        mFTransaction.add(R.id.set_fragment, fragment_relese);
                    }
                    fragmentShowToHide(v, fragment_relese);
                }
                break;
            case R.id.btn_album:
                getImage(1);
                break;
            case R.id.btn_cim:
                getImage(2);
                break;
            case R.id.add_this_shop:
                ejectOption();
                break;
        }
    }


    private void getImage(int i) {
        int code = 0;
        if (i == 1) {
            btn_album.setBackgroundResource(R.drawable.iamge_select_blue);
            btn_cim.setBackgroundResource(R.drawable.iamge_select_red);
            code = 2;
        } else {
            btn_cim.setBackgroundResource(R.drawable.iamge_select_blue);
            btn_album.setBackgroundResource(R.drawable.iamge_select_red);
            code = 1;
        }
        Intent intent = new Intent(this, MReleaseActivity.class);
        intent.putExtra("intent",code);
        startActivity(intent);
        hideOption();
    }

    private MyAnimation mAnimation;

    private void ejectOption() {
        isSelectImg = true;
        layout_select_img_fg.setVisibility(View.VISIBLE);
        //获得滑动对象
        mAnimation = MyAnimation.getMyAnimation();
        //设置获得图片界面透明度动画
        setLayoutAlpha();
        //设置滑动效果
        setViewTranslate();
    }

    private void setViewTranslate() {
        mAnimation.setViewTranslate(1000, true, 0, 0, 100, 0, layout_select_img_bs);
        mAnimation.setViewTranslate(1000, true, 0, 0, 10, 0, btn_album);
        mAnimation.setViewTranslate(1000, true, 0, 0, 10, 0, btn_cim);
    }

    private void setLayoutAlpha() {
        mAnimation.setLayoutAlpha(1000, true, 0f, 1f, layout_select_img_fg);
    }

    private void fragmentShowToHide(View v, Fragment mFragemnt) {
        Button btn = (Button) v;
        resetBtnTextColor();
        btn.setTextColor(0xffff9c00);
        btn.setBackgroundColor(0xfbfbfbf);
        currentClickBtn = btn;

        mFTransaction.hide(currentFragment);
        mFTransaction.show(mFragemnt);
        currentFragment = mFragemnt;
        mFTransaction.commit();
    }

    private void resetBtnTextColor() {
        currentClickBtn.setTextColor(0xff000000);
        currentClickBtn.setBackgroundColor(0x00000000);
    }


    @Override
    public void onBackPressed() {
        if (isSelectImg) {
            hideOption();
        } else {
            super.onBackPressed();
        }
    }

    private void hideOption(){
        //            layout_select_img_bs.clearAnimation();
        layout_select_img_fg.clearAnimation();
//            btn_album.clearAnimation();
//            btn_cim.clearAnimation();
        layout_select_img_fg.setVisibility(View.GONE);
//            Toast.makeText(homepage.this,(layout_select_img_fg.getVisibility()==View.GONE)+"", Toast.LENGTH_SHORT).show();
        isSelectImg = false;
    }
}
