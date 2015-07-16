package com.bensoft.forms;

/**
 * Created by Benson on 3/1/2015.
 */
public class FingerTwoBack {
    private String _gen_id;
    private String _cid;
    private String _user_id;
    private String _correct_seed;
    private String _row_spacing;
    private String _seed_per_stat;
    private String _planting_time;
    private String _farm_id;
    private String _form_date;

    public FingerTwoBack(String _form_date, String cid, String user_id, String correctSeedValue, String rowSpacingValue, String seedPerStat, String plantingTimeValue,String _farm_id) {
       // this._gen_id = gen_id;
        this._cid = cid;
        this._form_date = _form_date;
        this._user_id = user_id;
        this._correct_seed = correctSeedValue;
        this._row_spacing = rowSpacingValue;
        this._seed_per_stat = seedPerStat;
        this._planting_time = plantingTimeValue;
        this._farm_id = _farm_id;
    }

    public FingerTwoBack() {

    }


    public String getCID() {
        return _cid;
    }

    public String getUserID() {
        return _user_id;
    }

    public String getCorectSeed() {
        return _correct_seed;
    }

    public String getRowSpacing() {
        return _row_spacing;
    }

    public String getSeedPerStat() {
        return _seed_per_stat;
    }

    public String getPlantingTIme() {
        return _planting_time;
    }

    public void setCID(String CID) {
        this._cid = CID;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }


    public void setCorrectSeed(String correctSeed) {
        this._correct_seed = correctSeed;
    }

    public void setRowSpacing(String rowSpacing) {
        this._row_spacing = rowSpacing;
    }

    public void setSeedPerStat(String seedPerStat) {
        this._seed_per_stat = seedPerStat;
    }

    public void setPlantingTime(String plantingTime) {
        this._planting_time = plantingTime;
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
