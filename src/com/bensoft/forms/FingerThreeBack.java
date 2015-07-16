package com.bensoft.forms;

/**
 * Created by Benson on 3/1/2015.
 */
public class FingerThreeBack {
    private String _gen_id;
    private String _cid;
    private String _user_id;
    private String _gap_fill;
    private String _gap_fill_emer;
    private String _thin_num;
    private String _thin_num_emer;
    private String _farm_id;
    private String _form_date;

    public FingerThreeBack(String _form_date, String cid, String user_id, String gapFillValue, String gapFillOnEmerValue, String thinNumValue, String thinAfterEmerValue,String _farm_id) {
       
        this._cid = cid;
        this._form_date = _form_date;
        this._user_id = user_id;
        this._gap_fill = gapFillValue;
        this._gap_fill_emer = gapFillOnEmerValue;
        this._thin_num = thinNumValue;
        this._thin_num_emer = thinAfterEmerValue;
        this._farm_id = _farm_id;
    }

    public FingerThreeBack() {

    }

    public String getCID() {
        return _cid;
    }

    public String getUserID() {
        return _user_id;
    }

    public String getGapFill() {
        return _gap_fill;
    }

    public String getFillOnEmer() {
        return _gap_fill_emer;
    }

    public String getThinNum() {
        return _thin_num;
    }

    public String getThinNumEmer() {
        return _thin_num_emer;
    }

    public void setCID(String CID) {
        this._cid = CID;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }

    public void setGapFill(String gapFill) {
        this._gap_fill = gapFill;
    }

    public void setGapFillEmer(String gapFillEmer) {
        this._gap_fill_emer = gapFillEmer;
    }

    public void setThinNum(String thinNum) {
        this._thin_num = thinNum;
    }

    public void setThinNumEmer(String thinNumEmer) {
        this._thin_num_emer = thinNumEmer;
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
}
