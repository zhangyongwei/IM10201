package com.atguigu.im1020;

import android.app.Application;
import android.content.Context;

import com.atguigu.im1020.modle.Modle;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

public class ImApplication extends Application {

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        //初始化环信
        initHXSdk();
        //初始化Modle
        Modle.getInstance().init(this);
    }

    private void initHXSdk() {
        //配置文件
        EMOptions options = new EMOptions();
        //总是接收邀请
        options.setAcceptInvitationAlways(false);//不总是接收所有邀请
        //自动接受群邀请
        options.setAutoAcceptGroupInvitation(false);//不自动接收群邀请信息
        //初始化EaseUI
        EaseUI.getInstance().init(this, options);
    }

    public static Context getContext(){
        return context;
    }
}
