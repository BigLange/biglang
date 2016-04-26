package com.lang.big.biglang.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Size;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lang.big.biglang.R;
import com.lang.big.biglang.fragment.MreleFragment1;
import com.lang.big.biglang.fragment.MreleFragment2;
import com.lang.big.biglang.fragment.MreleFragment3;
import com.lang.big.biglang.utils.MyThreadPool;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;

public class MReleaseActivity extends Activity implements Runnable {

    private ProgressBar mProg1;
    private ProgressBar mProg2;
    private ProgressBar mProg3;

    private ImageView mback;

    private FragmentManager mFManager;
    private FragmentTransaction mFTransaction;

    private ArrayList<String> mDirPaths = new ArrayList<>();

    private HashSet<String> mShujuhuanchon = new HashSet<>();

    //记录当前应该在线程中改变第几个头部的进度条
    private int number = 1;
    //记录改变的值
    private int current = 0;

    private MreleFragment1 addimgFragment;
    private MreleFragment2 setVersionsAndLabelFragment;
    private MreleFragment3 setPiceAndFranking;

    private Fragment currentFragment;


    //线程池
    private ExecutorService pool;

    //进入相机请求码
    private final int CAPTURECODE = 111;
    //进入相册请求码
    private final int ALBUMCODE = 222;

    private final int IMGPRE = 333;

    //记录当前商品的大的分类
    private String classtitle;
    //记录当前的商品的分类
    private String classification;
    //当前商品的描述信息
    private String mDiscribe;

    //这个属性记录这个页面第一个添加图片的fragment的一个自定义的菜单弹出的状态，判断，如果是弹出的状态的话，那么back的时候 ，就需要先处理这个状态
    public boolean isAddImgstae;

    //记录当前选中的商品的特色标签
    private HashMap<Integer, String> frag2_labels;

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
        if (state == 1) {
            startCapture();
        } else if (state == 2) {
            startAlbum();
        }
    }


    //进入相机的意图
    private void startCapture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURECODE);
    }

    //进入相册意图
    private void startAlbum() {
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
        mFTransaction.add(R.id.mrele_frag_layout, addimgFragment);
        mFTransaction.commit();
        currentFragment = addimgFragment;
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
            public void nextFragment(String shopName) {
                numberadd();
                mFTransaction = mFManager.beginTransaction();
                if (setVersionsAndLabelFragment == null) {
                    setVersionsAndLabelFragment = new MreleFragment2();
                    mFTransaction.add(R.id.mrele_frag_layout, setVersionsAndLabelFragment);
                    setFragment2Callback();
                }
                fragmentShowToHided(setVersionsAndLabelFragment);
            }
        });

        addimgFragment.setStartImgPreListener(new MreleFragment1.StartImgPreListener() {
            @Override
            public void startImgPre(int index) {
                Intent intent = new Intent(MReleaseActivity.this, ImagePreview.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dirPaths", mDirPaths);
                bundle.putInt("index", index);
                intent.putExtras(bundle);
                startActivityForResult(intent, IMGPRE);
                overridePendingTransition(R.anim.img_pre_start, R.anim.img_pre_close);
            }
        });
    }

    //选择分类界面请求码
    private final static int CLASSFICATIONCODE = 233;

    private void setFragment2Callback() {
        setVersionsAndLabelFragment.setVersionsBtnClickListener(new MreleFragment2.VersionsBtnClickListener() {
            @Override
            public void onVersionsBtnClick() {
                Intent intent = new Intent(MReleaseActivity.this, ClassificationActivity.class);
                startActivityForResult(intent, CLASSFICATIONCODE);
            }
        });

        setVersionsAndLabelFragment.setGridItemOnclickListener(new MreleFragment2.GridItemOnclickListener() {
            @Override
            public void onItemClick(View view, int position, String shopLabel) {
                TextView textView = (TextView) view.findViewById(R.id.mrele_frag2_grid_item_btn);
                if (frag2_labels == null) {
                    frag2_labels = new HashMap<Integer, String>();
                }
                if (frag2_labels.get(position) != null) {
                    textView.setBackgroundColor(Color.WHITE);
                    textView.setTextColor(Color.parseColor("#333333"));
                    frag2_labels.remove(position);
                } else {
                    textView.setBackgroundColor(Color.parseColor("#FF472d"));
                    textView.setTextColor(Color.WHITE);
                    frag2_labels.put(position, shopLabel);
                }
            }
        });
        setVersionsAndLabelFragment.setUpNextBtnOnClickListener(new MreleFragment2.UpNextBtnOnClickListener() {
            @Override
            public void upNextBtnOnClick() {
                frag2ToFrag1();
            }
        });

        setVersionsAndLabelFragment.setDownNextBtnOnClickListener(new MreleFragment2.DownNextBtnOnClickListener() {
            @Override
            public void downNextBtnOnClick(EditText mDescribeEdt) {
                if(classtitle==null&&classification==null){
                    Toast.makeText(MReleaseActivity.this, "给宝贝分好类吧...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(frag2_labels.size()<1){
                    Toast.makeText(MReleaseActivity.this, "给宝贝来几个标签吧...", Toast.LENGTH_SHORT).show();
                    return;
                }
                mDiscribe = mDescribeEdt.getText().toString();
                if(mDiscribe.length()<1){
                    Toast.makeText(MReleaseActivity.this, "描述一下自己的宝贝吧...", Toast.LENGTH_SHORT).show();
                    return;
                }
                mFTransaction = mFManager.beginTransaction();
                if (setPiceAndFranking == null) {
                    setPiceAndFranking = new MreleFragment3();
                    mFTransaction.add(R.id.mrele_frag_layout, setPiceAndFranking);
                    setFragment3Callback();
                }
                numberadd();
                fragmentShowToHided(setPiceAndFranking);
            }
        });

    }

    private void setFragment3Callback() {
        setPiceAndFranking.setUpNextBtnOnClickListener(new MreleFragment3.UpNextBtnOnClickListener() {
            @Override
            public void upNextBtnOnClick() {
                frag3Tofrag2();
            }
        });
    }


    private void numberadd() {
        number++;
        pool.submit(this);
    }

    private void numberCut(){
        current = 100;
        System.out.println("heiheiheihei:" + number);
        pool.submit(new Runnable() {
            @Override
            public void run() {
                ProgressBar progressBar = null;
                switch (number) {
                    case 1:
                        progressBar = mProg1;
                        break;
                    case 2:
                        progressBar = mProg2;
                        System.out.println("heiheiheihei");
                        break;
                    case 3:
                        progressBar = mProg3;
                        break;
                }
                while (current > 0) {
                    progressBar.setProgress(current--);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                number--;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //获得当前线程池
        pool = MyThreadPool.getMyThreadPool().getPool();
        pool.submit(this);
    }

    private void intoView() {
        mProg1 = (ProgressBar) findViewById(R.id.progressBar1);
        mProg2 = (ProgressBar) findViewById(R.id.progressBar2);
        mProg3 = (ProgressBar) findViewById(R.id.progressBar3);
        mback = (ImageView) findViewById(R.id.mRele_head_up_next);
        mback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void frag2ToFrag1() {
        numberCut();
        mFTransaction = mFManager.beginTransaction();
        fragmentShowToHided(addimgFragment);
    }

    private void frag3Tofrag2() {
        numberCut();
        mFTransaction = mFManager.beginTransaction();
        fragmentShowToHided(setVersionsAndLabelFragment);
    }

    private void fragmentShowToHided(Fragment mFragemnt) {
        mFTransaction.hide(currentFragment);
        mFTransaction.show(mFragemnt);
        currentFragment = mFragemnt;
        mFTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (currentFragment == setPiceAndFranking) {
            frag3Tofrag2();
            return;
        }
        if(currentFragment==setVersionsAndLabelFragment){
            frag2ToFrag1();
            return;
        }
        if (isAddImgstae) {
            addimgFragment.hideAddImgOptions();
            isAddImgstae = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void run() {
        ProgressBar progressBar = null;
        switch (number) {
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
        while (current < 100) {
            progressBar.setProgress(current++);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        current = 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURECODE) {
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                setPathToSet(bm);
                addimgFragment.setmDatas(mDirPaths);
            } else if (requestCode == ALBUMCODE) {
                Bundle bundle = data.getExtras();
                HashSet<String> albumPath = (HashSet<String>) bundle.getSerializable("dirPaths");
                mShujuhuanchon.addAll(albumPath);
                mDirPaths.clear();
                mDirPaths.addAll(mShujuhuanchon);
                addimgFragment.setmDatas(mDirPaths);
            } else if (requestCode == IMGPRE) {
                int index = data.getIntExtra("index", -1);
                mShujuhuanchon.remove(mDirPaths.remove(index));
                addimgFragment.setmDatas(mDirPaths);
            } else if (requestCode == CLASSFICATIONCODE) {
                classification = data.getStringExtra("class");
                classtitle = data.getStringExtra("title");
                setVersionsAndLabelFragment.setClassBtnText(classification);
            }
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
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mDirPaths.add(imgFile.getAbsolutePath());
        mShujuhuanchon.add(imgFile.getAbsolutePath());
    }


}
