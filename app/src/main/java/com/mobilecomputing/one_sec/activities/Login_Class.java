package com.example.one_sec;

public class Login_Class {
    private int _id;
    private String _username;
    private String _password;

    //Added this empty constructor in lesson 50 in case we ever want to create the object and assign it later.
    public Login_Class(){

    }
    public Login_Class(String username, String password) {
        this._username = username;
        this._password = password;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String password) {
        this._password = password;
    }
    public String get_username() {
        return _username;
    }

    public void set_username(String _productname) {
        this._username = _productname;
    }
}