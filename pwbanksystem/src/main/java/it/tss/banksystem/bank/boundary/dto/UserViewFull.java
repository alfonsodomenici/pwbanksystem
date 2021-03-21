/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary.dto;

import it.tss.banksystem.bank.entity.User;

/**
 *
 * @author alfonso
 */
public class UserViewFull extends UserViewLink {

    public String fname;
    public String lname;
    public String email;
    public String tel;

    public UserViewFull() {
    }

    public UserViewFull(User u) {
        super(u);
        this.fname = u.getFname();
        this.lname = u.getLname();
        this.email = u.getEmail();
        this.tel = u.getTel();
    }

}
