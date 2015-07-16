package com.bensoft.entities;

/**
 * Created by user on 1/26/2015.
 */
public class Training {
    private String _lat;
    private String _longt;
    private String _date;
    private String _s1;
    private String _s;
    private String _train_id;
    private String _train_name;
    private String _train_date;
    private String _gen_id;
    private String _cid;
    private String _train_cat;
    private String _train_type;
    private String TCatID;
    private String _tcatid;
    private String _train_start_time;
    private String _train_stop_time;
    private String _train_status;

    public Training(String _train_id,String _train_start_time,String _train_stop_time,String _lat,String _longt, String _cid) {
        this._train_id = _train_id;
        this._train_start_time = _train_start_time;
        this._train_stop_time = _train_stop_time;
        this._cid = _cid;
        this._lat = _lat;
        this._longt = _longt;
    }

    public Training() {

    }



    public Training(String s, String s1) {
        this._s = s;
        this._s1 = s1;
    }

    public Training(String _train_id, String _train_type, String _train_date) {
        this._train_id = _train_id;
        this._train_type = _train_type;
        this._train_date = _train_date;
    }

    public String getTID() {
        return _train_id;
    }

    public String getTName() {
        return _train_name;
    }

    public String getTDate() {
        return _train_date;
    }
    public String getTDate1() {
        return _date;
    }

    public void setTID(String TID) {
        this._train_id = TID;
    }

    public void setTName(String TName) {
        this._train_name = TName;
    }

    public void setTDate(String TDate) {
        this._train_date = TDate;
    }

    public void setTDate1(String TDate) {
        this._date = TDate;
    }
    public String getGenID() {
        return _gen_id;
    }
    public void setGenID(String genID) {
        this._gen_id = genID;
    }

    public void setCID(String CID) {
        this._cid = CID;
    }

    public String getCID() {
        return _cid;
    }

    public String getTCat() {
        return _train_cat;
    }

    public String getTType() {
        return _train_type;
    }

    public void setTCat(String TCat) {
        this._train_cat = TCat;
    }

    public void setTType(String TType) {
        this._train_type = TType;
    }

    public String getTCatID() {
        return _tcatid;
    }

    public void setTCatID(String TCatID) {
        this._tcatid = TCatID;
    }

    public String getTCatID2() {
        return _s;
    }

    public String getTCat2() {
        return _s1;
    }

    public void setTCatID2(String TCatID2) {
        this._s = TCatID2;
    }

    public void setTCat2(String TCat2) {
        this._s1 = TCat2;
    }

    public String getTStartTime() {
        return _train_start_time;
    }

    public void setTStartTime(String TStartTime) {
        this._train_start_time = TStartTime;
    }

    public String getTStopTime() {
        return _train_stop_time;
    }

    public void setTStopTime(String TStopTime) {
        this._train_stop_time = TStopTime;
    }

    public String getTStatus() {
        return _train_status;
    }

    public void setTStatus(String TTypeStatus) {
        this._train_status = TTypeStatus;
    }

    public String getTLat() {
        return _lat;
    }

    public String getTLongt() {
        return _longt;
    }

    public void setTLat(String TLat) {
        this._lat = TLat;
    }

    public void setTLongt(String TLongt) {
        this._longt = TLongt;
    }
}
