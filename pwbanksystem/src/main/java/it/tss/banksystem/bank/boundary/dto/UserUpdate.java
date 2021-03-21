/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary.dto;

/**
 *
 * @author alfonso
 */
public class UserUpdate {

    public String fname;
    public String lname;
    public String pwd;
    public String email;
    public String tel;

    public UserUpdate() {
    }

    public UserUpdate(String fname, String lname, String pwd, String email, String tel) {
        this.fname = fname;
        this.lname = lname;
        this.pwd = pwd;
        this.email = email;
        this.tel = tel;
    }

}
