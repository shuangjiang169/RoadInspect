package com.example.boyceng.roadinspect;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.content.BroadcastReceiver;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;




public class MainActivity extends BaseActivity
{
    Button bn;
    float X,Y,Z;
    boolean is_on;
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
                //改这两行  它可以反映点击事件
              //  Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
               // break;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//实现布局
        Log.d("MainActivity"," oncreate");
        setContentView(R.layout.activity_main); //初始化布局
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bn = (Button)findViewById(R.id.power);
        //et = (TextView)findViewById(R.id.view);
        is_on=false;







        try{
            File file = new File(Environment.getExternalStorageDirectory(), "train.svm.model");
            FileOutputStream fos = new FileOutputStream(file);
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

            /*
            String info = "svm_type c_svc\n" +
                    "kernel_type rbf\n" +
                    "gamma 0.0078125\n" +
                    "nr_class 2\n" +
                    "total_sv 4\n" +
                    "rho -0.0504761\n" +
                    "label 0 1\n" +
                    "nr_sv 2 2\n" +
                    "SV\n" +
                    "535.5775827143784 1:0.0042218769 2:0.38286929 3:0.15925856 \n" +
                    "2048 1:0.0060628641 2:0.47086994 3:0.44959205 \n" +
                    "-2048 1:0.018807347 2:0.58605967 3:0.41533921 \n" +
                    "-535.5775827143784 1:0.055129483 2:0.63154449 3:0.48355666 ";
            */
            /*String info = "svm_type c_svc\n" + "kernel_type rbf\n" + "gamma 0.0078125\n" + "nr_class 2\n" + "total_sv 62\n" +
                    "rho -10.9291\n" + "label 1 0\n" + "nr_sv 32 30\n" + "SV\n" +
                    "2048 1:0.095355674 2:0.86046939 3:0.47450438 \n" + "2048 1:0.05750486 2:0.67505126 3:0.36559941 \n" +
                    "649.8014201811272 1:0.13292782 2:0.82821371 3:0.60014683 \n" + "2048 1:0.044329958 2:0.7209737 3:0.29723117 \n" +
                    "1159.366622669433 1:0.11675452 2:0.95029745 3:0.34260685 \n" + "2048 1:0.14348702 2:0.88237631 3:0.46986576 \n" +
                    "91.30990250559016 1:3.0300253 2:1.6498488 3:1.252028 \n" + "2048 1:0.011545771 2:0.49928484 3:0.24276833 \n" +
                    "2048 1:0.1299127 2:0.81927522 3:0.39675632 \n" + "2048 1:0.046286532 2:0.66757923 3:0.2158572 \n" +
                    "2048 1:0.10502215 2:0.83990603 3:0.45698683 \n" + "2048 1:0.032919351 2:0.67014695 3:0.43189229 \n" +
                    "2048 1:0.092108939 2:0.87184662 3:0.40868815 \n" + "2048 1:0.032286891 2:0.63350892 3:0.32694844 \n" +
                    "2048 1:0.064876701 2:0.73821829 3:0.32028452 \n" + "2048 1:0.044879516 2:0.72993438 3:0.33166641 \n" +
                    "2048 1:0.10911753 2:0.79665246 3:0.45467159 \n" + "2048 1:0.10508818 2:0.86221878 3:0.29951965 \n" +
                    "2048 1:0.088816296 2:0.86543437 3:0.43615835 \n" + "2048 1:0.12370195 2:0.89264727 3:0.46208928 \n" +
                    "2048 1:0.084661729 2:0.81908915 3:0.42261657 \n" + "2048 1:0.045955097 2:0.71723295 3:0.41555309 \n" +
                    "2048 1:0.10432212 2:0.88760468 3:0.36601453 \n" + "2048 1:0.092108939 2:0.87184662 3:0.40868815 \n" +
                    "2048 1:0.070232914 2:0.7247939 3:0.53704173 \n" + "2048 1:0.035574034 2:0.68667799 3:0.26074289 \n" +
                    "2048 1:0.1299127 2:0.81927522 3:0.39675632 \n" + "2048 1:0.09645705 2:0.85648538 3:0.32657801 \n" +
                    "2048 1:0.064735369 2:0.80591237 3:0.30879117 \n" + "2048 1:0.07893606 2:0.71163784 3:0.43297062 \n" +
                    "2048 1:0.047216173 2:0.75687257 3:0.36680373 \n" + "2048 1:1.5144071 2:1.4905222 3:0.76062718 \n" +
                    "-2048 1:0.018258006 2:0.59007974 3:0.24566353 \n" + "-1900.47794535615 1:0.016291509 2:0.51281075 3:0.25319131 \n" +
                    "-2048 1:0.071275879 2:0.74823353 3:0.37889485 \n" + "-2048 1:0.09352979 2:0.82246726 3:0.35518797 \n" +
                    "-2048 1:0.021936923 2:0.57209113 3:0.27716696 \n" + "-2048 1:0.01642925 2:0.53193185 3:0.22383042 \n" +
                    "-2048 1:0.038851092 2:0.65421278 3:0.2646406 \n" + "-2048 1:0.091441496 2:0.76514196 3:0.312945 \n" +
                    "-2048 1:0.20348695 2:0.94669957 3:0.4577389 \n" + "-2048 1:0.023771058 2:0.63977847 3:0.38273711 \n" +
                    "-2048 1:0.048008051 2:0.78152639 3:0.45495777 \n" + "-2048 1:0.21980185 2:0.92190882 3:0.44076343 \n" +
                    "-2048 1:0.066759439 2:0.82388136 3:0.50007158 \n" + "-2048 1:0.025704953 2:0.61424903 3:0.27188516 \n" +
                    "-2048 1:0.018073338 2:0.57138749 3:0.20901018 \n" + "-2048 1:0.8877585 2:1.331789 3:0.61084196 \n" +
                    "-2048 1:0.49644917 2:1.2070745 3:0.49443178 \n" + "-2048 1:0.050319636 2:0.76515762 3:0.41685501 \n" +
                    "-2048 1:0.16158417 2:0.81855236 3:0.54047366 \n" + "-2048 1:0.030366341 2:0.6331906 3:0.34055768 \n" +
                    "-2048 1:0.22526602 2:1.0486368 3:0.47580114 \n" + "-2048 1:0.050319636 2:0.76515762 3:0.41685501 \n" +
                    "-2048 1:0.69723873 2:1.3041477 3:0.4759621 \n" + "-2048 1:0.062382597 2:0.66393133 3:0.42389573 \n" +
                    "-2048 1:0.061625727 2:0.87074377 3:0.4614228 \n" + "-2048 1:0.076029975 2:0.76009008 3:0.31319015 \n" +
                    "-2048 1:0.044694002 2:0.78505421 3:0.46501949 \n" + "-2048 1:0.11393745 2:0.93008688 3:0.43558994 \n" +
                    "-2048 1:0.027583938 2:0.62632214 3:0.31729199 \n" + "-2048 1:0.047803326 2:0.91847038 3:0.55113764 "; */

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
                    startService(intent);

                    IntentFilter filter = new IntentFilter();
                    filter.addAction(ACTION_UPDATEUI);
                    broadcastReceiver = new UpdateUIBroadcastReceiver();
                    registerReceiver(broadcastReceiver, filter);

                }
                else
                {
                    is_on = false;
                    //et = (TextView)findViewById(R.id.view);
                    //et.setText("");
                    RelativeLayout Back = (RelativeLayout) findViewById(R.id.background);
                    Back.setBackgroundResource(R.color.dark);
                    stopService(intent);
                    unregisterReceiver(broadcastReceiver);
                }
            }
        });
    }


    private class UpdateUIBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //et.setText(String.valueOf(intent.getExtras().getString("info")));

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
                    //et.append("dump\n");
                    RelativeLayout Back = (RelativeLayout) findViewById(R.id.background);
                    Back.setBackgroundResource(R.color.red);
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.dump),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //et.append("flat\n");
                    RelativeLayout Back = (RelativeLayout) findViewById(R.id.background);
                    Back.setBackgroundResource(R.color.green);
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.flat),Toast.LENGTH_SHORT).show();
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

  /*  protected  void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        File file = new File(Environment.getExternalStorageDirectory(), "train.svm.model");
        if(file.isFile() && file.exists())
        {
            file.delete();
        }
    }*/
}