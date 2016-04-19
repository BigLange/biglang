package com.lang.big.biglang.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lang.big.biglang.R;
import com.lang.big.biglang.fragment.MreleFragment1;
import com.lang.big.biglang.utils.MyThreadPool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;

public class MReleaseActivity extends FragmentActivity implements Runnable{

    private ProgressBar mProg1;
    private ProgressBar mProg2;
    private ProgressBar mProg3;

    private FragmentManager mFManager;
    private FragmentTransaction mFTransaction;

    private HashSet<String> mDirPaths = new HashSet<>();

    //记录当前应该在线程中改变第几个头部的进度条
    private int number = 1;
    //记录改变的值
    private int current = 0;

    private MreleFragment1 addimgFragment;

    //线程池
    private ExecutorService pool;

    //进入相机请求码
    private final int CAPTURECODE = 111;
    //进入相册请求码
    private final int ALBUMCODE = 222;

    //这个属性记录这个页面第一个添加图片的fragment的一个自定义的菜单弹出的状态，判断，如果是弹出的状态的话，那么back的时候 ，就需要先处理这个状态
    public boolean isAddImgstae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mrelease);
        initVaues();
        intoView();
        intoFragment();
    }

    private void initVaues() {
        Intent intent = getIntent();
        int state = intent.getIntExtra("intent", -1);
        if(state==1){
            startCapture();
        }else if(state==2){
            startAlbum();
        }
    }


    //进入相机的意图
    private void startCapture(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURECODE);
    }

    //进入相册意图
    private void startAlbum(){
        Intent intent = new Intent(this, ImageSelectActivity.class);
        startActivityForResult(intent, ALBUMCODE);
    }

    private void intoFragment() {
        //首先创建好第一个页面的fragment
        addimgFragment = new MreleFragment1();
        setFragment1Callback();

        //获取当前的管理对象
        mFManager = getFragmentManager();
        mFTransaction = mFManager.beginTransaction();
        mFTransaction.add(R.id.mrele_frag_layout,addimgFragment);
        mFTransaction.commit();
    }

    private void setFragment1Callback() {
        addimgFragment.setCaptureClick(new MreleFragment1.OnCaptureBtnClickListeren() {
            @Override
            public void onCaptureBtnClick() {
                startCapture();
            }
        });

        addimgFragment.setAlbumClick(new MreleFragment1.OnAlbumBtnClickListeren() {
            @Override
            public void onAlbumBtnClick() {
                startAlbum();
            }
        });

        addimgFragment.setNextFragment(new MreleFragment1.Frag1ToFrag2Listener() {
            @Override
            public void nextFragment() {
                numberadd();
            }
        });
    }

    private void numberadd(){
        number++;
        pool.submit(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode == CAPTURECODE){
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                setPathToSet(bm);
            }else if(requestCode == ALBUMCODE){
                Bundle bundle = data.getExtras();
                HashSet<String> albumPath = (HashSet<String>)bundle.getSerializable("dirPaths");
                mDirPaths.addAll(albumPath);
            }
            addimgFragment.setmDatas(mDirPaths);
        }
    }

    public void setPathToSet(Bitmap bm) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(MReleaseActivity.this, "SD卡不能使用", Toast.LENGTH_SHORT).show();
            return;
        }
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "LangTaoSha");
        if (!file.exists()) {
            file.mkdir();
        }
        FileOutputStream fos = null;
        String imgName = System.currentTimeMillis() + ".jpg";
        File imgFile = new File(file, imgName);
        try {
            fos = new FileOutputStream(imgFile);
            bm.compress(Bitmap.CompressFormat.JPEG,100,fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mDirPaths.add(imgFile.getAbsolutePath());
    }
}
