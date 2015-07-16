/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.entities;

import com.bensoft.utils.DatabaseHandler;

/**
 *
 * @author Benson
 */
public class FarmAssFormsMedium{

    private String _farm_id;
    private String _form_type_id;
    private String _activity_date;
    private String _family_hours;
    private String _hired_hours;
    private String _collect_date;
    private String _money_out;
    private String _remarks;
    private String _user_id;
    private String _lat;
    private String _longt;
    private String _cid;
    private String _activity_method;
    private String _input_id;
    private String _input_quantity;
    private String _spray_method;

    /**
     * Constructor
     * @param _farm_id
     * @param _form_type_id
     * @param _activity_method
     * @param _activity_date
     * @param _family_hours
     * @param _hired_hours
     * @param _money_out
     * @param _input_id
     * @param _input_quantity
     * @param _spray_method
     * @param _user_id
     * @param _collect_date
     * @param _lat
     * @param _longt
     * @param _cid
     */
    public FarmAssFormsMedium(String _farm_id, String _form_type_id, String _activity_method, String _activity_date, String _family_hours, String _hired_hours, String _money_out, String _input_id, String _input_quantity, String _spray_method, String _user_id, String _collect_date, String _lat, String _longt, String _cid) {
        this._farm_id = _farm_id;
        this._form_type_id = _form_type_id;
        this._activity_method = _activity_method;
        this._activity_date = _activity_date;
        this._family_hours = _family_hours;
        this._hired_hours = _hired_hours;
        this._collect_date = _collect_date;
        this._money_out = _money_out;
        this._input_id = _input_id;
        this._input_quantity = _input_quantity;
        this._spray_method = _spray_method;
        this._user_id = _user_id;
        this._lat = _lat;
        this._lat = _longt;
        this._cid = _cid;

    }

    public FarmAssFormsMedium() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getFarmID() {
        return _farm_id;
    }

    public String getFormTypeID() {
        return _form_type_id;
    }

    public String getActDate() {
        return _activity_date;
    }

    public String getFamilyHours() {
        return _family_hours;
    }

    public String getHiredHours() {
        return _hired_hours;
    }

    public String getCollectDate() {
        return _collect_date;
    }

    public String getMoneyOut() {
        return _money_out;
    }

    public String getRemarks() {
        return _remarks;
    }

    public String getUserID() {
        return _user_id;
    }

    public String getCoID() {
        return _cid;
    }

    public void setFarmID(String _farm_id) {
        this._farm_id = _farm_id;
    }

    public void setFormTypeID(String _form_type_id) {
        this._form_type_id = _form_type_id;
    }

    public void setActivityDate(String _activity_date) {
        this._activity_date = _activity_date;
    }

    public void setFamilyHours(String _family_hours) {
        this._family_hours = _family_hours;
    }

    public void setHiredHours(String _hired_hours) {
        this._hired_hours = _hired_hours;
    }

    public void setCollectDate(String _collect_date) {
        this._collect_date = _collect_date;
    }

    public void setMoneyOut(String _money_out) {
        this._money_out = _money_out;
    }

    public void setRemarks(String _remarks) {
        this._remarks = _remarks;
    }

    public void setUserID(String _user_id) {
        this._user_id = _user_id;
    }

    public void setCoID(String _cid) {
        this._cid = _cid;
    }

    public String getActivityMethod() {
        return _activity_method;
    }

    public void setActivityMethodID(String _activity_method) {
        this._activity_method = _activity_method;
    }

    public String getInputID() {
        return _input_id;
    }

    public String getInputQuantity() {
        return _input_quantity;
    }

    public String getSprayType() {
        return _spray_method;
    }

    public void setInputID(String _input_id) {
        this._input_id = _input_id;
    }

    public void setInputQuantity(String _input_quantity) {
        this._input_quantity = _input_quantity;
    }

    public void setSprayType(String _spray_method) {
        this._spray_method = _spray_method;
    }

    public String getLat() {
        return _lat;
    }

    public String getLongt() {
        return _longt;
    }
}
