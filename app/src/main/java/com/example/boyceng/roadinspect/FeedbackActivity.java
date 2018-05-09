package com.example.boyceng.roadinspect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scrat.app.selectorlibrary.ImageSelector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FeedbackActivity extends BaseActivity {

    //一下添加图片反馈  这是网上找的
    private final String CLASS_TAG = this.getClass().getSimpleName();
    private RelativeLayout textviewLayout;
    private RelativeLayout pictureLayout;
    private EditText telET;
    private TextView currentPicTV;
    private EditText feedbackET;
    private TextWatcher watcher;


    private List<Bitmap> mGridViewDatas;
    private List<Uri> mGridViewUris;
    private GridView mGridView;
  //  private GridViewAdapter mGridViewAdapter;
    private Bitmap mBitmap;
    private static int REQUEST_ADD_PIC = 1;
    private static int REQUEST_SHOW_PIC = 2;
    private static int MAX_UPLOAD_PIC = 4;
    private static String SHOW_PIC_URI = "show_pic_full_screen";
    private static String PIC_POSITION = "picture_position";
  //  private WaitingDialog waitingDialog;
    private boolean uploadSucc = false;

    //添加select 图片
    private static final int REQUEST_CODE_SELECT_IMG = 1;
    private static final int MAX_SELECT_COUNT = 9;
    private TextView mContentTv;
    private Button feedbackBtn;
    private String feedbacktext = "";
    private EditText help_feedback;


        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_feedback);
            initView();
            Intent intent = getIntent();
            help_feedback = (EditText) findViewById(R.id.help_feedback);

            //这两行修改了 fruitName里修改标题
            String fruitName = " 建议和反馈";
            //int fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            ImageView fruitImageView = (ImageView) findViewById(R.id.fruit_image_view);
            TextView fruitContentText = (TextView) findViewById(R.id.fruit_content_text);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            collapsingToolbar.setTitle(fruitName);
            //以下为了添加图片上传功能
            setContentView(R.layout.activity_feedback);
            //    initLayoutParameters();

            //提交按钮判断
            feedbackBtn = (Button) findViewById(R.id.feedbackBtn);
            feedbackBtn.setOnClickListener(feedbackClick);
           /* feedbackBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                }
            });*/
            help_feedback.setText("");
        }

        //判断提交按钮

               View.OnClickListener feedbackClick = new View.OnClickListener() {
        public void onClick(final View v) {
                if(checkEmpty()){
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.feed_ok),Toast.LENGTH_SHORT).show();
                       finish();
                }else{
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.feed_empty),Toast.LENGTH_SHORT).show();
                }
            }
        };










        //添加select 图片
        private void initView() {
            mContentTv = (TextView) findViewById(R.id.content);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_IMG) {
            showContent(data);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showContent(Intent data) {
        List<String> paths = ImageSelector.getImagePaths(data);
        if (paths.isEmpty()) {
            mContentTv.setText(R.string.image_selector_select_none);
            return;
        }

        mContentTv.setText(paths.toString());
    }

    public void selectImg(View v) {
        ImageSelector.show(this, REQUEST_CODE_SELECT_IMG, MAX_SELECT_COUNT);
    }



    /*
    private void initViews(){

        pictureLayout = (RelativeLayout) findViewById(R.id.pictureLayout);
        telET = (EditText) findViewById(R.id.telET);*/
        //  submitBT = (RoundConerTextView) findViewById(R.id.submitBT);

     //   feedbackET = (EditText) findViewById(R.id.feedbackET);
        //currentNumberTV = (TextView) findViewById(R.id.currentNumberTV);
        //监控输入字数

/*
        feedbackET.addTextChangedListener(watcher);

        mGridView=(GridView) findViewById(R.id.gridview);
        mGridViewDatas = new ArrayList<>();
        mGridViewUris = new ArrayList<>();
     *//*   Bitmap addBitmap = Utils.getInstance().readBitmap(getApplicationContext(), R.drawable.add_pic);
        mGridViewDatas.add(0, addBitmap);
        mGridViewUris.add(0, new Uri.Builder().build());
        mGridViewAdapter = new GridViewAdapter(getApplication(), mGridViewDatas, MAX_UPLOAD_PIC);
        mGridView.setAdapter(mGridViewAdapter);*//*
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pictureNumber = mGridViewDatas.size();
               // ECMLog.i_ui(CLASS_TAG,"position: " + position + " pictureNumber: " + pictureNumber);
                if (position == pictureNumber - 1){
                    if (pictureNumber < MAX_UPLOAD_PIC + 1){
                        chosePictureFromPhone();
                    }
                }else {*/
                  /*  Uri uri = mGridViewUris.get(position);
                    Intent intent = new Intent(FeedbackActivity.this, PicShowerActivity.class);
                    intent.putExtra(SHOW_PIC_URI, uri.toString());
                    intent.putExtra(PIC_POSITION, position);
                    startActivityForResult(intent, REQUEST_SHOW_PIC);*/


   /* private void initLayoutParameters(){
        GlobalData globalDate = (GlobalData) getApplication();

        RelativeLayout.LayoutParams layoutparam = (RelativeLayout.LayoutParams) textviewLayout.getLayoutParams();
        layoutparam.height = 350 * globalDate.mScreenHeight / 1920;
        textviewLayout.setLayoutParams(layoutparam);

        layoutparam = (RelativeLayout.LayoutParams) pictureLayout.getLayoutParams();
        layoutparam.height = 430 * globalDate.mScreenHeight / 1920;
        layoutparam.topMargin = 60 * globalDate.mScreenHeight / 1920;
        pictureLayout.setLayoutParams(layoutparam);


        TextView pictureTV = (TextView) findViewById(R.id.pictureTV);
        pictureTV.setTextSize(COMPLEX_UNIT_PX, 46 * globalDate.mScreenHeight / 1920);

        TextView allowedPicTV = (TextView) findViewById(R.id.allowedPicTV);
        allowedPicTV.setTextSize(COMPLEX_UNIT_PX, 40 * globalDate.mScreenHeight / 1920);

        currentPicTV = (TextView) findViewById(R.id.currentPicTV);
        currentPicTV.setTextSize(COMPLEX_UNIT_PX, 40 * globalDate.mScreenHeight / 1920);

        TextView telTV = (TextView) findViewById(R.id.telTV);
        telTV.setTextSize(COMPLEX_UNIT_PX, 40 * globalDate.mScreenHeight / 1920);

        telET = (EditText) findViewById(R.id.telET);
        telET.setTextSize(COMPLEX_UNIT_PX, 50 * globalDate.mScreenHeight / 1920);

        layoutparam = (RelativeLayout.LayoutParams) telET.getLayoutParams();
        layoutparam.height = 128 * globalDate.mScreenHeight / 1920;
        telET.setLayoutParams(layoutparam);


    }*/

    private void chosePictureFromPhone(){
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(intent, REQUEST_ADD_PIC);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_ADD_PIC) {
            Uri uri = data.getData();
            File myFile = new File(uri.getPath());
            myFile.getAbsolutePath();


            String picPath = Utils.getFilePathFromUri(getApplicationContext(), uri);
            if (picPath == null){
                ContentResolver cr = this.getContentResolver();
                try {
                    mBitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else mBitmap = Utils.getSmallBitmap(picPath, 480, 800);

            //加到第一个
            mGridViewUris.add(0, uri);
            mGridViewDatas.add(0, mBitmap);

            mGridViewAdapter=new GridViewAdapter(getApplication(), mGridViewDatas, MAX_UPLOAD_PIC);
            mGridView.setAdapter(mGridViewAdapter);
            mGridViewAdapter.notifyDataSetChanged();

        }else if (resultCode == RESULT_OK && requestCode == REQUEST_SHOW_PIC){
            int position = data.getIntExtra(PIC_POSITION, 0);
            mGridViewDatas.remove(position);
            mGridViewUris.remove(position);
            mGridViewAdapter=new GridViewAdapter(getApplication(), mGridViewDatas, MAX_UPLOAD_PIC);
            mGridView.setAdapter(mGridViewAdapter);
            mGridViewAdapter.notifyDataSetChanged();
        }

        //包含添加图片
        int totalPictures = mGridViewDatas.size();
        currentPicTV.setText(String.valueOf(totalPictures - 1));
    }

    private List<File> creatUploadFiles(){
        //包含文本、图片
        List<File> fileList = new ArrayList<>();
        File suggestFile = creatSuggestionFile();
        if (suggestFile != null){
            String tel = telET.getText().toString();
            if (tel.length() > 0){
                tel = "\n" + "tel:" + tel;
                appendStringToFile(getApplicationContext(), suggestFile.getName(), tel);
            }
            fileList.add(suggestFile);
        }

        //不包括添加图片
        int picCount = mGridViewDatas.size() - 1;

        for (int i = 0;i < picCount;i++){
            //phoneNumber_tfNumber_20170101010101_1
            String bitmapFileName = getFileName() + "_" + i + ".webp";
            Bitmap bitmap = mGridViewDatas.get(i);
            File bitmapFile = saveBitmapToFile(bitmap, bitmapFileName);
            fileList.add(bitmapFile);
        }

        return fileList;
    }*/

    /**
     * phoneNumber_tfNumber_time
     * 没有后缀
     * @return
     */
    private String getFileName(){

        String fileName;
        String phoneNumber = "";
        String tfNumber = "";
        String time = "";

    /*    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        time = sdf.format(new Date());*/

        //    ..............

        fileName = phoneNumber +"_" + tfNumber +"_" + time;
        return fileName;
    }

    private File creatSuggestionFile(){

        String fileName;
        fileName = getFileName() + ".txt";
        String path = getApplicationContext().getFilesDir().getAbsolutePath();
        File file = new File(path, fileName);
        //保存String为文件
       // writeStringToFile(getApplication(), feedbackET.getText().toString(), file.getName());
        return file;
    }

    private File saveBitmapToFile(Bitmap bitmap, String fileName){

        String path = getApplicationContext().getFilesDir().getAbsolutePath();
        File file = new File(path, fileName);
        if(file.exists()){
            file.delete();
        }

        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.WEBP, 50, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

   /* private List<File> creatFilesThread(){
        List<File> fileList = null;
        ExecutorService cacheThreadExecutor = Executors.newSingleThreadExecutor();
        Future<List<File>> future = cacheThreadExecutor.submit(new preUploadTask());

        try {
            fileList = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return fileList;
    }*/

/*    private void ExecutorServiceThread(List<File> files) {

        final String path = getApplicationContext().getFilesDir().getAbsolutePath();
        int fileCount = files.size();
        MyThreadPool myThreadPool = new MyThreadPool(2, 4, 1,
                TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(fileCount), files);
        for (int i = 0; i < fileCount; i++) {
            final File file = files.get(i);
            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    int ret = ftpUpload(ftpUrl, fptPort, ftpUserName, ftpPassword, ftpRemotePath, path, file.getName());
                    //    ECMLog.i_ui(CLASS_TAG, "ftpUpload ret: " + ret);
                    uploadSucc = ret == FTP_UPLOAD_SUCC;
                }
            };
            myThreadPool.execute(runnable);
        }

        myThreadPool.shutdown();
    }*/
/*

    class preUploadTask implements Callable<List<File>> {

        @Override
        public List<File> call() throws Exception {
          */
/*  if (!Utils.ping()){
                return null;
            }*//*

        //    return creatUploadFiles();
        }
    }
*/

    class MyThreadPool extends ThreadPoolExecutor {

        private List<File> fileList;
        private MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                             BlockingQueue<Runnable> workQueue, List<File> files) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
            fileList = files;
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
        }

        @Override
        protected void terminated() {
            super.terminated();
            //当调用shutDown()或者shutDownNow()时会触发该方法
    /*        waitingDialog.dismissAllowingStateLoss();
            for (File file:fileList){
                deleteOldLogcatFile(getApplicationContext(), file.getName());
            }
*/
            //必须用looper才能在线程中用toast
            Looper.prepare();
            if (uploadSucc){
                Toast.makeText(getApplicationContext(), "上传完成", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(getApplicationContext(), "上传失败！", Toast.LENGTH_LONG).show();
            }
            Looper.loop();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {

        if (mBitmap != null &&!mBitmap.isRecycled()){
            mBitmap.recycle();
            mBitmap = null;
        }

        mGridView = null;
        super.onDestroy();
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }

    private boolean checkEmpty(){
        feedbacktext = help_feedback.getText().toString().trim();
        boolean ret = false;
        if(help_feedback.equals("")){
            Toast.makeText(getApplicationContext(),
                    getString(R.string.remain_empty),Toast.LENGTH_SHORT).show();
            ret = false;
        }else{
            ret = true;
        }
        return ret;
    }
}





