package com.bensoft.entities;

/**
 * Created by Benson on 3/1/2015.
 */
public class Farm {
    private String _farm_id;
    private String _farm_name;
    private String _farm_size;
    private String _farm_peri;
    private String _cid;
    private String _lat;
    private String _longt;

    public Farm(String pid, String _farm_name, String _farm_size, String _farm_peri,String _lat,String _longt,String cid) {
        this._farm_id = pid;
        this._farm_name = _farm_name;
        this._farm_size = _farm_size;
        this._farm_peri = _farm_peri;
        this._lat = _lat;
        this._longt= _longt;
        this._cid = cid;
    }

    public Farm() {

    }

    public String getFarmID() {
        return _farm_id;
    }

    public String getFarmName() {
        return _farm_name;
    }

    public String getFarmSize() {
        return _farm_size;
    }

    public String getFarmPeri() {
        return _farm_peri;
    }

    public String getCID() {
        return _cid;
    }

    public void setFarmID(String farmID) {
        this._farm_id = farmID;
    }

    public void setFarmName(String farmName) {
        this._farm_name = farmName;
    }

    public void setFarmSize(String farmSize) {
        this._farm_size = farmSize;
    }

    public void setFarmPeri(String farmPeri) {
        this._farm_peri = farmPeri;
    }

    public void setCID(String CID) {
        this._cid = CID;
    }

    public void setLat(String _lat) {
        this._lat = _lat;
    }

    public void setLongt(String _longt) {
       this._longt= _longt;
    }

    public String getLat() {
        return _lat;
    }

    public String getLongt() {
        return _longt;
    }
}
