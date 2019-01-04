package com.example.darkness.KeepUrFund.bean;

import java.util.List;

public class NoteBean extends BaseBean{

    private List<SortlistBean> sortlist;
    private List<PayinfoBean> payinfo;


    public List<SortlistBean> getSortlist() {
        return sortlist;
    }

    public void setSortlist(List<SortlistBean> sortlist) {
        this.sortlist = sortlist;
    }

    public List<PayinfoBean> getPayinfo() {
        return payinfo;
    }

    public void setPayinfo(List<PayinfoBean> payinfo) {
        this.payinfo = payinfo;
    }


    public static class SortlistBean {

        private String sort_id;
        private String uid;
        private String sort_name;
        private String sort_img;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean select) {
            this.selected = select;
        }

        public String getSort_id() {
            return sort_id;
        }

        public void setSort_id(String sort_id) {
            this.sort_id = sort_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSort_name() {
            return sort_name;
        }

        public void setSort_name(String sort_name) {
            this.sort_name = sort_name;
        }

        public String getSort_img() {
            return sort_img;
        }

        public void setSort_img(String sort_img) {
            this.sort_img = sort_img;
        }
    }

    public static class PayinfoBean {
        private String pay_type;
        private String pay_name;

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }
    }

}
