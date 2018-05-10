package com.example.boyceng.roadinspect;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Boyce Ng on 2017/5/25.
 */

public class SensingFunction extends Service implements SensorEventListener
{
    private SensorManager mSensorManager;
    private float valx,valy,valz;
    public static SensingFunction uniqueInstance = null;
    private Timer timer;
    private TimerTask task;
    int c;
    float[] Listx,Listy,Listz;

    public static SensingFunction getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SensingFunction();
        }
        return uniqueInstance;
    }

    public float getValueX()
    {
        return valx;
    }

    public float getValueY()
    {
        return valy;
    }

    public float getValueZ()
    {
        return valz;
    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }


    public float cal_avg(float[] a)
    {
        if(a!=null) {
            float sum=0;
            for(int i=0;i<a.length;i++)
            {
                sum += a[i];
            }
            return sum/a.length;
        }
        else
            return -Float.MAX_VALUE;
    }

    public float cal_cov(float[] a, float[] b) {
        if(a!=null && b!=null)
        {
            int lena = a.length;
            int lenb = b.length;
            float cov = 0;
            if (lena == lenb) {
                float avga, avgb;
                avga = cal_avg(a);
                avgb = cal_avg(b);
                for (int i = 0; i < lena; i++) {
                    cov += (a[i] - avga) * (b[i] - avgb);
                }
                return cov / (a.length - 1);
            } else
                return -Float.MAX_VALUE;
        }
        else
            return -Float.MAX_VALUE;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d("SensingFunction","sensor create"); //以下是注册传感器
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener( this, mSensorManager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER ),1000);


    }


    //sever  启动时会执行  一般在这里 做些什么..
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }



   @Override
    public void onDestroy()
    {
        mSensorManager.unregisterListener(this);
        super.onDestroy();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){ }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float val[] = event.values;      //val 数组里就是加速度三个值 （包含了重力加速的的值？需要剔除？）
        valx=val[0];
        valy=val[1];
        valz=val[2];
    }

}
