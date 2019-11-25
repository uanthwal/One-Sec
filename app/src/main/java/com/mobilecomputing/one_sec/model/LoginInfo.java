package com.mobilecomputing.one_sec.model;

public class LoginInfo {
    private int _id;
    private String username;
    private String password;
    private String email;
    private String mobNum;
    private String dob;

    public int get_id() {
        return _id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getMobNum() {
        return mobNum;
    }

    public String getDob() {
        return dob;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}