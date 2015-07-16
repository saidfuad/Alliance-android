package com.bensoft.entities;

/**
 * Created by user on 2/2/2015.
 */
public class Companies {
    private String _co_id;
    private String _co_name;

    public Companies(String _co_id, String _co_name) {
        this._co_id = _co_id;
        this._co_name = _co_name;
    }

    public Companies() {

    }

    public String getCoID() {
        return _co_id;
    }

    public String getCoName() {
        return _co_name;
    }

    public void setCoID(String coID) {
        this._co_id = coID;
    }

    public void setCoName(String coName) {
        this._co_name = coName;
    }
}
