package com.bensoft.entities;

/**
 * Created by user on 1/20/2015.
 */
public class Wards {
    private String _district_id;
    private String _ward_id;
    private String _ward_name;

    public Wards(String _ward_id,String _district_id, String _ward_name) {
        this._ward_id = _ward_id;
        this._district_id = _district_id;
        this._ward_name = _ward_name;
    }

    public Wards() {

    }

    public String getWardID() {
        return _ward_id;
    }
    public String getDistrictID() {
        return _district_id;
    }

    public String getWardName() {
        return _ward_name;
    }

    public void setWardID(String wardID) {
        this._ward_id = wardID;
    }
    public void setDistrictID(String districtID) {
        this._district_id = districtID;
    }

    public void setWardName(String wardName) {
        this._ward_name = wardName;
    }
}
