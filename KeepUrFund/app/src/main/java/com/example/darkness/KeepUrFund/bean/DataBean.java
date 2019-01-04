package com.example.darkness.KeepUrFund.bean;

public class DataBean {
    private int id;
    private String money;
    private String moneytype;
    private String paytype;
    private String payway;
    private String comments;
    private String nydate;
    private String date;
    public int getdId() {
        return id;
    }
    public void setdId(int id) {
        this.id = id;
    }

    public String getdMoney() {
        return money;
    }
    public void setdMoney(String money) {
        this.money = money;
    }

    public String getdMoneytype(){return moneytype;}
    public void setdMoneytype(String moneytype) {
        this.moneytype = moneytype;
    }

    public String getdPaytype(){return paytype;}
    public void setdPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getdPayway(){return payway;}
    public void setdPayway(String payway) {
        this.payway = payway;
    }

    public String getdComments(){return comments;}
    public void setdComments(String comments) {
        this.comments = comments;
    }
    public String getdnyDate(){return nydate;}
    public void setdnyDate(String nydate){this.nydate=nydate;}
    public String getdDate() {
        return date;
    }
    public void setdDate(String date) {
        this.date = date;
    }

    public DataBean(int id, String money,String moneytype,String paytype,String payway,String comments,String nydate,String date) {
        super();
        this.id=id;
        this.money = money;
        this.moneytype = moneytype;
        this.paytype = paytype;
        this.payway=payway;
        this.comments=comments;
        this.nydate=nydate;
        this.date=date;
    }
    public DataBean(String money,String moneytype,String paytype,String payway,String comments,String nydate,String date) {
        super();
        this.money = money;
        this.moneytype = moneytype;
        this.paytype = paytype;
        this.payway=payway;
        this.comments=comments;
        this.nydate=nydate;
        this.date=date;
    }
}
