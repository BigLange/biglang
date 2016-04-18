package com.lang.big.biglang.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lang.big.biglang.Adapter.ImageSlectAdapter;
import com.lang.big.biglang.R;
import com.lang.big.biglang.bean.FolderBean;
import com.lang.big.biglang.utils.MyFileNameFileter;
import com.lang.big.biglang.utils.MyThreadPool;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class ImageSelectActivity extends Activity implements Runnable{

    private GridView mGridView;
    private ImageSlectAdapter imgAdapter;
    private List<String> mImgs;
    private RelativeLayout mButton;
    private TextView mDirName;
    private TextView mDirCount;


    private File currentDir;
    private int mMaxCount;

    private ProgressDialog mProgressDialog;

    private List<FolderBean> mFolderBeans = new ArrayList<>();

    private ExecutorService pool;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //这里接受到信息后，的处理
            mProgressDialog.dismiss();
            //绑定数据到View中去
            data2View();
            if (currentDir!=null) {
                imgAdapter = new ImageSlectAdapter(ImageSelectActivity.this, R.layout.iamge_select_grid_item_moban
                        , mImgs, currentDir.getAbsolutePath());
            }

            mGridView.setAdapter(imgAdapter);

            mDirCount.setText(mMaxCount + "");
            mDirName.setText(currentDir.getName());
        }
    };

    private void data2View() {
        if(currentDir==null){
            Toast.makeText(ImageSelectActivity.this, "未扫描的任何图片", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println(currentDir);
        mImgs = Arrays.asList(currentDir.list(new MyFileNameFileter()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);

        initView();
        initDatas();
        initEvent();
    }

    private void initView() {
        mGridView = (GridView)findViewById(R.id.img_select_main_grid);
        mButton = (RelativeLayout)findViewById(R.id.img_select_bottom_layout);
        mDirName = (TextView)findViewById(R.id.img_select_wenjianjia);
        mDirCount = (TextView)findViewById(R.id.img_select_number);
    }

    private void initDatas(){
        //遍历所有的图片
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(ImageSelectActivity.this, "当前存储卡不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        if(pool==null){
            pool = MyThreadPool.getMyThreadPool().getPool();
        }
        mProgressDialog = ProgressDialog.show(this,null,"正在加载...");
        pool.submit(this);
    }

    private void initEvent(){

    }

    @Override
    public void run() {

        //多线程来扫描
        //获取图片对应的Uri
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver cr = this.getContentResolver();


        Cursor cursor = cr.query(uri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[] { "image/jpeg", "image/png" },
                MediaStore.Images.Media.DATE_MODIFIED);

        Set<String> mDirPaths = new HashSet<>();

        while(cursor.moveToNext()){
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

            //获得这张图片地址的父目录的file对象
            File parenFile = new File(path).getParentFile();

            if(parenFile==null){
                continue;
            }
            String dirPath = parenFile.getAbsolutePath();
            FolderBean folderBean = null;

            if(mDirPaths.contains(dirPath)){
                continue;
            }else {
                mDirPaths.add(dirPath);
                folderBean = new FolderBean();
                folderBean.setDirPath(dirPath);
                folderBean.setFirstImgPath(path);
            }

            if(parenFile.list()==null)
                continue;

            int picSize = parenFile.list(new MyFileNameFileter()).length;

            folderBean.setCount(picSize);
            mFolderBeans.add(folderBean);

            //最开始显示的那个文件夹里面相片。这里设置为最多的显示
            if (picSize>mMaxCount){
                mMaxCount = picSize;
                currentDir = parenFile;
            }
        }
        cursor.close();
        mDirPaths.clear();

        //通知扫描结束
        mHandler.sendEmptyMessage(0x110);
    }


}
