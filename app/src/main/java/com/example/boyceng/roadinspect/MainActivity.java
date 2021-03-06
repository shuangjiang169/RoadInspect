package com.example.boyceng.roadinspect;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity
{
    Button bn;
    TextView dump;
    float X,Y,Z;
    boolean is_on;
    //定位信息
    public LocationClient mLocationClient;
    private TextView positionText;
    private final boolean isfabButton = true;


    //声明一个操作常量字符串
    public static final String ACTION_UPDATEUI = "action.updateUI";
    UpdateUIBroadcastReceiver broadcastReceiver;



    //以下是调用toolbar
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.instruction:
                //toolbar上的三个按钮的intent 跳转到其他activity
            {
                Intent intent = new Intent();
                intent.putExtra("testIntent", "123");
                intent.setClass(MainActivity.this, InstructionActivity.class);
                MainActivity.this.startActivity(intent);
            }
             break;
            case R.id.advise:
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, FeedbackActivity.class);
                MainActivity.this.startActivity(intent);
            }
            break;
            case R.id.settings: {
                Intent intent = new Intent();
                //  intent.putExtra("testIntent", "123");
                intent.setClass(MainActivity.this, HelpActivity.class);
                MainActivity.this.startActivity(intent);
            }
                break;
            default:
        }
        return true;
    }
//toolbar end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//实现布局
        Log.d("MainActivity"," oncreate");
        setContentView(R.layout.activity_main); //初始化布局
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton bn = (FloatingActionButton) findViewById(R.id.power);
        //bn = (Button)findViewById(R.id.power);
        //et = (TextView)findViewById(R.id.view);
        is_on=false;



        //以下是跳动柱条
        final AudioColumnView audioColumnView = (AudioColumnView) findViewById(R.id.acv);
        audioColumnView.stop();

//以下是实现悬浮按钮 √

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "显示经纬度", Snackbar.LENGTH_SHORT)  //第一个显示内容，第二个显示时间
                        .setAction("获取", new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                if(isfabButton){
                                    positionText.setVisibility(View.VISIBLE);
                                   // isfabButton = false;
                                }else {
                                  //  positionText.setVisibility(View.GONE);
                                  //  isfabButton = true;
                                }
                            }
                        })
                        .show();
            }
        });*/

           //以下实现定位信息显示
        positionText = (TextView) findViewById(R.id.position_text_view);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            requestLocation();
        }






        try{
            Log.d("MainActivity"," try info");
            File file = new File(Environment.getExternalStorageDirectory(), "train.svm.model");
            FileOutputStream fos = new FileOutputStream(file);
            Log.d("MainActivity"," try info");
            String info = "svm_type c_svc\n" +
                    "kernel_type rbf\n" +
                    "gamma 0.5\n" +
                    "nr_class 2\n" +
                    "total_sv 54\n" +
                    "rho 0.247432\n" +
                    "label 0 1\n" +
                    "nr_sv 27 27\n" +
                    "SV\n" +
                    "3.579841760392417 1:1.5065526 2:1.4137369 3:1.0070131 \n" +
                    "2.14081013592028 1:15.139354 2:2.6920889 3:1.9682032 \n" +
                    "1.242761439663696 1:19.070361 2:2.7970196 3:1.3810352 \n" +
                    "0.2920776093270825 1:6.7208401 2:2.0728481 3:1.4711621 \n" +
                    "0.1634116221267713 1:9.3772101 2:2.3060034 3:1.2598513 \n" +
                    "0.4717392645059789 1:8.3310695 2:2.4182082 3:1.3951648 \n" +
                    "3.081738453816772 1:4.5374637 2:1.8735151 3:1.389421 \n" +
                    "0.457054541028374 1:6.1881296 2:2.4350251 3:1.1497286 \n" +
                    "0.1600995749095585 1:2.9184467 2:2.0368189 3:1.3468192 \n" +
                    "7.481838690370672 1:15.221786 2:2.7641811 3:1.2903016 \n" +
                    "0.1052070241967303 1:4.6398318 2:2.0378048 3:0.76454291 \n" +
                    "5.356619806671899 1:3.9661419 2:2.0616863 3:1.1754525 \n" +
                    "8 1:0.0086454484 2:0.46248664 3:0.27141492 \n" +
                    "5.646905056072898 1:4.0689291 2:2.1399642 3:1.1944069 \n" +
                    "3.873872722675862 1:0.088027702 2:0.83157647 3:0.52874064 \n" +
                    "0.1714367247734601 1:6.646395 2:2.2563039 3:1.0279823 \n" +
                    "8 1:13.67076 2:2.619197 3:1.4148049 \n" +
                    "2.281748934267763 1:9.9606916 2:2.4411949 3:1.3343148 \n" +
                    "4.763012639904519 1:0.73789624 2:1.2329468 3:0.77198639 \n" +
                    "8 1:0.26303091 2:0.95249233 3:0.7772573 \n" +
                    "8 1:0.0020099501 2:0.26462113 3:0.22862038 \n" +
                    "1.385252309272657 1:24.942113 2:2.2543834 3:2.4990202 \n" +
                    "8 1:0.0035327378 2:0.36694601 3:0.24710442 \n" +
                    "0.5964692559251191 1:8.2199716 2:2.2070401 3:1.369682 \n" +
                    "0.2151721285249179 1:11.679489 2:2.6648837 3:1.470015 \n" +
                    "6.266856454687951 1:2.8254858e-05 2:0.12611176 3:0.067171269 \n" +
                    "3.28823557277838 1:4.0628997 2:1.8308174 3:1.1041755 \n" +
                    "-6.638431519216506 1:14.154484 2:2.4722602 3:1.582208 \n" +
                    "-8 1:0.94387682 2:1.1994524 3:0.90386432 \n" +
                    "-0.7521847271260392 1:1056.552 2:8.6062695 3:3.5994636 \n" +
                    "-0.5882481986255641 1:36.454415 2:3.1089716 3:2.5234487 \n" +
                    "-8 1:4.1865857 2:2.053159 3:1.159591 \n" +
                    "-0.7529492717238578 1:96.784608 2:4.1883279 3:2.8285665 \n" +
                    "-0.7529196348989338 1:149.76012 2:4.4490344 3:2.9751487 \n" +
                    "-8 1:0.014318301 2:0.38458277 3:0.40139852 \n" +
                    "-0.752792280888691 1:518.28354 2:6.6797802 3:3.5488386 \n" +
                    "-2.489138340887012 1:9.6787651 2:2.1150767 3:2.2586774 \n" +
                    "-8 1:0.00039593927 2:0.17025323 3:0.43209426 \n" +
                    "-8 1:14.670373 2:2.8515284 3:1.2405111 \n" +
                    "-0.6474008363469402 1:38.099685 2:3.5129085 3:1.7679392 \n" +
                    "-0.7522681546068527 1:2328.076 2:9.4213736 3:3.7038084 \n" +
                    "-0.7524878695471756 1:270.11993 2:4.931205 3:3.4276063 \n" +
                    "-8 1:0.0024722275 2:0.22156203 3:0.34647625 \n" +
                    "-0.9469259568855823 1:23.21138 2:2.8199507 3:1.7175093 \n" +
                    "-8 1:0.25001333 2:0.93419546 3:0.51203438 \n" +
                    "-0.7527073591370387 1:247.27049 2:5.2833242 3:3.6972262 \n" +
                    "-8 1:4.0112072 2:1.965687 3:1.2812412 \n" +
                    "-0.7321382984364448 1:30.652571 2:3.2112598 3:2.1335728 \n" +
                    "-0.75255936472311 1:1143.9529 2:7.5270656 3:3.8657283 \n" +
                    "-0.7526171712818164 1:1776.8441 2:9.2729628 3:3.9028882 \n" +
                    "-8 1:0.016951021 2:0.2848464 3:0.50672934 \n" +
                    "-0.7427076088655939 1:27.953225 2:3.1583665 3:2.1578263 \n" +
                    "-0.711500401490565 1:34.187482 2:2.7406514 3:2.8224507 \n" +
                    "-0.7521847271260392 1:48.599207 2:3.428214 3:2.2149261 ";

            fos.write(info.getBytes());
            fos.close();
          Log.d("MainActivity"," train.svm.model 写入成功!!!");
        } catch (Exception e) {
             Log.d("MainActivity","Error in create!!!");
            e.printStackTrace();
        }

        final Intent intent = new Intent(this, SensingFunction.class);
        startService(intent);

        bn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               Log.d("MainActivity","Click on !!!");
                if(!is_on)
                {
                    is_on = true;
                    //换按钮图
                    FloatingActionButton bn = (FloatingActionButton) findViewById(R.id.power);
                    bn.setImageResource(R.drawable.stop);

                    startService(intent);

                    broadcastReceiver = new UpdateUIBroadcastReceiver();    //生成broadreceiver对象
                    IntentFilter filter = new IntentFilter();
                    filter.addAction(ACTION_UPDATEUI);        //通过广播更新面板界面
                    registerReceiver(broadcastReceiver, filter);        //注册broadreceiver

                }
                else
                {
                    is_on = false;
                    //换按钮图
                    FloatingActionButton bn = (FloatingActionButton) findViewById(R.id.power);
                    bn.setImageResource(R.drawable.run);
                    final AudioColumnView audioColumnView = (AudioColumnView) findViewById(R.id.acv);
                    audioColumnView.stop();
                    //结束跳动
                    stopService(intent);
                    unregisterReceiver(broadcastReceiver);     //解除注册
                }
            }
        });
    }


    private class UpdateUIBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //et.setText(String.valueOf(intent.getExtras().getString("info")));
             //收到广播后执行以下
            String outcome = null;
            outcome = String.valueOf(intent.getExtras().getString("outcome"));      //计算所得 cov、avg、stdev
            if(outcome != null)
            {
                //et.append("\n\n\n10组数据处理结果：\n");
                //et.append(outcome+"\n\n\n");

                String path = Environment.getExternalStorageDirectory().toString();
                String svmmodelname = "train.svm.model";
                String save = "test.predict";
                String predict = "test.svm";

                //====================将计算所得 cov、avg、stdev 写入predict="test.svm"文件========================
                try{
                    File file = new File(path, predict);
                    FileOutputStream fos = new FileOutputStream(file, false);
                    fos.write(outcome.getBytes());
                    fos.close();
                     Log.d("MainActivity","test.svm写入成功!!!");
                    //et.append("test.svm写入成功!!!");
                } catch (Exception e) {
                     Log.d("MainActivity","test.svm未写入!!!");
                    e.printStackTrace();
                }
                //=========================================end=====================================================

                //============================predict 结果写入save = "test.predict"文件============================
                svm_predict prediction = new svm_predict();
                String[] testArgs = {path+"/"+predict, path+"/"+svmmodelname, path+"/"+save};
                try {
                    prediction.main(testArgs);
                    Log.d("MainActivity","Predicted!!!");
                    //et.append("Predicted!!!");
                } catch (IOException e) {
                    Log.d("MainActivity","Predict Error!!!");
                    //et.append("Predict Error!!!");
                    e.printStackTrace();
                }
                //=========================================end=====================================================

                float flag = 0;
                try {
                    File file = new File(path, save);
                    FileInputStream is = new FileInputStream(file);
                    byte[] buff = new byte[1024];
                    StringBuilder sb = new StringBuilder("");
                    is.read(buff);
                    sb.append(new String(buff));
                    flag = Float.parseFloat(sb.toString());
                    is.close();
                     Log.d("MainActivity","读取成功："+sb.toString()+"\n"+flag+"\n");
                    //et.append("读取成功："+sb.toString()+"\n"+flag+"\n");
                } catch (Exception e) {
                    //System.out.println("Error");
                    e.printStackTrace();
                }

                if(flag==1)
                {
                    final AudioColumnView audioColumnView = (AudioColumnView) findViewById(R.id.acv);
                    //audioColumnView.stop();
                    //开始跳动
                   // audioColumnView.setBackgroundColor(Color.parseColor("#AA0000"));
                    audioColumnView.start();
                    //换图
                    ImageView Back = (ImageView) findViewById(R.id.dump);
                    Back.setImageResource(R.drawable.dump);

                   // ImageView imgDev = new ImageView(this);
                   // imgDev.setImageResource(R.drawable.dump);
                   // Resource dump = (Resource) findViewById(R.id.dump);
                   // dump.s(R.drawable.dump);
                   /* //et.append("dump\n");
                    RelativeLayout Back = (RelativeLayout) findViewById(R.id.background);
                    Back.setBackgroundResource(R.color.red);*/

                }
                else
                {
                    final AudioColumnView audioColumnView = (AudioColumnView) findViewById(R.id.acv);
                    audioColumnView.stop();
                    //结束跳动
                    //audioColumnView.setBackgroundColor(Color.parseColor("#006600"));
                    //换按钮图
                    ImageView Back = (ImageView) findViewById(R.id.dump);
                    Back.setImageResource(R.drawable.flat);
                    //dump.setBackgroundResource(R.drawable.flat);
                    /*//et.append("flat\n");
                    RelativeLayout Back = (RelativeLayout) findViewById(R.id.background);
                    Back.setBackgroundResource(R.color.green);*/

                }

                File file1 = new File(path, save);
                if(file1.isFile() && file1.exists())
                {
                    file1.delete();
                }

                File file2 = new File(path, predict);
                if(file2.isFile() && file2.exists())
                {
                    file2.delete();
                }

            }
        }
    }

   protected  void onDestroy()
    {
        super.onDestroy();
        mLocationClient.stop();
        unregisterReceiver(broadcastReceiver);
        File file = new File(Environment.getExternalStorageDirectory(), "train.svm.model");
        if(file.isFile() && file.exists())
        {
            file.delete();
        }
    }



  //百度定位
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setAddrType("all"); //加上这个配置后才可以取到详细地址信息
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if(location == null){
                Toast.makeText(MainActivity.this, "无法定位", Toast.LENGTH_SHORT).show();
                return ;
            }

                StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度：").append(location.getLatitude()).append("\n");
            currentPosition.append("经度：").append(location.getLongitude()).append("\n");
           /* currentPosition.append("国家：").append(location.getCountry()).append("\n");
            currentPosition.append("省：").append(location.getProvince()).append("\n");
            currentPosition.append("市：").append(location.getCity()).append("\n");
            currentPosition.append("区：").append(location.getDistrict()).append("\n");
            currentPosition.append("街道：").append(location.getStreet()).append("\n");*/
            currentPosition.append("定位方式：网络/GPS");
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                currentPosition.append("GPS");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                currentPosition.append("网络");
            }
                    positionText.setText(currentPosition);
                   }
              }



}