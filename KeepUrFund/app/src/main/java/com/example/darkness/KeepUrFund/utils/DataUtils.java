package com.example.darkness.KeepUrFund.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.darkness.KeepUrFund.activity.BaseActivity;
import com.example.darkness.KeepUrFund.bean.AccountBean;
import com.example.darkness.KeepUrFund.bean.DataBean;
import com.example.darkness.KeepUrFund.bean.DetailBean;
import com.example.darkness.KeepUrFund.bean.NoteBean;
import com.example.darkness.KeepUrFund.bean.TypeBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 */

public class DataUtils {
    public static DetailBean getTallyDetailBean(){
        List<DataBean> dataBeanList=BaseActivity.dbServiceUtils.getAllnyDataBean(BaseActivity.Qnydate);
        //存日
        TreeSet<String> treeSet=new TreeSet<>();
        double income=0.00,outcome=0.00;
        for(int i=0;i<dataBeanList.size();i++) {
            DataBean dataBean=dataBeanList.get(i);
            treeSet.add(dataBean.getdDate());
            if(dataBean.getdMoneytype().equals("收入")){
                income+=Float.valueOf(dataBean.getdMoney());
            }else{
                outcome+=Float.valueOf(dataBean.getdMoney());
            }
        }
        List<DetailBean.DaylistBean> daylist=new ArrayList<>();
        for(String s:treeSet){
            DetailBean.DaylistBean daylistBean=new DetailBean.DaylistBean();
            //存同一天的数据
            List<DetailBean.DaylistBean.ListBean> list=new ArrayList<>();
            daylistBean.setTime(s);
            daylistBean.setMoney("");
            for(int i=0;i<dataBeanList.size();i++){
                DataBean dataBean=dataBeanList.get(i);
                if(dataBean.getdDate().equals(s)){
                    DetailBean.DaylistBean.ListBean listBean = new DetailBean.DaylistBean.ListBean();
                    listBean.setId(String.valueOf(dataBean.getdId()));
                    if(dataBean.getdMoneytype().equals("收入"))
                        listBean.setAffect_money("+"+dataBean.getdMoney());
                    else
                        listBean.setAffect_money("-"+dataBean.getdMoney());
                    listBean.setTypename(dataBean.getdPaytype());
                    listBean.setImg(dataBean.getdPaytype());
                    list.add(listBean);
                }
            }
            daylistBean.setList(list);
            daylist.add(daylistBean);
        }
        DetailBean detailBean=new DetailBean();
        detailBean.setT_income(String.valueOf(income));
        detailBean.setT_outcome(String.valueOf(outcome));
        detailBean.setDaylist(daylist);
        return detailBean;
    }


    //类别报表支出
    public static TypeBean getTallyTypeBeanOut(){
        List<DataBean> dataBeanList=BaseActivity.dbServiceUtils.getAllnyDataBean(BaseActivity.Qnydate);
        TreeSet<String> treeSet=new TreeSet<>();
        double income=0.00,outcome=0.00;
        for(int i=0;i<dataBeanList.size();i++) {
            DataBean dataBean=dataBeanList.get(i);
            if(dataBean.getdMoneytype().equals("收入")){
                income+=Float.valueOf(dataBean.getdMoney());
            }else{
                outcome+=Float.valueOf(dataBean.getdMoney());
                treeSet.add(dataBean.getdPaytype());
            }
        }
        List<TypeBean.TMoneyBean> list=new ArrayList<>();
        for(String s:treeSet)
        {
            TypeBean.TMoneyBean tMoneyBean=new TypeBean.TMoneyBean();
            dataBeanList=BaseActivity.dbServiceUtils.getAlltypeDataBean(s,BaseActivity.Qnydate,"支出");
            List<Float> list1=new ArrayList<>();
            float affect_money=0;
            for(int i=0;i<dataBeanList.size();i++) {
                DataBean dataBean = dataBeanList.get(i);
                list1.add(Float.valueOf(dataBean.getdMoney()));
                affect_money+=Float.valueOf(dataBean.getdMoney());
            }
            Collections.sort(list1);
            Collections.reverse(list1);
            tMoneyBean.setList(list1);
            tMoneyBean.setAffect_money(affect_money);
            tMoneyBean.setBack_color("#ffb073");
            tMoneyBean.setType(BaseActivity.codeMap.get(s));
            tMoneyBean.setTypename(s);
            list.add(tMoneyBean);
        }
        TypeBean typeBean=new TypeBean();
        typeBean.setT_money(list);
        typeBean.setTotal(Float.valueOf(String.valueOf(outcome)));
        typeBean.setSurplus(String.valueOf(outcome));
        typeBean.setScale(String.valueOf(income));
        return typeBean;
    }

    public static TypeBean getTallyTypeBeanIn(){
        List<DataBean> dataBeanList=BaseActivity.dbServiceUtils.getAllnyDataBean(BaseActivity.Qnydate);
        TreeSet<String> treeSet=new TreeSet<>();
        double income=0.00,outcome=0.00;
        for(int i=0;i<dataBeanList.size();i++) {
            DataBean dataBean=dataBeanList.get(i);
            if(dataBean.getdMoneytype().equals("收入")){
                treeSet.add(dataBean.getdPaytype());
                income+=Float.valueOf(dataBean.getdMoney());
            }else{
                outcome+=Float.valueOf(dataBean.getdMoney());
            }
        }
        List<TypeBean.TMoneyBean> list=new ArrayList<>();
        for(String s:treeSet)
        {
            TypeBean.TMoneyBean tMoneyBean=new TypeBean.TMoneyBean();
            dataBeanList=BaseActivity.dbServiceUtils.getAlltypeDataBean(s,BaseActivity.Qnydate,"收入");
            float affect_money=0;
            List<Float> list1=new ArrayList<>();
            for(int i=0;i<dataBeanList.size();i++) {
                DataBean dataBean = dataBeanList.get(i);
                list1.add(Float.valueOf(dataBean.getdMoney()));
                affect_money+=Float.valueOf(dataBean.getdMoney());
            }
            Collections.sort(list1);
            Collections.reverse(list1);
            tMoneyBean.setList(list1);
            tMoneyBean.setAffect_money(affect_money);
            tMoneyBean.setBack_color("#ffb073");
            tMoneyBean.setType(BaseActivity.codeMap.get(s));
            tMoneyBean.setTypename(s);
            list.add(tMoneyBean);
        }
        TypeBean typeBean=new TypeBean();
        typeBean.setT_money(list);
        typeBean.setTotal(Float.valueOf(String.valueOf(income)));
        typeBean.setSurplus(String.valueOf(outcome));
        typeBean.setScale(String.valueOf(income));
        return typeBean;
    }




    //记一笔支出
    public static NoteBean getTallyNoteBeanOut(){
        String str= "{\n" +
                "    \"status\":1,\n" +
                "    \"sortlist\":[\n" +
                "        {\n" +
                "            \"sort_id\":\"315\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"捐赠\",\n" +
                "            \"sort_img\":\"捐赠\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"316\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"零食\",\n" +
                "            \"sort_img\":\"零食\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"317\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"孩子\",\n" +
                "            \"sort_img\":\"孩子\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"318\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"长辈\",\n" +
                "            \"sort_img\":\"长辈\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"319\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"礼物\",\n" +
                "            \"sort_img\":\"礼物\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"320\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"学习\",\n" +
                "            \"sort_img\":\"学习\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"321\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"水果\",\n" +
                "            \"sort_img\":\"水果\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"322\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"美容\",\n" +
                "            \"sort_img\":\"美容\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"323\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"维修\",\n" +
                "            \"sort_img\":\"维修\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"324\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"旅行\",\n" +
                "            \"sort_img\":\"旅行\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"325\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"交通\",\n" +
                "            \"sort_img\":\"交通\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"payinfo\":[\n" +
                "        {\n" +
                "            \"pay_type\":\"1\",\n" +
                "            \"pay_name\":\"现金\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"pay_type\":\"3_28\",\n" +
                "            \"pay_name\":\"支付宝\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"pay_type\":\"3_22\",\n" +
                "            \"pay_name\":\"微信\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        return new Gson().fromJson(str, NoteBean.class);
    }


    //记一笔收入假
    public static NoteBean getTallyNoteBeanIn(){
        String str= "{\n" +
                "    \"status\":1,\n" +
                "    \"sortlist\":[\n" +
                "        {\n" +
                "            \"sort_id\":\"328\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"礼金\",\n" +
                "            \"sort_img\":\"礼金\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"329\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"加息\",\n" +
                "            \"sort_img\":\"加息\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"333\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"利息\",\n" +
                "            \"sort_img\":\"利息\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"334\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"返现\",\n" +
                "            \"sort_img\":\"返现\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"sort_id\":\"335\",\n" +
                "            \"uid\":\"0\",\n" +
                "            \"sort_name\":\"兼职\",\n" +
                "            \"sort_img\":\"兼职\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"payinfo\":[\n" +
                "        {\n" +
                "            \"pay_type\":\"1\",\n" +
                "            \"pay_name\":\"现金\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"pay_type\":\"3_28\",\n" +
                "            \"pay_name\":\"支付宝\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"pay_type\":\"3_22\",\n" +
                "            \"pay_name\":\"微信\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        return new Gson().fromJson(str, NoteBean.class);
    }



    //我的账户假
    public static AccountBean getTallyAccountBean(){
        return BaseActivity.dbServiceUtils.getAllpaywayBean(BaseActivity.Qnydate);
    }
}
