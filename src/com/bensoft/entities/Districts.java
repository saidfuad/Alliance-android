package com.bensoft.entities;

/**
 * Created by user on 1/20/2015.
 */
public class Districts {
    private String _region_id;
    private String _district_id;
    private String _district_name;

    public Districts(String _district_id,String _region_id, String _district_name) {
        this._district_id = _district_id;
        this._region_id = _region_id;
        this._district_name = _district_name;
    }

    public Districts() {

    }

    public String getDistrictID() {
        return _district_id;
    }
    public String getRegionID() {
        return _region_id;
    }

    public String getDistrictName() {
        return _district_name;
    }

    public void setDistrictID(String districtID) {
        this._district_id = districtID;
    }
    public void setRegionID(String regionID) {
        this._region_id = regionID;
    }
    public void setDistrictName(String districtName) {
        this._district_name = districtName;
    }
}
