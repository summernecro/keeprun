package com.summer.keeprun.main;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.lib.constant.ValueConstant;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.util.LogUtil;
import com.android.lib.util.SPUtil;
import com.android.lib.util.StringUtil;
import com.android.lib.util.ToastUtil;
import com.android.lib.util.data.DateFormatUtil;
import com.android.lib.util.system.HandleUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OneReceiver extends BroadcastReceiver {

    private long starttime = 0;
    @Override
    public void onReceive(final Context context, Intent intent) {

        int count = intent.getIntExtra("DATA_DATA",0);
        if(count==0){
            starttime = System.currentTimeMillis();
        }
        String str = getTimeDistance(DateFormatUtil.getdDateStr(DateFormatUtil.YYYY_MM_DD_HH_MM_SS,new Date(starttime)),
                DateFormatUtil.getdDateStr(DateFormatUtil.YYYY_MM_DD_HH_MM_SS,new Date(System.currentTimeMillis())));
        ToastUtil.getInstance().showShort(context,"AppOneService 成功运行第"+StringUtil.getStr(count)+"次 "+ str );
        SPUtil.getInstance().saveStr(ValueConstant.DATA_DATA,str);
        KeepRun keepRun = new KeepRun();
        keepRun.setText("AppOneService "+StringUtil.getStr(count)+" 次 AppTwoService 是否活着");
        keepRun.setTime(DateFormatUtil.getdDateStr(DateFormatUtil.DD_HH_MM_SS,new Date(System.currentTimeMillis())));
        NetServer.postKeepRun(context, keepRun, new NetAdapter(context));
    }

    public static String getTimeDistance(String startTime, String endTime) {
        try {
            LogUtil.E(startTime + "&&" + endTime);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            new SimpleDateFormat("H:mm:ss");
            Date startDate = format.parse(startTime);
            Date endDate = format.parse(endTime);
            long between = (endDate.getTime() - startDate.getTime()) / 1000L;
            long day1 = (between / 86400);
            long hour1 = (between / 3600L)% 24 ;
            long minute1 = (between / 60L)% 60 ;
            long second1 = between%60;
            return day1 + "天" + hour1 + "时"+minute1+"分"+second1+"秒";
        } catch (ParseException var14) {
            var14.printStackTrace();
            return "";
        }
    }

    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);
        if (!(serviceList.size() > 0)) {
            return false;
        }
        Log.e("OnlineService：",className);
        for (int i = 0; i < serviceList.size(); i++) {
            Log.e("serviceName：",serviceList.get(i).service.getClassName());
            if (serviceList.get(i).service.getClassName().contains(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
