package com.example.bazaarbargains;

public class Users {

    String firstnName;
    String lastName;
    String userName;
    String password;


    public Users() {
    }

    public Users(String firstnName, String lastName, String userName, String password) {
        this.firstnName = firstnName;
        this.lastName = lastName;
        this.userName = userName;

        this.password = password;
    }


    public String getFirstnName() {
        return firstnName;
    }

    public void setFirstnName(String firstnName) {
        this.firstnName = firstnName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
