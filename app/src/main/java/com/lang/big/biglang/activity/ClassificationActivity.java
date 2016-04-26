package com.lang.big.biglang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.ShopClass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;

public class ClassificationActivity extends Activity implements AdapterView.OnItemClickListener{

    private ListView titleListView;
    private ListView optionListView;

    private ShopClass2 mClass;

    private Handler mUiHandler;

    private ArrayList<String> leftTitles;
    private ArrayList<String> rightOptions;

    private HashMap<String,ArrayList<String>> classOptions;

    private ArrayAdapter<String> leftTitleAdapter;
    private ArrayAdapter<String> rightOptionsAdapter;


    //记录当前的大的一个分类
    private String currentTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        initView();
        initEvent();
        initValuesAndHandler();
    }

    private void initValuesAndHandler() {
        mUiHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
               initAdapter();
            }
        };
        mClass = ShopClass2.getShopClass2(this,mUiHandler);
    }

    private void initView() {
        titleListView = (ListView)findViewById(R.id.class_title_list);
        optionListView = (ListView)findViewById(R.id.class_options_list);
    }

    private void initEvent() {
        titleListView.setOnItemClickListener(this);
        optionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("title",currentTitle);
                intent.putExtra("class", rightOptions.get(position));
                ClassificationActivity.this.setResult(RESULT_OK, intent);
                ClassificationActivity.this.finish();
            }
        });
    }

    private void initAdapter() {
        if(mClass!=null) {
            leftTitles = mClass.getTitles();
            classOptions = mClass.getItems();
            leftTitleAdapter =
                    new ArrayAdapter<String>(this,R.layout.mrele_frag2_label_grid_moban,R.id.mrele_frag2_grid_item_btn,leftTitles);
            titleListView.setAdapter(leftTitleAdapter);
        }
    }

    //记录上次optionListView被点击到变化颜色的item的View的对象
    private View optionListItemView;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(optionListItemView!=null){
            optionListItemView.setBackgroundColor(Color.WHITE);
        }
        optionListItemView = view;
        view.setBackgroundColor(Color.parseColor("#dbdbdb"));
        currentTitle = leftTitles.get(position);
        rightOptions = classOptions.get(currentTitle);
            rightOptionsAdapter =
                    new ArrayAdapter<String>(this,R.layout.mrele_frag2_label_grid_moban,R.id.mrele_frag2_grid_item_btn,rightOptions);
            optionListView.setAdapter(rightOptionsAdapter);
    }
}
