package com.bensoft.entities;

/**
 * Created by user on 1/20/2015.
 */
public class Countries {
    private String _country_id;
    private String _country_name;

    public Countries(String _country_id, String _country_name) {
        this._country_id = _country_id;
        this._country_name = _country_name;
    }

    public Countries() {

    }

    public String getCountryID() {
        return _country_id;
    }

    public String getCountryName() {
        return _country_name;
    }

    public void setCountryID(String countryID) {
        this._country_id = countryID;
    }

    public void setCountryName(String countryName) {
        this._country_name = countryName;
    }
}
