package com.bensoft.entities;

/**
 * Created by Benson on 3/29/2015.
 */
public class KmlMetaData {

    private String _user_id;
    private String _lat;
    private String _longt;
    private String _cid;
    private String _fid;
    private String _farm_id;

    public KmlMetaData(String _fid, String _user_id, String _cid, String _farm_id) {
        this._fid = _fid;
        this._user_id = _user_id;
        this._cid = _cid;
        this._farm_id = _farm_id;
    }

    public KmlMetaData() {

    }

    public String getUserID() {
        return _user_id;
    }

    public String getCID() {
        return _cid;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }

    public void setCID(String CID) {
        this._cid = CID;
    }

    public void setFID(String FID) {
        this._fid = FID;
    }

    public String getFID() {
        return _fid;
    }

    public String getFarmID() {
        return _farm_id;
    }

    public void setFarmID(String _farm_id) {
        this._farm_id = _farm_id;
    }
}
