package com.atguigu.im1020.modle.table;

/**
 * Created by 张永卫on 2017/2/14.
 */

public class AccountTable {

    public static  final String TABLE_NAME = "userinfo";

    public static  final String COL_USER_NAME = "username";
    public static  final String COL_USER_HXID = "hxid";
    public static  final String COL_USER_NICK = "nick";
    public static  final String COL_USER_PHOTO = "photo";

    public static final String CREATE_TABLE = "create table "+TABLE_NAME+"("
            + COL_USER_HXID + " text primary key,"
            + COL_USER_NAME + " text,"
            + COL_USER_NICK + " text,"
            + COL_USER_PHOTO + "text); ";
}
