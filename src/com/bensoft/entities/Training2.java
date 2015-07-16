package com.bensoft.entities;

/**
 * Created by Benson on 2/25/2015.
 */
public class Training2 extends Training {
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
    private String _user_id;
    private String villageID;
    private String _village_id;
    private String _train_type_id;
    private String _ext_train_date;
    private String _farm_id;

    public Training2(String _train_type_id,String _ext_train_date,String _farm_id) {
        this._train_type_id = _train_type_id;
        this._ext_train_date = _ext_train_date;
        this._farm_id = _farm_id;
    }

    public Training2() {

    }

    public String getTID() {
        return _train_type_id;
    }


    public String getTDate1() {
        return _ext_train_date;
    }
    
    
    public String getFarmID() {
        return _farm_id;
    }
    
    public void setFarmID(String FID) {
        this._farm_id = FID;
    }
    
    public String getTDate() {
        return _train_date;
    }

    public void setTID(String TID) {
        this._train_type_id = TID;
    }

    public void setTName(String TName) {
        this._train_name = TName;
    }

    public void setTDate(String TDate) {
        this._train_date = TDate;
    }

    public void setTDate1(String TDate) {
        this._ext_train_date = TDate;
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

    public String getUserID() {
        return _user_id;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }

    public void setVillageID(String villageID) {
        this._village_id = villageID;
    }

    public String getVillageID() {
        return _village_id;
    }
}
