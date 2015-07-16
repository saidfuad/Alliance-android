package com.bensoft.forms;

/**
 * Created by Benson on 3/1/2015.
 */
public class FingerFourBack {
    private String _first_branch;
    private String _foliar;
    private String _weeds;
    private String _gen_id;
    private String _cid;
    private String _user_id;
    private String _farm_id;
    private String _form_date;

    public FingerFourBack(String _form_date, String cid, String user_id, String branchesValue, String foliarValue, String weedsValue,String _farm_id) {
       
        this._cid = cid;
        this._form_date = _form_date;
        this._user_id = user_id;
        this._first_branch = branchesValue;
        this._foliar = foliarValue;
        this._weeds = weedsValue;
        this._farm_id = _farm_id;
    }

    public FingerFourBack() {

    }


    public String getUserID() {
        return _user_id;
    }
    public String getFirstBranch() {
        return _first_branch;
    }

    public String getFoliar() {
        return _foliar;
    }

    public String getWeeds() {
        return _weeds;
    }

    public void setCID(String CID) {
        this._cid = CID;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }

    public void setFirstBranch(String firstBranch) {
        this._first_branch = firstBranch;
    }

    public void setFoliar(String foliar) {
        this._foliar = foliar;
    }

    public void setWeeds(String weeds) {
        this._weeds = weeds;
    }

    public String getFarmID() {
        return _farm_id;
    }

    public void setFarmID(String farmID) {
        this._farm_id = farmID;
    }

    public String getFormDate() {
        return _form_date;
    }

    public String getCID() {
     return _cid;
    }
}
