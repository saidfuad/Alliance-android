package com.bensoft.entities;

/**
 * Created by user on 2/2/2015.
 */
public class Users {
    private String _user_id;
    private String _user_name;
    private String _user_pwd;
    private String _vid;
    private String _co_id;

    public Users(String _user_id, String _user_name, String _user_pwd,String _vid,String _co_id) {
        this._user_id = _user_id;
        this._user_name = _user_name;
        this._user_pwd = _user_pwd;
        this._vid =_vid;
        this._co_id = _co_id;
    }

    public Users() {

    }

    public String getUserID() {
        return _user_id;
    }

    public String getUserName() {
        return _user_name;
    }

    public String getUserPwd() {
        return _user_pwd;
    }

    public String getCoID() {
        return _co_id;
    }

    public void setUserID(String userID) {
        this._user_id = userID;
    }

    public void setUserName(String userName) {
        this._user_name = userName;
    }

    public void setUserPwd(String userPwd) {
        this._user_pwd = userPwd;
    }

    public void setCoID(String coID) {
        this._co_id = coID;
    }

    public String getVID() {
        return _vid;
    }

}
