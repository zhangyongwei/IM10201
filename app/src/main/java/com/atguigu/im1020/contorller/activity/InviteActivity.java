package com.atguigu.im1020.contorller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.im1020.R;
import com.atguigu.im1020.modle.Modle;
import com.atguigu.im1020.utils.ShowToast;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 张永卫on 2017/2/14.
 */
public class InviteActivity extends AppCompatActivity {

    @InjectView(R.id.invite_btn_save)
    Button inviteBtnSave;
    @InjectView(R.id.invite_et_search)
    EditText inviteEtSearch;
    @InjectView(R.id.invite_tv_username)
    TextView inviteTvUsername;
    @InjectView(R.id.invite_btn_add)
    Button inviteBtnAdd;
    @InjectView(R.id.invite_ll_item)
    LinearLayout inviteLlItem;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_activity);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.invite_btn_save, R.id.invite_btn_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invite_btn_save:
                //搜索
                //验证
                if(verify()) {
                    //显示搜索结果
                    inviteLlItem.setVisibility(View.VISIBLE);
                    inviteTvUsername.setText(username);
                }else{
                    inviteLlItem.setVisibility(View.GONE);
                }
                break;
            case R.id.invite_btn_add:
                //添加
                Modle.getInstance().getGlobalThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        //去环信服务器添加好友
                        //参数为要添加好友的username和添加理由
                        try {
                            EMClient.getInstance().contactManager()
                                    .addContact(username,"添加好友");
                            ShowToast.showUI(InviteActivity.this,"添加成功成功");
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            ShowToast.showUI(InviteActivity.this,"添加好友失败"+e.getMessage());
                        }
                    }
                });
                break;
        }
    }

    private boolean verify() {
        username = inviteEtSearch.getText().toString().trim();

        if(TextUtils.isEmpty(username)) {
            ShowToast.show(this,"输入用户名不能为空！");
            return false;
        }
        //服务器验证
        return true;
    }
}
