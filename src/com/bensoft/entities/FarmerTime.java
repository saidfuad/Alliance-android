package com.bensoft.entities;

/**
 * Created by Benson on 2/20/2015.
 */
public class FarmerTime {
    private String _fid;
    private String _date;
    private String _f_time;
    private String _cid;
    private String _user_id;
    private String _id;

    public FarmerTime(String _fid, String _f_time,String _user_id,String _cid) {
        this._fid = _fid;
        this._f_time = _f_time;
        this._user_id = _user_id;
        this._cid = _cid;
    }

    public FarmerTime() {

    }

    public String getFID() {
        return _fid;
    }

    public String getFDate() {
        return _date;
    }

    public String getFTime() {
        return _f_time;
    }

    public void setFID(String FID) {
        this._fid = FID;
    }

    public void setFarmerDate(String farmerDate) {
        this._date = farmerDate;
    }

    public void setFarmerTime(String farmerTime) {
        this._f_time = farmerTime;
    }

    public String getUserID() {
        return _user_id;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }

    public void setID(String ID) {
        this._id = ID;
    }
}
