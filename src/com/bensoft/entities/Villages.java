package com.bensoft.entities;

/**
 * Created by user on 1/20/2015.
 */
public class Villages {
    private String _ward_id;
    private String _village_id;
    private String _village_name;

    public Villages(String _village_id,String _ward_id, String _village_name) {
        this._village_id = _village_id;
        this._ward_id = _ward_id;
        this._village_name = _village_name;
    }

    public Villages() {
        
    }

    public String getVillageID() {
        return _village_id;
    }
    public String getWardID() {
        return _ward_id;
    }

    public String getVillageName() {
        return _village_name;
    }

    public void setVillageID(String villageID) {
        this._village_id = villageID;
    }
    public void setWardID(String wardID) {
        this._ward_id = wardID;
    }

    public void setVillageName(String villageName) {
        this._village_name = villageName;
    }
}
