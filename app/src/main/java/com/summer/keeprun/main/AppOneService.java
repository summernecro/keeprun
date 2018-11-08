//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.summer.keeprun.main;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.lib.base.interf.OnFinishWithObjI;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.util.LogUtil;
import com.android.lib.util.StringUtil;
import com.android.lib.util.ToastUtil;
import com.android.lib.util.data.DateFormatUtil;
import com.android.lib.util.system.AudioUtil;
import com.android.lib.util.system.HandleUtil;
import com.summer.keeprun.R;

import java.util.Date;

public class AppOneService extends Service {
    MediaPlayer mPlayer;
    int i = 0;

    public AppOneService() {
    }

    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1,new Notification()); //这个id不要和应用内的其他同志id一样，不行就写 int.maxValue()        //context.startForeground(SERVICE_ID, builder.getNotification());
        }
    }


    public int onStartCommand(Intent intent, int flags, int startId) {

        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.a10);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                Intent intent1 = new Intent(AppOneService.class.getSimpleName());
                intent1.putExtra("DATA_DATA", ++i);
                //AppOneService.this.sendBroadcast(intent1);


                KeepRun keepRun = new KeepRun();
                keepRun.setText("AppOneService "+i+" 次" );
                keepRun.setTime(DateFormatUtil.getdDateStr(DateFormatUtil.DD_HH_MM_SS,new Date(System.currentTimeMillis())));
                NetServer.postKeepRun(getApplicationContext(), keepRun, new NetAdapter(getApplicationContext()));
            }
        });
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });


//        AppThread appThread = new AppThread(new OnFinishWithObjI() {
//            public void onNetFinish(Object o) {
//                final int i = (Integer)o;
//
//            }
//        });
//        appThread.sleepTime = 5*1000l;
//        appThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if(mPlayer!=null&&mPlayer.isPlaying()){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        super.onDestroy();
    }
}