package com.atguigu.im1020.contorller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.im1020.R;
import com.atguigu.im1020.contorller.fragment.ContactFragment;
import com.atguigu.im1020.contorller.fragment.ConversitionFragment;
import com.atguigu.im1020.contorller.fragment.SettingFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_fl)
    FrameLayout mainFl;
    @InjectView(R.id.rg_main)
    RadioGroup rgMain;
    private Fragment conversitionFragment;
    private Fragment contactListFragment;
    private Fragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initData();
        initListener();

    }



    private void initData() {
        conversitionFragment = new ConversitionFragment();
        contactListFragment = new ContactFragment();
        settingFragment = new SettingFragment();
        switchFragment(R.id.rb_main_chat);
    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //切换Fragment
                switchFragment(checkedId);
            }
        });
    }

    /**
     * fragment页面切换
     * @param checkedId
     */
    private void switchFragment(int checkedId) {

        Fragment fragment = null;
        switch (checkedId){

            case R.id.rb_main_chat:
                fragment = conversitionFragment;
                break;
            case R.id.rb_main_contact:
                fragment = contactListFragment;
                break;
            case R.id.rb_main_setting:
                fragment = settingFragment;
                break;
        }
        if(fragment==null) {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fl,fragment)
                .commit();
    }
}
