package com.atguigu.im1020.contorller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.contorller.adapter.InviteAdapter;
import com.atguigu.im1020.modle.Modle;
import com.atguigu.im1020.modle.bean.InvitationInfo;
import com.atguigu.im1020.utils.ShowToast;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddContactActivity extends AppCompatActivity {

    @InjectView(R.id.lv_invite)
    ListView lvInvite;
    private InviteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.inject(this);

        initData();

        initListener();

    }

    private void initListener() {

    }

    private void initData() {
        adapter = new InviteAdapter(this, onInviteItemClickListener);
        lvInvite.setAdapter(adapter);
        //获取数据
        refresh();
    }

    private InviteAdapter.OnInviteItemClickListener onInviteItemClickListener = new InviteAdapter.OnInviteItemClickListener() {
        @Override
        public void accpt(final InvitationInfo invitationInfo) {

            //接受

            //告诉环信服务器
            Modle.getInstance().getGlobalThread().execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        //网络
                        EMClient.getInstance().contactManager()
                                .acceptInvitation(invitationInfo.getUserInfo().getHxid());


                        //改状态
                        Modle.getInstance().getDbManager()
                                .getInvitationDao()
                                .updateInvitationStatus(
                                        InvitationInfo.InvitationStatus.INVITE_ACCEPT,
                                        invitationInfo.getUserInfo().getHxid());
                        //刷新内存和页面
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ShowToast.show(AddContactActivity.this,"接受成功");
                            }
                        });

                    } catch (HyphenateException e) {
                        ShowToast.show(AddContactActivity.this,"接受失败"+e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public void reject(InvitationInfo invitationInfo) {
            //拒绝

        }
    };


    private void refresh() {
        //从数据库获取邀请人信息
        List<InvitationInfo> invitations = Modle.getInstance().getDbManager()
                .getInvitationDao().getInvitations();
        
        if(invitations==null || invitations.size()>=0) {
            //刷新数据
            adapter.refresh(invitations);
        }
    }
}
