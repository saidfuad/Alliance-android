package com.bensoft.forms;

/**
 * Created by Benson on 3/1/2015.
 */
public class FingerOneBack {
    private String _gen_id;
    private String _cid;
    private String _user_id;
    private String _soil_type;
    private String _wl_risk;
    private String _ePrev;
    private String _crop_rotation;
    private String _ratoon;
    private String _cresidues;
    private String _manure;
    private String _lprep;
    private String _sbed_prep;
    private String _erosion_prev;
    private String _farm_id;
    private String _form_date;

    public FingerOneBack(String _form_date, String _cid, String _user_id, String soilTypeValue, String waterLogRiskValue, String erosionPrevValue, String cropRotationValue, String ratoonValue, String cropResValue, String manureValue, String landPrepValue, String seedBedPrepValue, String _farm_id) {
        
        this._form_date = _form_date;
        this._cid = _cid;
        this._user_id = _user_id;
        this._soil_type = soilTypeValue;
        this._wl_risk = waterLogRiskValue;
        this._erosion_prev = erosionPrevValue;
        this._crop_rotation = cropRotationValue;
        this._ratoon = ratoonValue;
        this._cresidues = cropResValue;
        this._manure = manureValue;
        this._lprep = landPrepValue;
        this._sbed_prep = seedBedPrepValue;
        this._farm_id = _farm_id;
    }

    public FingerOneBack() {

    }


    public String getCID() {
        return _cid;
    }

    public String getUserID() {
        return _user_id;
    }

    public String getSoilType() {
        return _soil_type;
    }

    public String getWLRisk() {
        return _wl_risk;
    }


    public String getEPrev() {
        return _erosion_prev;
    }

    public String getCRotation() {
        return _crop_rotation;
    }

    public String getRatoon() {
        return _ratoon;
    }

    public String getCResidues() {
        return _cresidues;
    }

    public String getManure() {
        return _manure;
    }

    public String getLPrep() {
        return _lprep;
    }

    public String getSBedPrep() {
        return _sbed_prep;
    }


    public void setCID(String CID) {
        this._cid = CID;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }


    public void setSoilType(String soilType) {
        this._soil_type = soilType;
    }

    public void setWLRisk(String WLRisk) {
        this._wl_risk = WLRisk;
    }

    public void setErosionPrev(String erosionPrev) {
        this._erosion_prev = erosionPrev;
    }

    public void setCropRotation(String cropRotation) {
        this._crop_rotation = cropRotation;
    }

    public void setRatoon(String ratoon) {
        this._ratoon = ratoon;
    }

    public void setCropResidues(String cropResidues) {
        this._cresidues = cropResidues;
    }

    public void setManure(String manure) {
        this._manure = manure;
    }

    public void setLandPrep(String landPrep) {
        this._lprep = landPrep;
    }

    public void setSBedPrep(String SBedPrep) {
        this._sbed_prep = SBedPrep;
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
