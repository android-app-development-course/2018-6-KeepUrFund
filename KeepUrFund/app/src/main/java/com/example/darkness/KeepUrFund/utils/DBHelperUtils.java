package com.example.darkness.KeepUrFund.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperUtils extends SQLiteOpenHelper{
        private static String DATABASE_NAME="funddb.db";
        private static int DATABASE_VERSION=1;
        public DBHelperUtils(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists funddb(" +
                    "id integer primary key autoincrement," +       //主键
                    "money text not null," +                         //金额
                    "moneytype text not null,"+                            //收入/支出：income/outcome
                    "paytype text not null," +                          //类别：住房、娱乐
                    "payway text not null,"+                            //付款方式：现金、银行卡、微信等
                    "comments text not null," +                     //备注
                    "nydate text not null,"+                        //年月
                    "date text not null" +                          //日
                    ")" );
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

}
