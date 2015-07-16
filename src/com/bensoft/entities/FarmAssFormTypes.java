/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.entities;

/**
 * class to interact with DatabaseHandler (Farm assessment form type tables).
 * @author Benson
 */
public class FarmAssFormTypes {

    private String _form_type_id;
    private String _form_id;
    private String _form_type;

    public String getFormTypeID() {
        return _form_type_id;
    }

    public String getFormID() {
        return _form_id;
    }

    public String getFormType() {
        return _form_type;
    }

    public void setFormTypeID(String _form_type_id) {
        this._form_type_id = _form_type_id;
    }

    public void setFormID(String _form_id) {
        this._form_id = _form_id;
    }

    public void setFormType(String _form_type) {
        this._form_type = _form_type;
    }

}
