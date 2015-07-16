package com.bensoft.entities;

/**
 * Created by user on 1/19/2015.
 */
public class Structures {
    private String _countries,_regions,_districts,_wards,_villages,_sub_villages;
    private int villages;

    public Structures() {
    }
    // constructor
    public Structures(String _countries,String _regions,String _districts,String _wards,String _villages,String _sub_villages) {
        //this._input_id = id;
        this._countries = _countries;
        this._regions = _regions;
        this._districts = _districts;
        this._wards = _wards;
        this._villages = _villages;
        this._sub_villages = _sub_villages;

    }

    // getting ID
    public String getCountries() {
        return this._countries;
    }

    public String getRegions() {
        return _regions;
    }

    public String getDistricts() {
        return _districts;
    }

    public String getWards() {
        return _wards;
    }

    public String getVillages() {
        return _villages;
    }

    public String getSubVillages() {
        return _sub_villages;
    }

    public void setCountries(String countries) {
        this._countries = countries;
    }

    public void setRegions(String regions) {
        this._regions = regions;
    }

    public void setDistricts(String districts) {
        this._districts = districts;
    }

    public void setWards(String wards) {
        this._wards = wards;
    }

    public void setVillages(String villages) {
        this._villages = villages;
    }

    public void setSubVillages(String subVillages) {
        this._sub_villages = subVillages;
    }
}
