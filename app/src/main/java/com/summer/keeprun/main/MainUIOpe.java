package com.summer.keeprun.main;

import com.android.lib.base.ope.BaseUIOpe;
import com.summer.keeprun.databinding.ActMainBinding;

public class MainUIOpe extends BaseUIOpe<ActMainBinding> {


    public void setText(String str){
        getBind().text.setText(str);
    }
}
