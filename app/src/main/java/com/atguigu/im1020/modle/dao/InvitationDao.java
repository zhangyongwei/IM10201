package com.atguigu.im1020.modle.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.atguigu.im1020.modle.bean.GroupInfo;
import com.atguigu.im1020.modle.bean.InvitationInfo;
import com.atguigu.im1020.modle.bean.UserInfo;
import com.atguigu.im1020.modle.db.DBHelper;
import com.atguigu.im1020.modle.table.InvitationTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张永卫on 2017/2/14.
 */

public class InvitationDao {

    /**
     * 因为联系人表和邀请信息表是在一个DB里所以传dbhelper
     */
    private DBHelper dbHelper;
    public InvitationDao(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    // 添加邀请
    public void addInvitation(InvitationInfo invitationInfo){

        //校验
        if(invitationInfo==null) {
            return;
        }

        //获取数据库连接
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //数据库操作
        ContentValues contentValues = new ContentValues();
        contentValues.put(InvitationTable.COL_REASON,invitationInfo.getReason());
        contentValues.put(InvitationTable.COL_STATUS,invitationInfo.getStatus().ordinal());
        if(invitationInfo.getUserInfo()==null) {

            //群邀请
            contentValues.put(InvitationTable.COL_GROUP_HXID,invitationInfo.getGroupInfo().getGroupId());
            contentValues.put(InvitationTable.COL_GROUP_NAME,invitationInfo.getGroupInfo().getGroupName());
            contentValues.put(InvitationTable.COL_USER_HXID,invitationInfo.getGroupInfo().getInvitePerson());

        }else{

            //联系人邀请
            contentValues.put(InvitationTable.COL_USER_HXID,invitationInfo.getUserInfo().getHxid());
            contentValues.put(InvitationTable.COL_USER_NAME,invitationInfo.getUserInfo().getUsername());
        }
        contentValues.put(InvitationTable.COL_REASON,invitationInfo.getReason());
        contentValues.put(InvitationTable.COL_STATUS,invitationInfo.getStatus().ordinal());
        db.replace(InvitationTable.TABLE_NAME,null,contentValues);
    }

    // 获取所有邀请信息
    public List<InvitationInfo> getInvitations(){
        //获取数据库资源
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //数据库操作
        String sql = "select * from "+InvitationTable.TABLE_NAME;

        Cursor cursor = db.rawQuery(sql, null);

        List<InvitationInfo> invitationInfos =  new ArrayList<>();

        while (cursor.moveToNext()){

            InvitationInfo invitation = new InvitationInfo();

            invitation.setReason(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_REASON)));
            invitation.setStatus(int2InviteStatus(cursor.getInt(cursor.getColumnIndex(InvitationTable.COL_STATUS))));

            String groupid = cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_HXID));
            if(groupid==null) {
                //联系人邀请信息
                UserInfo userInfo = new UserInfo();
                userInfo.setHxid(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_HXID)));
                userInfo.setUsername(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_NAME)));

                invitation.setUserInfo(userInfo);
            }else{

                //群邀请
                GroupInfo groupInfo = new GroupInfo();
                groupInfo.setGroupId(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_HXID)));
                groupInfo.setGroupName(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_GROUP_NAME)));
                groupInfo.setInvitePerson(cursor.getString(cursor.getColumnIndex(InvitationTable.COL_USER_HXID)));

                invitation.setGroupInfo(groupInfo);
            }
            invitationInfos.add(invitation);
        }
        //关闭资源
        cursor.close();
        //返回数据
        return invitationInfos;

    }

    // 将int类型状态转换为邀请的状态
    private InvitationInfo.InvitationStatus int2InviteStatus(int intStatus){
        if (intStatus == InvitationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT;
        }

        if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        }

        if (intStatus == InvitationInfo.InvitationStatus.NEW_GROUP_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_GROUP_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_GROUP_APPLICATION;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_INVITE_ACCEPTED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_APPLICATION_ACCEPTED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_INVITE_DECLINED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_INVITE_DECLINED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_APPLICATION_DECLINED;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_ACCEPT_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_ACCEPT_INVITE;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_REJECT_APPLICATION.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_REJECT_APPLICATION;
        }

        if (intStatus == InvitationInfo.InvitationStatus.GROUP_REJECT_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.GROUP_REJECT_INVITE;
        }

        return null;
    }

    // 删除邀请
    public void removeInvitation(String hxId){
        //校验
        if(TextUtils.isEmpty(hxId)) {
            return;
        }
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        database.delete(InvitationTable.TABLE_NAME,
                InvitationTable.COL_USER_HXID+"=?",new String[]{hxId});
    }

    // 更新邀请状态
    public void updateInvitationStatus(InvitationInfo.InvitationStatus invitationStatus, String hxId){

        if(TextUtils.isEmpty(hxId)) {
            return;
        }
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(InvitationTable.COL_STATUS,invitationStatus.ordinal());

        //第一个参数表名，第二个参修改的字段和值 第三个参数条件选择 第四个参数条件选择的值

        database.update(InvitationTable.TABLE_NAME,contentValues,
                InvitationTable.COL_USER_HXID+"=?",new String[]{hxId});
    }
}
