/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.entities;

/**
 *
 * @author Benson
 */
public class FarmOtherCrops {

    private String _farm_id;
    private String _crop_id_1;
    private String _crop_id_2;
    private String _crop_id_3;
    private String _user_id;
    private String _collect_date;
    private String _lat;
    private String _longt;
    private String _cid;

    public FarmOtherCrops(String _farm_id, String _crop_id_1, String _crop_id_2, String _crop_id_3, String _user_id, String _collect_date, String _lat, String _longt, String _cid) {
        this._farm_id = _farm_id;
        this._crop_id_1 = _crop_id_1;
        this._crop_id_2 = _crop_id_2;
        this._crop_id_3 = _crop_id_3;
        this._user_id = _user_id;
        this._collect_date = _collect_date;
        this._lat = _lat;
        this._longt = _longt;
        this._cid = _cid;

    }

    public FarmOtherCrops() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getFarmID() {
        return _farm_id;
    }

    public String getCropID1() {
        return _crop_id_1;
    }

    public String getCropID2() {
        return _crop_id_2;
    }

    public String getCropID3() {
        return _crop_id_3;
    }

    public String getUserID() {
        return _user_id;
    }

    public String getCollectDate() {
        return _collect_date;
    }

    public String getLat() {
        return String.valueOf(_lat);
    }

    public String getLongt() {
        return String.valueOf(_longt);
    }

    public String getCID() {
        return _cid;
    }

    public void setFarmID(String _farm_id) {
        this._farm_id = _farm_id;
    }

    public void setCropID1(String _crop_id1) {
        this._crop_id_1 = _crop_id1;
    }

    public void setCropID2(String _crop_id_2) {
        this._crop_id_2 = _crop_id_2;
    }

    public void setCropID3(String _crop_id_3) {
        this._crop_id_3 = _crop_id_3;
    }

    public void setUserID(String _user_id) {
        this._user_id = _user_id;
    }

    public void setCollectDate(String _collect_date) {
        this._collect_date = _collect_date;
    }

    public void setLat(String _lat) {
        this._lat = _lat;
    }

    public void setLongt(String _longt) {
        this._longt = _longt;
    }

    public void setCID(String _cid) {
        this._cid = _cid;
    }

}
