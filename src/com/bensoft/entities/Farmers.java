package com.bensoft.entities;

/**
 * Created by user on 12/29/2014.
 */


public class Farmers {

    private String _reg_date;
    private String _cid;
    String _lat,_longt;
    //private variables
    int _id;
    String _id_no;
    String _fname,_lname,_gender;
    String _phone_number;
    String _email, _post, _village, _country, _district,_pic_path;
    private String _region;
    private String _ward;
    private String _sub_village;
    private String _est_farm_area;


    // Empty constructor
    public Farmers() {

    }

    // constructor
    public Farmers( String _fname,String _lname,String _gender,String _id_no, String _phone_number, String _email, String _post,
                    String _village,String _sub_village,String _pic_path,String _lat,String _longt,String _est_farm_area,String _reg_date,String _cid) {
        //this._id = id;
        this._id_no = _id_no;
        this._fname = _fname;
        this._lname = _lname;
        this._gender = _gender;
        this._phone_number = _phone_number;
        this._email = _email;
        this._post = _post;
        this._village = _village;
        this._sub_village = _sub_village;
        this._pic_path = _pic_path;
       // this._finger_path = _finger_path;
        this._lat =_lat;
        this._longt = _longt;
        this._est_farm_area = _est_farm_area;
        this._reg_date = _reg_date;
        this._cid = _cid;

    }

    // getting ID
    public int getID() {
        return this._id;
    }

    public void setID(int id) {
        this._id = id;
    }

    public String getIDNO() {
        return this._id_no;
    }

    public void setIDNO(String id_no) {
        this._id_no = id_no;
    }


    // getting fname
    public String getFName() {
        return this._fname;
    }

    // setting fname
    public void setFName(String name) {
        this._fname = name;
    }
    // getting lname
    public String getLName() {
        return this._lname;
    }

    // setting lname
    public void setLName(String name) {
        this._lname = name;
    }
    // getting gender
    public String getGender() {
        return this._gender;
    }

    // setting gender
    public void setGender(String gender) {
        this._gender = gender;
    }

    // getting phone number
    public String getPhoneNumber() {
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
        this._phone_number = phone_number;
    }

    public String getEmail() {
        return this._email;
    }

    // setting email
    public void setEmail(String email) {
        this._email = email;
    }

    public String getPost() {
        return this._post;
    }

    // setting post
    public void setPost(String post) {
        this._post = post;
    }

    public String getVillage() {
        return this._village;
    }

    // setting village
    public void setVillage(String village) {
        this._village = village;
    }
       //getting country
    public String getCountry() {
        return this._country;
    }

    // setting country
    public void setCountry(String country) {
        this._country = country;
    }

    public String getDistrict() {
        return this._district;
    }

    // setting district
    public void setDistrict(String district) {
        this._district = district;
    }
    public String getPicPath() {
        return this._pic_path;
    }

    // setting pic path
    public void setPicPath(String filePath) {
        this._pic_path = filePath;
    }
    //getting finger path
  //  public String getFingerPath() {
  //     return this._finger_path;
  //  }

    // setting finger path
   // public void setFingerPath(String filePath) {
   //     this._finger_path = filePath;
  //  }

    public void setRegions(String _region) {
        this._region = _region;
    }

    public void setWard(String _ward) {
        this._ward = _ward;
    }

    public void setSubVillage(String _sub_village) {
        this._sub_village = _sub_village;
    }

    public String getLat() {
        return _lat;
    }

    public String getLong() {
        return _longt;
    }

    public void setLat(String lat) {
        this._lat = lat;
    }

    public void setLongt(String longt) {
        this._longt = longt;
    }

    public void setCID(String CID) {
        this._cid = CID;
    }

    public String getCID() {
        return _cid;
    }

    public String getRegion() {
        return _region;
    }

    public String getWard() {
        return _ward;
    }

    public String getSubVillage() {
        return _sub_village;
    }

    public void setRegDate(String regDate) {
        this._reg_date = regDate;
    }

    public String getRegDate() {
        return _reg_date;
    }

    public String getEstFarmArea() {
        return _est_farm_area;
    }

    public void setEstFarmArea(String _est_farm_area) {
      this._est_farm_area = _est_farm_area;
    }
}
