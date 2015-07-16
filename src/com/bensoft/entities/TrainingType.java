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
public class TrainingType {

    private String _tid;
    private String _train_type;
    private String _train_cat_id;

    public TrainingType(String _train_cat_id,String _train_type_id, String _train_type) {
        this._train_cat_id = _train_cat_id;
        this._tid = _train_type_id;
        this._train_type = _train_type;
    }

    public TrainingType() {
        
    }

    public void setTID(String _tid) {
        this._tid = _tid;
    }

    public void setTType(String _train_type) {
        this._train_type = _train_type;
    }

    public String getTID() {
        return _tid;
    }

    public String getTType() {
        return _train_type;
    }

    public String getTcatID() {
       return _train_cat_id;
    }

    public void setTrainCatID(String _train_cat_id) {
       this._train_cat_id = _train_cat_id;
    }

}
