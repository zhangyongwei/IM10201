package com.atguigu.im1020.modle.db;

import android.content.Context;

import com.atguigu.im1020.modle.dao.ContactDao;
import com.atguigu.im1020.modle.dao.InvitationDao;

/**
 * Created by 张永卫on 2017/2/15.
 */
public class DBManager {

    private final ContactDao contactDao;
    private final InvitationDao invitationDao;
    private final DBHelper dbHelper;

    public DBManager(Context context,String name) {

        dbHelper = new DBHelper(context,name);

        //创建联系人操作类
        contactDao = new ContactDao(dbHelper);

        //创建邀请信息操作类
        invitationDao = new InvitationDao(dbHelper);
    }

    public ContactDao getContactDao(){
        return contactDao;
    }

    public InvitationDao getInvitationDao(){
        return invitationDao;

    }

    public void close(){
        dbHelper.close();
    }
}
