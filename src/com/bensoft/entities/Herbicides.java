/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.entities;

/**
 *
 * @author Benson
 */
public class Herbicides {

    private String _input_id;
    private String _input_type;

    public Herbicides(String _input_id, String _input_type) {
       this._input_id = _input_id;
       this._input_type = _input_type;
    }

    public Herbicides() {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getInputID() {
        return _input_id;
    }

    public String getInputType() {
        return _input_type;
    }

    public void setInputID(String _input_id) {
        this._input_id = _input_id;
    }

    public void setInputType(String _input_type) {
        this._input_type = _input_type;
    }

}
