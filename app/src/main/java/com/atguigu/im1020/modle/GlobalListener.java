package com.atguigu.im1020.modle;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.atguigu.im1020.modle.bean.InvitationInfo;
import com.atguigu.im1020.modle.bean.UserInfo;
import com.atguigu.im1020.utils.Contacts;
import com.atguigu.im1020.utils.SpUtils;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

/**
 * Created by 张永卫on 2017/2/15.
 */

public class GlobalListener {


    private final LocalBroadcastManager lm;

    public GlobalListener(Context context) {

        EMClient.getInstance().contactManager().setContactListener(listener);

        //本地广播
        lm = LocalBroadcastManager.getInstance(context);
        //发送广播
        lm.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHANGED));

    }
    EMContactListener listener = new EMContactListener() {


        //收到好友邀请 别人加你
        @Override
        public void onContactInvited(String username, String reson) {

            //添加邀请消息
            InvitationInfo invitationInfo = new InvitationInfo();
            invitationInfo.setUserInfo(new UserInfo(username));
            invitationInfo.setReason(reson);
            invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);
            Modle.getInstance().getDbManager().getInvitationDao().addInvitation(invitationInfo);
            //显示小红点
            SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
            //发送广播
            lm.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHANGED));

        }
        //好友请求被同意，你加被人的时候 别人同意了
        @Override
        public void onContactAgreed(String username) {

//            Modle.getInstance().getDbManager().getContactDao().saveContact(new UserInfo(username),true);
            InvitationInfo invitation = new InvitationInfo();
            invitation.setReason("加为好友");
            invitation.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);
            invitation.setUserInfo(new UserInfo(username));
            Modle.getInstance().getDbManager().getInvitationDao().addInvitation(invitation);
            //显示小红点
            SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
            //发送广播
            lm.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHANGED));

        }
        //被删除时回调此方法
        @Override
        public void onContactDeleted(String username) {
            //删除联系人
            Modle.getInstance().getDbManager().getContactDao().deleteContactByHxId(username);
            //删除邀请信息
            Modle.getInstance().getDbManager().getInvitationDao().removeInvitation(username);

            //发送广播
            lm.sendBroadcast(new Intent(Contacts.CONTACT_CHANGED));

        }
        //增加了联系人时回调此方法 当你同意添加好友
        @Override
        public void onContactAdded(String username) {

            //添加联系人
            Modle.getInstance().getDbManager().getContactDao().saveContact(new UserInfo(username),true);
            //发送广播
            lm.sendBroadcast(new Intent(Contacts.CONTACT_CHANGED));

        }
        //好友请求数据被拒绝 你加别人 别人拒绝了
        @Override
        public void onContactRefused(String username) {

            //显示小红点
            SpUtils.getInstace().save(SpUtils.NEW_INVITE,true);
            //发送广播
            lm.sendBroadcast(new Intent(Contacts.NEW_INVITE_CHANGED));
        }
    };
}

