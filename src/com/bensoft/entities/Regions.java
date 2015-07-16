package com.bensoft.entities;

/**
 * Created by user on 1/20/2015.
 */
public class Regions {
    private String _country_id;
    private String _region_name;
    private String _region_id;

    public Regions(String _region_id,String _country_id, String _region_name) {
        this._region_id = _region_id;
        this._country_id = _country_id;
        this._region_name = _region_name;
    }

    public Regions() {

    }

    public String getRegionName() {
        return _region_name;
    }

    public String getRegionID() {
        return _region_id;
    }
    public String getCountryID() {
        return _country_id;
    }

    public void setRegionID(String regionID) {
        this._region_id = regionID;
    }
    public void setCountryID(String countryID) {
        this._country_id = countryID;
    }

    public void setRegionName(String regionName) {
        this._region_name = regionName;
    }
}
