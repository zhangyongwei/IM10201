package com.atguigu.im1020.modle.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.atguigu.im1020.modle.bean.UserInfo;
import com.atguigu.im1020.modle.db.DBHelper;
import com.atguigu.im1020.modle.table.ContactTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张永卫on 2017/2/14.
 */

public class ContactDao {
    /**
     * 因为联系人表和邀请信息表是在一个DB里所以传dbhelper
     */
    private DBHelper dbHelper;
    public ContactDao(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    // 获取所有联系人
    public List<UserInfo> getContacts() {
        List<UserInfo> userInfos = new ArrayList<>();

        // 获取数据库链接
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 查询联系人
        String sql = "select * from " + ContactTable.TABLE_NAME + " where " + ContactTable.COL_IS_CONTACT + "=1";
        Cursor cursor = db.rawQuery(sql, null);
        //数据封装
        while (cursor.moveToNext()) {
            UserInfo userInfo = new UserInfo();

            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));
            userInfo.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));

            userInfos.add(userInfo);
        }

        // 关闭cursor
        cursor.close();

        // 返回
        return userInfos;
    }

    // 通过环信id获取用户联系人信息
    public List<UserInfo> getContactsByHx(List<String> hxIds) {
        // 校验
        if (hxIds == null || hxIds.size()==0) {
            return null;
        }


        //封装数据
        List<UserInfo> userInfos = new ArrayList<>();
        for (String hxid : hxIds) {

            UserInfo userInfo = getContactByHx(hxid);
            if(userInfo!=null) {
             userInfos.add(userInfo);
             }
        }

        // 返回
        return userInfos;
    }

    // 通过环信id获取联系人单个信息
    public UserInfo getContactByHx(String hxId) {
        // 校验
        if (hxId == null) {
            return null;
        }

        // 获取数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 查询
        String sql = "select * from " + ContactTable.TABLE_NAME + " where " + ContactTable.COL_USER_HXID + " =?";
        Cursor cursor = db.rawQuery(sql, new String[]{hxId});

        UserInfo contact = null;

        if (cursor.moveToNext()) {
            contact = new UserInfo();

            contact.setHxid(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_HXID)));
            contact.setUsername(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NAME)));
            contact.setNick(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_NICK)));
            contact.setPhoto(cursor.getString(cursor.getColumnIndex(ContactTable.COL_USER_PHOTO)));

        }

        cursor.close();

        return contact;
    }

    // 保存单个联系人
    public void saveContact(UserInfo user, boolean isMyContact) {
        // 获取数据库链接
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 执行添加语句
        ContentValues values = new ContentValues();
        values.put(ContactTable.COL_USER_HXID, user.getHxid());
        values.put(ContactTable.COL_USER_NAME, user.getUsername());
        values.put(ContactTable.COL_USER_NICK, user.getNick());
        values.put(ContactTable.COL_USER_PHOTO, user.getPhoto());
        values.put(ContactTable.COL_IS_CONTACT, isMyContact ? 1 : 0);

        db.replace(ContactTable.TABLE_NAME, null, values);
    }


    // 保存联系人信息
    public void saveContacts(List<UserInfo> contacts, boolean isMyContact) {
        // 校验
        if (contacts == null || contacts.size() <= 0) {
            return;
        }

        for (UserInfo contact : contacts) {
            saveContact(contact, isMyContact);
        }
    }

    // 删除联系人信息
    public void deleteContactByHxId(String hxId) {
        // 校验
        if (hxId == null) {
            return;
        }

        // 获取数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 删除操作
        db.delete(ContactTable.TABLE_NAME, ContactTable.COL_USER_HXID + "=?", new String[]{hxId});
    }
}
