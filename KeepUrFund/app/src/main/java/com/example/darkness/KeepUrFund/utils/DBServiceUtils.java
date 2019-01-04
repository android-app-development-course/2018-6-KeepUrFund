package com.example.darkness.KeepUrFund.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.darkness.KeepUrFund.bean.AccountBean;
import com.example.darkness.KeepUrFund.bean.DataBean;
import com.example.darkness.KeepUrFund.bean.DetailBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBServiceUtils {
    private DBHelperUtils dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    public DBServiceUtils(Context context) {
        dbHelper = new DBHelperUtils(context);
    }
    /**
     * 保存
     */
    public void save(DataBean dataBean) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("money",dataBean.getdMoney());
        values.put("moneytype",dataBean.getdMoneytype());
        values.put("paytype",dataBean.getdPaytype());
        values.put("payway",dataBean.getdPayway());
        values.put("comments",dataBean.getdComments());
        values.put("nydate",dataBean.getdnyDate());
        values.put("date",dataBean.getdDate());
        sqLiteDatabase.insert("funddb",null,values);
        sqLiteDatabase.close();
    }
    // 列出所有
    public List<DataBean> getAllnyDataBean(String nydate1) {
        DataBean dataBean = null;
        List<DataBean> dataBeanList = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("funddb",null,"nydate=?"
                ,new String[]{nydate1},null,null,null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String money = cursor.getString(cursor.getColumnIndex("money"));
            String moneytype = cursor.getString(cursor.getColumnIndex("moneytype"));
            String paytype = cursor.getString(cursor.getColumnIndex("paytype"));
            String payway = cursor.getString(cursor.getColumnIndex("payway"));
            String comments = cursor.getString(cursor.getColumnIndex("comments"));
            String nydate = cursor.getString(cursor.getColumnIndex("nydate"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            dataBean = new DataBean(id, money,moneytype,paytype,payway,comments,nydate,date);
            dataBeanList.add(dataBean);
        }
        cursor.close();
        sqLiteDatabase.close();
        return dataBeanList;
    }
    public List<DataBean> getAlltypeDataBean(String pt,String nyd,String mt) {
        DataBean dataBean = null;
        List<DataBean> dataBeanList = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("funddb",null,"paytype=? and nydate=? and moneytype=?"
                ,new String[]{pt,nyd,mt},null,null,null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String money = cursor.getString(cursor.getColumnIndex("money"));
            String moneytype = cursor.getString(cursor.getColumnIndex("moneytype"));
            String paytype = cursor.getString(cursor.getColumnIndex("paytype"));
            String payway = cursor.getString(cursor.getColumnIndex("payway"));
            String comments = cursor.getString(cursor.getColumnIndex("comments"));
            String nydate = cursor.getString(cursor.getColumnIndex("nydate"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            dataBean = new DataBean(id, money,moneytype,paytype,payway,comments,nydate,date);
            dataBeanList.add(dataBean);
        }
        cursor.close();
        sqLiteDatabase.close();
        return dataBeanList;
    }
    //
    //列出所有payway
    public AccountBean getAllpaywayBean(String nydate1){
        AccountBean accountbean=new AccountBean();
        List<AccountBean.ListBean> accountBeanList = new ArrayList<>();
        int index=0;
        Map<String,Integer> map=new HashMap<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("funddb",null,"nydate=?"
                ,new String[]{nydate1},null,null,null);
        while(cursor.moveToNext()){
            AccountBean.ListBean temp=new AccountBean.ListBean();
            String payway=cursor.getString(cursor.getColumnIndex("payway"));

            String moneytype=cursor.getString(cursor.getColumnIndex("moneytype"));
            String money=cursor.getString(cursor.getColumnIndex("money"));
            temp.setImg(payway);
            if(map.containsKey(payway)){
                temp=accountBeanList.get(map.get(payway));
                if(moneytype.equals("收入"))  temp.setIncome(String.valueOf(Double.valueOf(money)+Double.valueOf(temp.getIncome())));
                else   temp.setOutcome(String.valueOf(Double.valueOf(money)+Double.valueOf(temp.getOutcome())));
                accountBeanList.set(map.get(payway),temp);
            }else{
                map.put(payway,index);
                index++;
                temp.setName(payway);
                if(moneytype.equals("收入"))  temp.setIncome(String.valueOf(Double.valueOf(money)));
                else  temp.setOutcome(String.valueOf(Double.valueOf(money)));
                // temp.set
                accountBeanList.add(temp);
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        for(int i=0;i<accountBeanList.size();i++){
            AccountBean.ListBean temp=new AccountBean.ListBean();
            temp=accountBeanList.get(i);
            accountbean.setTotal_in(String.valueOf(Double.valueOf(accountbean.getTotal_in())+ Double.valueOf(temp.getIncome()))  );
            accountbean.setTotal_out(String.valueOf(Double.valueOf(accountbean.getTotal_out())+ Double.valueOf(temp.getOutcome()))  );
            if(!temp.getIncome().equals("0"))
                temp.setIncome("+"+temp.getIncome());
            if(!temp.getOutcome().equals("0"))
                temp.setOutcome("-"+temp.getOutcome());
            accountBeanList.set(i,temp);
        }
        accountbean.setList(accountBeanList);

        return accountbean;
    }

    /**
     * 根据id删除
     */
    public void delete(int id) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete("funddb","id=?",new String[]{id+""});
        sqLiteDatabase.close();
    }

    public DataBean search(int id1) {
        DataBean dataBean = null;
        sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("funddb",null,"id=?"
                ,new String[]{id1+""},null,null,null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String money = cursor.getString(cursor.getColumnIndex("money"));
            String moneytype = cursor.getString(cursor.getColumnIndex("moneytype"));
            String paytype = cursor.getString(cursor.getColumnIndex("paytype"));
            String payway = cursor.getString(cursor.getColumnIndex("payway"));
            String comments = cursor.getString(cursor.getColumnIndex("comments"));
            String nydate = cursor.getString(cursor.getColumnIndex("nydate"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            dataBean = new DataBean(id, money,moneytype,paytype,payway,comments,nydate,date);
        }
        cursor.close();
        sqLiteDatabase.close();
        return dataBean;
    }



//    public void deleteAll(){
//        sqLiteDatabase = dbHelper.getWritableDatabase();
//        sqLiteDatabase.delete("diarytable",null,null);
//        sqLiteDatabase.close();
//    }
//    /**
//     * 通过id更新
//     */
//    public void update(Diary diary) {
//        sqLiteDatabase = dbHelper.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("title",diary.getTitle());
//        values.put("content",diary.getContent());
//        values.put("pubdate",diary.getPubdate());
//        sqLiteDatabase.update("diarytable",values,"id=?",new String[]{diary.getId()+""});
//        sqLiteDatabase.close();
//    }
}
