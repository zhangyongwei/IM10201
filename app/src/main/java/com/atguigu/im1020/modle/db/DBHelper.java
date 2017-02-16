package com.atguigu.im1020.modle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atguigu.im1020.modle.table.ContactTable;
import com.atguigu.im1020.modle.table.InvitationTable;

/**
 * Created by 张永卫on 2017/2/14.
 */

public class DBHelper  extends SQLiteOpenHelper{
    //
    public DBHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建联系人的表
        db.execSQL(ContactTable.CREATE_TABLE);
        //创建邀请信息的表
        db.execSQL(InvitationTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
