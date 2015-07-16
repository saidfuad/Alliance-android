package com.bensoft.entities;

/**
 * Created by user on 1/20/2015.
 */
public class SubVillages {
    private String _subvillage_id;
    private String _village_id;
    private String _subvilage_name;

    public SubVillages(String _subvillage_id, String _village_id, String _subvillage_name) {
        this._subvillage_id =_subvillage_id;
        this._village_id = _village_id;
        this._subvilage_name = _subvillage_name;
    }

    public SubVillages() {

    }

    public String getSubVillageID() {
        return _subvillage_id;
    }
    public String getVillageID() {
        return _village_id;
    }

    public String getSubVillageName() {
        return _subvilage_name;
    }

    public void setSubVillageID(String subVillageID) {
        this._subvillage_id = subVillageID;
    }
    public void setVillageID(String villageID) {
        this._village_id = villageID;
    }

    public void setSubVillageName(String subVillageName) {
        this._subvilage_name = subVillageName;
    }
}
