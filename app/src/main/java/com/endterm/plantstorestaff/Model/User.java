package com.endterm.plantstorestaff.Model;

public class User {
    private String name;
    private String Password;
    private String phone;
    private String isStaff;

    public User() {}
    public User(String name, String password,String isStaff) {
        this.name = name;
        this.Password = password;
        this.isStaff = isStaff;
    }
    public String getIsStaff() { return isStaff; }
    public void setIsStaff(String staff) {
        this.isStaff = staff;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        this.Password = password;
    }
    public String getPhone(){return phone;}
    public void setPhone(String phone){
        this.phone = phone;
    }
}
