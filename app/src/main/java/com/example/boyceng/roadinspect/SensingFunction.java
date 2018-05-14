package com.example.boyceng.roadinspect;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Boyce Ng on 2017/5/25.
 */

public class SensingFunction extends Service
        implements SensorEventListener
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
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener( this, mSensorManager.getDefaultSensor( Sensor.TYPE_ACCELEROMETER ),1000);

        final Intent intent = new Intent();
        intent.setAction(MainActivity.ACTION_UPDATEUI);

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                c++;
                if(c%10!=0 && Listx!=null && Listy!=null && Listz!=null)
                {
                    Listx[c%10]=valx;
                    Listy[c%10]=valy;
                    Listz[c%10]=valz;
                }
                else
                {
                    //==================================计算 cov、avg、stdev===========================================
                    float cov;
                    float cov1,cov2;
                    float mritx_a = 0,mritx_c = 0,mritx_d = 0;
                    mritx_a = cal_cov(Listx,Listx);
                    mritx_c = cal_cov(Listx,Listz);
                    mritx_d = cal_cov(Listz,Listz);
                    cov1 = mritx_a * mritx_d - mritx_c * mritx_c;
                    mritx_a = 0;mritx_c = 0;mritx_d = 0;
                    mritx_a = cal_cov(Listy,Listy);
                    mritx_c = cal_cov(Listy,Listz);
                    mritx_d = cal_cov(Listz,Listz);
                    cov2 = mritx_a * mritx_d - mritx_c * mritx_c;
                    if(cov1>=cov2)
                    {
                        cov = cov1;
                    }
                    else
                    {
                        cov = cov2;
                    }

                    float aver;
                    float avg_x, avg_y, avg_z;
                    float[] sum = new float[10];
                    avg_x = cal_avg(Listx);
                    avg_y = cal_avg(Listy);
                    avg_z = cal_avg(Listz);
                    for(int i=0;i<10;i++) {
                        if(Listx!=null && Listy!=null && Listz!=null)
                        {
                            sum[i] = (Listx[i]-avg_x)*(Listx[i]-avg_x) + (Listy[i]-avg_y)*(Listy[i]-avg_y) + (Listz[i]-avg_z)*(Listz[i]-avg_z);
                            sum[i] = (float)Math.sqrt(sum[i]);
                        }
                    }
                    aver = cal_avg(sum);

                    float stdev;
                    for(int i=0;i<10;i++) {
                        if(Listx!=null && Listy!=null && Listz!=null)
                        {
                            sum[i] -= aver;
                            sum[i] = sum[i] * sum[i];
                        }
                    }
                    stdev = cal_avg(sum);
                    stdev = (float) Math.sqrt(stdev);
                    //=========================================end=====================================================

                    StringBuilder str = null;
                    str = new StringBuilder();
                    for (int i = 0; i <= 9; i++) {
                        if(Listx!=null && Listy!=null && Listz!=null)
                        {
                            str.append(Listx[i]);
                            str.append(' ');
                            str.append(Listy[i]);
                            str.append(' ');
                            str.append(Listz[i]);
                            str.append('\n');
                        }
                    }

                    intent.putExtra("info", str.toString());
                    String info = "0 1:" + cov + " 2:" + aver + " 3:" + stdev +"\n";
                    intent.putExtra("outcome", info);
                    sendBroadcast(intent);

                    Listx = new float[10];
                    Listy = new float[10];
                    Listz = new float[10];
                    Listx[c % 10] = valx;
                    Listy[c % 10] = valy;
                    Listz[c % 10] = valz;
                }
            }
        };
        timer.schedule(task, 500, 25);
    }

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
        float val[] = event.values;
        valx=val[0];
        valy=val[1];
        valz=val[2];
    }

}
