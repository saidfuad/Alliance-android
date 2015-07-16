package com.bensoft.entities;

/**
 * Created by Benson on 3/17/2015.
 */
public class SignedDoc {
    private String _fid;
    private String _sign_date;
    private String _user_id;
    private String _cid;

    public SignedDoc(String farmerID, String format, String user_id, String cid) {
        this._fid = farmerID;
        this._sign_date = format;
        this._user_id = user_id;
        this._cid = cid;
    }

    public SignedDoc() {

    }

    public String getFID() {
        return _fid;
    }

    public String getSignDate() {
        return _sign_date;
    }

    public String getUserID() {
        return _user_id;
    }

    public String getCID() {
        return _cid;
    }

    public void setFID(String FID) {
        this._fid = FID;
    }

    public void setSignDate(String signDate) {
        this._sign_date = signDate;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }

    public void setCID(String CID) {
        this._cid = CID;
    }
}
