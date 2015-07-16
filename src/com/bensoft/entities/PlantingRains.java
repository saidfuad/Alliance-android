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
public class PlantingRains {
    private String _farm_id;
    private String _rain_date;
    private String _collect_date;
    private String _user_id;
    private double _lat;
    private double _longt;
    private String _cid;

    public PlantingRains(String _farm_id, String _rain_date, String _collect_date, String _user_id,double _lat, double _longt, String _cid) {
       this._farm_id = _farm_id;
       this._rain_date = _rain_date;
       this._collect_date = _collect_date;
       this._user_id = _user_id;
       this._lat = _lat;
       this._longt = _longt;
       this._cid = _cid;
       
    }

    public String getFarmID() {
        return _farm_id;
    }

    public String getRainDate() {
        return _rain_date;
    }

    public String getCollectDate() {
        return _collect_date;
    }

    public String getUserID() {
        return _user_id;
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

}
