package com.atguigu.im1020.contorller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.atguigu.im1020.R;
import com.atguigu.im1020.contorller.activity.LoginActivity;
import com.atguigu.im1020.modle.Modle;
import com.atguigu.im1020.utils.ShowToast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by 张永卫on 2017/2/14.
 */
public class SettingFragment extends Fragment {

    @InjectView(R.id.settings_btn_logout)
    Button settingsBtnLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        ButterKnife.inject(this, view);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Modle.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                String currentUser = EMClient.getInstance().getCurrentUser();

                settingsBtnLogout.setText("退出登录("+currentUser+")");
            }
        });
    }

    @OnClick(R.id.settings_btn_logout)
    public void onClick() {

        Modle.getInstance().getGlobalThread().execute(new Runnable() {
            @Override
            public void run() {
                //去环信服务器退出登录
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {

                        //数据库关闭

                        //跳转，结束
                        if(getActivity()==null) {
                            return;
                        }
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        ShowToast.showUI(getActivity(),"退出登录成功");
                        getActivity().finish();
                    }

                    @Override
                    public void onError(int i, String s) {
                        ShowToast.showUI(getActivity(),"退出登录失败"+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
