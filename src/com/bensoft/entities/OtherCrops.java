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
public class OtherCrops {
    private String _crop_id;
    private String _crop_name;

    public OtherCrops(){
    }

    public OtherCrops(String _crop_id, String _crop_name) {
       this._crop_id  = _crop_id;
       this._crop_name = _crop_name;
    }
    
    public String getCropID() {
       return _crop_id;
    }

    public String getCropName() {
       return _crop_name;
    }

    public void setCropID(String _crop_id) {
       this._crop_id = _crop_id;
    }

    public void setCropName(String _crop_name) {
       this._crop_name = _crop_name;
    }

    
    
}
