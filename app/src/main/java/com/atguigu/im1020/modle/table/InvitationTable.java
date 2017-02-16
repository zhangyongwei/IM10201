package com.atguigu.im1020.modle.table;

/**
 * Created by 张永卫on 2017/2/14.
 */

public class InvitationTable {

    public static final String TABLE_NAME = "invitation";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_USER_HXID = "user_hxid";

    public static final String COL_GROUP_NAME = "group_name";

    public static final String COL_GROUP_HXID = "group_hxid";

    public static final String COL_REASON = "reason";

    public static final String COL_STATUS = "status";

    public static final String CREATE_TABLE = "create table "+TABLE_NAME +"("
            +COL_USER_HXID + " text primary key,"
            +COL_USER_NAME +" text,"
            +COL_GROUP_NAME +" text,"
            +COL_GROUP_HXID +" text,"
            +COL_REASON +" text,"
            +COL_STATUS +" integer);";
}
