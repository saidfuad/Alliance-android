package com.bensoft.entities;

/**
 * Created by user on 1/21/2015.
 */
public class Assigns {

    private String _input_id;
    private String _input_type;
    private String _gen_id;
    private String _rcpt_num;
    private String _fid;
    private String _input_total;
    private String _input_price;
    private String _assfinputs_id;
    private String _input_unit;

    public Assigns(String _assfinputs_id, String _input_id, String _input_type,String _input_unit,
            String _input_price, String _input_total, String _fid, String _gen_id, String _rcpt_num) {
        this._assfinputs_id = _assfinputs_id;
        this._input_id = _input_id;
        this._input_type = _input_type;
        this._input_price = _input_price;
        this._input_unit = _input_unit;
        this._input_total = _input_total;
        this._fid = _fid;
        this._gen_id = _gen_id;
        this._rcpt_num = _rcpt_num;
    }

    public Assigns() {

    }

    public String getInputID() {
        return _input_id;
    }

    public String getInputType() {
        return _input_type;
    }

    public String getGenID() {
        return _gen_id;
    }

    public void setInputID(String inputId) {
        this._input_id = inputId;
    }

    public void setInputType(String inputType) {
        this._input_type = inputType;
    }

    public void setGenID(String genID) {
        this._gen_id = genID;
    }

    public String getInputPrice() {
        return _input_price;
    }

    public String getInputTotal() {
        return _input_total;
    }

    public String getFID() {
        return _fid;
    }

    public String getRcptNum() {
        return _rcpt_num;
    }

    public void setInputPrice(String _input_price) {
        this._input_price = _input_price;
    }

    public void setInputTotal(String _input_total) {
        this._input_total = _input_total;
    }

    public void setFID(String _fid) {
        this._fid = _fid;
    }

    public void setRcptNum(String _rcpt_num) {
        this._rcpt_num = _rcpt_num;
    }

    public void setAssInputID(String _assfinputs_id) {
        this._assfinputs_id = _assfinputs_id;
    }

    public String getInputAssInputID() {
        return _assfinputs_id;
    }

    public String getInputUnit() {
        return _input_unit;
    }

    public void setInputUnit(String _input_unit) {
        this._input_unit = _input_unit;
    }
}
