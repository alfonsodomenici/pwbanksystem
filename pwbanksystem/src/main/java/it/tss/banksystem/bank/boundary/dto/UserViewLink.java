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
public class UserViewLink {
    public Long id;
    public String usr;

    public UserViewLink() {
    }
    
    public UserViewLink(User u) {
        this.id = u.getId();
        this.usr = u.getUsr();
    }
}
