package com.summer.keeprun.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;

import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.util.LogUtil;
import com.android.lib.util.system.SystemUtil;

public class MainAct extends BaseUIActivity<MainUIOpe,MainValue> {


    boolean i = false;

    public static OneReceiver oneReceiver;

    @Override
    protected void initNow() {
        super.initNow();
        LogUtil.E("000000"+SystemUtil.getAppName(this,android.os.Process.myPid()));
        if(!i){
            LogUtil.E("11111111");
            startBackGroundOne(this);
            i = true;
        }
    }

    public static  void startBackGroundOne(Context context) {
        if(OneReceiver.isServiceRunning(context,AppOneService.class.getName())){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, AppOneService.class));
        } else {
            context.startService(new Intent(context, AppOneService.class));
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppOneService.class.getSimpleName());
        if(oneReceiver!=null){
            context.unregisterReceiver(oneReceiver);
            oneReceiver= null;
        }
        oneReceiver = new OneReceiver();
        context.registerReceiver(oneReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  stopService(new Intent(this,AppOneService.class));
    }
}
