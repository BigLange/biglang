package com.lang.big.biglang.fragment;

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

import com.lang.big.biglang.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MreleFragment2 extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener{
    private GridView mLabelGrid;

    private Button mClassificationBtn;
    private EditText mDescribeEdt;
    private Button upNextBtn;
    private Button downNextBtn;

    private View nextBtnLayout;

    private ArrayAdapter<String> mAdapter;

    private String[] labels = new String[]{"全新未用","验货面付","快递包邮","一口价","配件齐全","保修期内","无拆无修","大陆行货"};


    public MreleFragment2() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mrele_fragment2, container, false);
        initView(v);
        initEvent();
        return v;
    }

    private void initView(View v) {
        mLabelGrid = (GridView)v.findViewById(R.id.mrele_frag2_label_grid);
        mDescribeEdt = (EditText)v.findViewById(R.id.mrele_fragment2_describe_edt);
        mClassificationBtn = (Button)v.findViewById(R.id.mrele_frag2_classification_btn);
        upNextBtn = (Button)v.findViewById(R.id.mrele_fragment2_up_next_btn);
        downNextBtn = (Button)v.findViewById(R.id.mrele_fragment2_down_next_btn);
        nextBtnLayout = v.findViewById(R.id.mrele_next_btn_layout);
        mAdapter = new ArrayAdapter<String>(getActivity(),R.layout.mrele_frag2_label_grid_moban,R.id.mrele_frag2_grid_item_btn,labels);
        mLabelGrid.setAdapter(mAdapter);
    }

    private void initEvent() {
        mLabelGrid.setOnItemClickListener(this);
        mClassificationBtn.setOnClickListener(this);
        upNextBtn.setOnClickListener(this);
        downNextBtn.setOnClickListener(this);

    }



    private VersionsBtnClickListener versionsBtnClickListener;
    private UpNextBtnOnClickListener upNextBtnOnClickListener;
    private DownNextBtnOnClickListener downNextBtnOnClickListener;

    //mrele_frag2_versions_btn按钮回调的接口
    public interface VersionsBtnClickListener{
        void onVersionsBtnClick();
    }
    //上一步按钮被点击回调接口
    public interface UpNextBtnOnClickListener{
        void upNextBtnOnClick();
    }
    //下一步按钮被点击的接口的回调
    public interface DownNextBtnOnClickListener{
        void downNextBtnOnClick(EditText edt);
    }


    public void setVersionsBtnClickListener(VersionsBtnClickListener v){
        versionsBtnClickListener = v;
    }

    public void setUpNextBtnOnClickListener(UpNextBtnOnClickListener up){
        upNextBtnOnClickListener = up;
    }

    public void setDownNextBtnOnClickListener(DownNextBtnOnClickListener down){
        downNextBtnOnClickListener = down;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mrele_frag2_classification_btn:
                if(versionsBtnClickListener!=null){
                    versionsBtnClickListener.onVersionsBtnClick();
                }
                break;
            case R.id.mrele_fragment2_up_next_btn:
                if(upNextBtnOnClickListener!=null){
                    upNextBtnOnClickListener.upNextBtnOnClick();
                }
                break;
            case R.id.mrele_fragment2_down_next_btn:
                if(downNextBtnOnClickListener!=null){
                    downNextBtnOnClickListener.downNextBtnOnClick(mDescribeEdt);
                }
                break;
        }
    }

    private GridItemOnclickListener giol;
    public interface GridItemOnclickListener{
       void  onItemClick(View view, int position,String shopLabel);
    }

    public void setGridItemOnclickListener(GridItemOnclickListener giol){
        this.giol = giol;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        view.setBackgroundColor(Color.parseColor("#FF472d"));
        if(giol!=null){
            String shopLabel = labels[position];
            giol.onItemClick(view,position,shopLabel);
        }
    }

    //设置返回回来的类型到那个按钮上
    public void setClassBtnText(String classifiaction){
        mClassificationBtn.setText(classifiaction);
    }

}
