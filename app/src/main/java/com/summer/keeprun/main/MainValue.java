package com.summer.keeprun.main;

import com.android.lib.base.ope.BaseValue;
import com.android.lib.constant.ValueConstant;
import com.android.lib.util.GsonUtil;
import com.android.lib.util.SPUtil;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainValue extends BaseValue {


    private long starttime = 0;
}
