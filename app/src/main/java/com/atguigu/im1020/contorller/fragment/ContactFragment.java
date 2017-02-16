package com.atguigu.im1020.contorller.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atguigu.im1020.R;
import com.atguigu.im1020.contorller.activity.AddContactActivity;
import com.atguigu.im1020.contorller.activity.InviteActivity;
import com.atguigu.im1020.utils.Contacts;
import com.atguigu.im1020.utils.ShowToast;
import com.atguigu.im1020.utils.SpUtils;
import com.hyphenate.easeui.ui.EaseContactListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张永卫on 2017/2/14.
 */
public class ContactFragment extends EaseContactListFragment {

    @InjectView(R.id.contanct_iv_invite)
    ImageView redDot;
    @InjectView(R.id.ll_new_friends)
    LinearLayout newFriends;
    @InjectView(R.id.ll_groups)
    LinearLayout llGroups;

    private BroadcastReceiver notifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            changeDot();
        }
    };
    private LocalBroadcastManager lm;

    @Override
    protected void initView() {
        super.initView();

        //初始化头布局
        View view = View.inflate(getActivity(),
                R.layout.fragment_contact_list_head, null);

        ButterKnife.inject(this,view);
        //添加头布局
        listView.addHeaderView(view);
        //添加actionbar右侧的加号
        titleBar.setRightImageResource(R.mipmap.em_add);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),
                        InviteActivity.class);

                startActivity(intent);
            }
        });

        newFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一件事 处理小红点
                SpUtils.getInstace().save(SpUtils.NEW_INVITE,false);
                changeDot();
                //跳转
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });

        changeDot();
        //获取监听 邀请信息变化
        lm = LocalBroadcastManager.getInstance(getActivity());
        lm.registerReceiver(notifyReceiver,new IntentFilter(Contacts.NEW_INVITE_CHANGED));
    }

    /**
     * 解注册
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        lm.unregisterReceiver(notifyReceiver);
    }

    private void changeDot() {
        boolean isRedShow = SpUtils.getInstace().getBoolean(SpUtils.NEW_INVITE, false);
        redDot.setVisibility(isRedShow ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void setUpView() {
        super.setUpView();


    }


    @OnClick({R.id.ll_new_friends, R.id.ll_groups})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_new_friends:
                ShowToast.show(getActivity(),"aaa");
                break;
            case R.id.ll_groups:
                ShowToast.show(getActivity(),"bbb");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
