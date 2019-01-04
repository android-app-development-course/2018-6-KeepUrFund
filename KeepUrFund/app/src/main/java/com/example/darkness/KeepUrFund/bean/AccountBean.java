package com.example.darkness.KeepUrFund.bean;

import java.io.Serializable;
import java.util.List;

/**
 */

public class AccountBean extends BaseBean{



    private String total_in="0.0";
    private String total_out="0.0";
    private List<ListBean> list;

    public String getTotal_in() {
        return total_in;
    }

    public void setTotal_in(String total_in) {
        this.total_in = total_in;
    }

    public String getTotal_out() {
        return total_out;
    }

    public void setTotal_out(String total_out) {
        this.total_out = total_out;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{

        private String type;
        private String img;
        private String name;
        private String num;
        private String income="0";
        private String outcome="0";

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getIncome() {
            return income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

        public String getOutcome() {
            return outcome;
        }

        public void setOutcome(String outcome) {
            this.outcome = outcome;
        }
    }
}
