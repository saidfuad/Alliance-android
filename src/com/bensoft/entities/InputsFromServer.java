package com.bensoft.entities;

/**
 * Created by user on 12/29/2014.
 */


public class InputsFromServer {

    //private variables
    String _input_id;
    String _input_cat,_input_typ;


    // Empty constructor
    public InputsFromServer() {

    }

    // constructor
    public InputsFromServer(String _id,String _input_cat, String _input_typ) {
        //this._input_id = id;
        this._input_id = _id;
        this._input_cat = _input_cat;
        this._input_typ = _input_typ;

    }

    // getting ID
    public String getInputId() {
        return this._input_id;
    }

    public void setInputId(String id) {
        this._input_id = id;
    }



    public String getInputCat() {
        return this._input_cat;
    }

    public void setInputCat(String cat) {
        this._input_cat = cat;
    }

    public String getInputType() {
        return this._input_typ;
    }

    public void setInputType(String type) {
        this._input_typ = type;
    }



}
