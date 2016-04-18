package com.lang.big.biglang.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.lang.big.biglang.R;
import com.lang.big.biglang.fragment.MreleFragment1;
import com.lang.big.biglang.utils.MyThreadPool;

import java.util.concurrent.ExecutorService;

public class MReleaseActivity extends FragmentActivity implements Runnable{

    private ProgressBar mProg1;
    private ProgressBar mProg2;
    private ProgressBar mProg3;

    private FragmentManager mFManager;
    private FragmentTransaction mFTransaction;

    //记录当前应该在线程中改变第几个头部的进度条
    private int number = 1;
    //记录改变的值
    private int current = 0;

    private MreleFragment1 addimgFragment;

    //线程池
    private ExecutorService pool;

    //这个属性记录这个页面第一个添加图片的fragment的一个自定义的菜单弹出的状态，判断，如果是弹出的状态的话，那么back的时候 ，就需要先处理这个状态
    public boolean isAddImgstae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrelease);
        intoView();
        intoFragment();
    }

    private void intoFragment() {
        //首先创建好第一个页面的fragment
        addimgFragment = new MreleFragment1();

        //获取当前的管理对象
        FragmentManager mFManager = getFragmentManager();
        mFTransaction = mFManager.beginTransaction();
        mFTransaction.add(R.id.mrele_frag_layout,addimgFragment);
        mFTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //获得当前线程池
        pool = MyThreadPool.getMyThreadPool().getPool();
        pool.submit(this);
    }

    private void intoView() {
        mProg1 = (ProgressBar)findViewById(R.id.progressBar1);
        mProg2 = (ProgressBar)findViewById(R.id.progressBar2);
        mProg3 = (ProgressBar)findViewById(R.id.progressBar3);
    }

    @Override
    public void onBackPressed() {
        if(isAddImgstae){
            addimgFragment.hideAddImgOptions();
            isAddImgstae = false;
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void run() {
        System.out.println("进来了嘛？");
        ProgressBar progressBar = null;
        switch (number){
            case 1:
                progressBar = mProg1;
                break;
            case 2:
                progressBar = mProg2;
                break;
            case 3:
                progressBar = mProg3;
                break;
        }
        while (current<100){
            progressBar.setProgress(current++);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        current = 0;
    }
}
