package com.bensoft.utils;

/**
 * Created by Benson on 2/10/2015.
 */
public class MyData {
    private String _farm_name;
    private String _farm_id;
    private String _farm_size;
    private String _train_type;
    private String _train_id;
    private String spinnerCat;
    private String value2;
    String spinnerText2;
    String hiddenDate;
    String spinnerText;
    String value;

    public MyData(String spinnerText,String spinnerText2, String value, String hiddenDate) {
        this.spinnerText = spinnerText;
        this.spinnerText2 = spinnerText2;
        this.value = value;
        this.hiddenDate = hiddenDate;
    }

 //   public MyData(String spinnerCat, String value2) {
    //    this.spinnerCat = spinnerCat;
   //     this.value2 = value2;
  //  }

    public MyData(String train_type, String train_id) {
        this._train_type = train_type;
        this._train_id = train_id;
    }

    public MyData(String farmName, String farmID, String farmSize) {
        this._farm_name = farmName;
        this._farm_id = farmID;
        this._farm_size = farmSize;
    }

    public String getSpinnerCat() {
        return spinnerCat;
    }public String getSpinnerText() {
        return spinnerText;
    }
    public String getSpinnerText2() {
        return spinnerText2;
    }

    public String getDynamicSpinnerText2(String key) {
        return spinnerText2;
    }

    public String getSpinnerDate() {
        return hiddenDate;
    }

    public String getValue() {
        return value;
    }
    public String getFarmID(){return _farm_id;}
    public String getFarmName(){return _farm_name;}
    public String getFarmSize(){return _farm_size;}
    public String getValue2() {
        return value2;
    }

    public String toString() {
        return spinnerText;
    }
}

