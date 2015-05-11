package com.my.web;

/**
 * Created by Sergey on 10.05.2015.
 */
public class ResultBean {

    private boolean crcOk;
    private boolean digestOk;

    public boolean isCrcOk() {
        return crcOk;
    }

    public void setCrcOk(boolean crcOk) {
        this.crcOk = crcOk;
    }

    public boolean isDigestOk() {
        return digestOk;
    }

    public void setDigestOk(boolean digestOk) {
        this.digestOk = digestOk;
    }
}
