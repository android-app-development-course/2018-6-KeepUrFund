package com.example.darkness.KeepUrFund.bean;

import java.io.Serializable;

/**
 */

public class BaseBean implements Serializable {


    private static final long serialVersionUID = 1L;


    private int status=1;
    private String message="成功！";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}