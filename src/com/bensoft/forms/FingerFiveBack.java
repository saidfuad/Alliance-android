package com.bensoft.forms;

/**
 * Created by Benson on 3/1/2015.
 */
public class FingerFiveBack {
    private String _pest_level;
    private String _pest_damage;
    private String _last_scout;
    private String _empty_cans;
    private String _peg_board_avail;
    private String _scout_method;
    private String _spray_time;
    private String pest_abs_dur;
    private String _correct_pest_use;
    private String _safe_usage_cans;
    private String _user_id;
    private String _cid;
    private String _gen_id;
    private String _pest_abs_dur;
    private String _farm_id;
    private String _form_date;

    public FingerFiveBack(String _form_date, String cid, String user_id, String pestLevelValue, String pestDamageValue, String scoutSignValue, String emptyCansValue, String pegBoardValue, String scoutMethodValue, String sprayTimeValue, String pestAbsDurrationValue, String correctPesticideValue, String safeUsageCansValue,String _farm_id) {
        
        this._form_date = _form_date;
        this._cid = cid;
        this._user_id = user_id;
        this._pest_level = pestLevelValue;
        this._pest_damage = pestDamageValue;
        this._last_scout = scoutSignValue;
        this._empty_cans = emptyCansValue;
        this._peg_board_avail = pegBoardValue;
        this._scout_method = scoutMethodValue;
        this._spray_time = sprayTimeValue;
        this._pest_abs_dur = pestAbsDurrationValue;
        this._correct_pest_use = correctPesticideValue;
        this._safe_usage_cans = safeUsageCansValue;
           this._farm_id = _farm_id;
    }

    public FingerFiveBack() {

    }

    public String getPestLevel() {
        return _pest_level;
    }

    public String getPestDamage() {
        return _pest_damage;
    }

    public String getLastScout() {
        return _last_scout;
    }

    public String getEmptyCans() {
        return _empty_cans;
    }

    public String getPegBoardAvail() {
        return _peg_board_avail;
    }

    public String getScoutMethod() {
        return _scout_method;
    }

    public String getSprayTime() {
        return _spray_time;
    }

    public String getPestAbsDur() {
        return _pest_abs_dur;
    }

    public String getCorrectPestUse() {
        return _correct_pest_use;
    }

    public String getSafeUsageCans() {
        return _safe_usage_cans;
    }

    public String getUserID() {
        return _user_id;
    }

    public String getCID() {
        return _cid;
    }


    public void setCID(String CID) {
        this._cid = CID;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }

    public void setPestLevel(String pestLevel) {
        this._pest_level = pestLevel;
    }

    public void setPestDamage(String pestDamage) {
        this._pest_damage = pestDamage;
    }

    public void setLastScout(String lastScout) {
        this._last_scout = lastScout;
    }

    public void setEmptyCans(String emptyCans) {
        this._empty_cans = emptyCans;
    }

    public void setPegBoardAvail(String pegBoardAvail) {
        this._peg_board_avail = pegBoardAvail;
    }

    public void setScoutMethod(String scoutMethod) {
        this._scout_method = scoutMethod;
    }

    public void setSprayTime(String sprayTime) {
        this._spray_time = sprayTime;
    }

    public void setPestADur(String pestADur) {
        this._pest_abs_dur = pestADur;
    }

    public void setPestCUse(String pestCUse) {
        this._correct_pest_use = pestCUse;
    }

    public void setSafeCanUsage(String safeCanUsage) {
        this._safe_usage_cans = safeCanUsage;
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
