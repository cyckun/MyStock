package com.example.rui.mystock.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlHelper extends SQLiteOpenHelper {

        public static final String TAG = "MYSQLITEHELPER";
        public static final int CUR_VERSION = 8;

        public static final String CREATE_STOCK = "create table t_stock (" +

                "number varchar(20) primary key, name varchar(20), value varchar(10),character varchar(20),isSelected integer," +
                "topTime integer)";
        public static final String CREATE_EXCHANGE_STOCK = "create table t_exe_stock (" +

            "exe_id varchar(20) primary key,acc_id integer,number varchar(10),exe_value decimal(10,2), exe_mount integer,exe_time integer,exe_type integer)";

        public static final String CREATE_ACCOUNT_STOCK = "create table t_acc_stock (" +

            "acc_id integer primary key autoincrement,name varchar(10),phone varchar(11),password varchar(32),init_value decimal(10,2),cur_value decimal(10,2),cur_stock_value decimal(10,2))";

        public MySqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,

                              int version) {

            super(context, name, factory, version);

        }

        @Override

        public void onOpen(SQLiteDatabase db) {

            Log.i(TAG,"open db");

            super.onOpen(db);

        }


    @Override
    public synchronized void close() {
        super.close();
        Log.i(TAG,"close db");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(TAG,"create db");

        Log.i(TAG,"before excSql");

        db.execSQL(CREATE_STOCK);

        Log.i(TAG,"after excSql");
    }

        @Override

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.i(TAG, "onUpgrade oldVersion:" + oldVersion + " newVersion:" + newVersion);
            switch (newVersion) {
                case 1:
                    db.execSQL(CREATE_STOCK);
                    break;
                case 2:
                    db.execSQL("alter table t_stock add column character varchar(20)");
                    break;
                case 3:
                    db.execSQL("alter table t_stock add column isSelected integer");
                    break;
                case 4:
                    db.execSQL("alter table t_stock add column topTime integer");
                    break;
                case CUR_VERSION:
                    db.execSQL("drop table if exists t_acc_stock");
                    db.execSQL("drop table if exists t_exe_stock");
                    db.execSQL(CREATE_EXCHANGE_STOCK);
                    db.execSQL(CREATE_ACCOUNT_STOCK);
                    break;
                default:
                    break;
            }
        }
}
