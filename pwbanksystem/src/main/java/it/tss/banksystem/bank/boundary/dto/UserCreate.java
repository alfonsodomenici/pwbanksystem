/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary.dto;

import javax.validation.constraints.NotEmpty;

/**
 *
 * @author alfonso
 */
public class UserCreate {

    
    public String fname;
    @NotEmpty
    public String lname;
    @NotEmpty
    public String usr;
    @NotEmpty
    public String pwd;
    public String email;
    public String tel;

}
