package com.example.darkness.KeepUrFund.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;


import com.example.darkness.KeepUrFund.R;
import com.example.darkness.KeepUrFund.utils.ActivityManagerUtil;
import com.example.darkness.KeepUrFund.utils.DBServiceUtils;
import com.example.darkness.KeepUrFund.utils.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends FragmentActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;
    public static DBServiceUtils dbServiceUtils;
    public static String Qnydate="";
    //存放item映射的图片
    public static Map<String,Integer> picMap=new HashMap<>();
    //存放消费种类映射的图片
    public static Map<String,Integer> codeMap=new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbServiceUtils=new DBServiceUtils(this);
        setContentView(getLayout());
        ActivityManagerUtil.mActivities.add(this);
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initEventAndData();
        setStatusBar();
        createPic();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        ActivityManagerUtil.mActivities.remove(this);
    }
    protected void createPic(){
        String s="捐赠";
        int resource=R.mipmap.sort_juanzeng;
        picMap.put(s,resource);
        codeMap.put(s,315);
        //支出：315捐赠 316零食   317孩子  318长辈  319 礼物
        //320学习 321水果 322美容 323维修  324旅行  325交通
        s="零食";
        resource=R.mipmap.sort_lingshi;
        picMap.put(s,resource);
        codeMap.put(s,316);
        s="孩子";
        resource=R.mipmap.sort_haizi;
        picMap.put(s,resource);
        codeMap.put(s,317);
        s="长辈";
        resource=R.mipmap.sort_zhangbei;
        picMap.put(s,resource);
        codeMap.put(s,318);
        s="礼物";
        resource=R.mipmap.sort_liwu;
        picMap.put(s,resource);
        codeMap.put(s,319);
        s="学习";
        resource=R.mipmap.sort_xuexi;
        picMap.put(s,resource);
        codeMap.put(s,320);
        s="水果";
        resource=R.mipmap.sort_shuiguo;
        picMap.put(s,resource);
        codeMap.put(s,321);
        s="美容";
        resource=R.mipmap.sort_meirong;
        picMap.put(s,resource);
        codeMap.put(s,322);
        s="维修";
        resource=R.mipmap.sort_weixiu;
        picMap.put(s,resource);
        codeMap.put(s,323);
        s="旅行";
        resource=R.mipmap.sort_lvxing;
        picMap.put(s,resource);
        codeMap.put(s,324);
        s="交通";
        resource=R.mipmap.sort_jiaotong;
        picMap.put(s,resource);
        codeMap.put(s,325);
        // 收入 328礼金 329加息 333利息 334返现 335兼职                   其他
        s="礼金";
        resource=R.mipmap.sort_lijin;
        picMap.put(s,resource);
        codeMap.put(s,328);
        s="加息";
        resource=R.mipmap.sort_jiaxi;
        picMap.put(s,resource);
        codeMap.put(s,329);
        s="利息";
        resource=R.mipmap.sort_lixi;
        picMap.put(s,resource);
        codeMap.put(s,333);
        s="返现";
        resource=R.mipmap.sort_fanxian;
        picMap.put(s,resource);
        codeMap.put(s,334);
        s="兼职";
        resource=R.mipmap.sort_jianzhi;
        picMap.put(s,resource);
        codeMap.put(s,335);

        s="现金";
        resource=R.mipmap.card_cash;
        picMap.put(s,resource);
        s="支付宝";
        resource=R.mipmap.card_zhifubao;
        picMap.put(s,resource);
        s="微信";
        resource=R.mipmap.card_weixin;
        picMap.put(s,resource);
    }
    protected abstract int getLayout();
    protected abstract void initEventAndData();
}
