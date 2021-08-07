package com.example.loginotpsamplewithfirebase;

public class User {
    String n_fname, n_lname, n_number, n_address, n_password;

    public void setUser(String n_fname, String n_lname, String n_number, String n_address, String n_password){
        this.n_fname = n_fname;
        this.n_lname = n_lname;
        this.n_number = n_number;
        this.n_address = n_address;
        this.n_password = n_password;
    }

    public String get_f_Name(){
        return n_fname;
    }
    public String get_l_name(){
        return n_lname;
    }
    public String getNumber(){
        return n_number;
    }
    public String getAddress(){
        return n_address;
    }
    public String getPassword(){
        return n_password;
    }
}
