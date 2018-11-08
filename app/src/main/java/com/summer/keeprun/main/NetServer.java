package com.summer.keeprun.main;

import android.content.Context;

import com.android.lib.network.bean.req.BaseReqBean;
import com.android.lib.network.news.NetGet;
import com.android.lib.network.news.NetI;
import com.android.lib.util.GsonUtil;

public class NetServer {

    public static void postKeepRun(Context context, KeepRun keepRun,NetI netI){
        BaseReqBean baseReqBean = new BaseReqBean();
        baseReqBean.setData(GsonUtil.getInstance().toJson(keepRun));
        NetGet.postData(context, "http://222.186.36.75:8888/record/keeprun/insert",baseReqBean ,netI );
    }
}
