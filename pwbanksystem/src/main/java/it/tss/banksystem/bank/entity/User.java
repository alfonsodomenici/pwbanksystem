/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.entity;

import java.io.Serializable;
import javax.json.JsonString;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author alfonso
 */
@Entity
@Table(name = "user")
public class User extends AbstractEntity implements Serializable {

    public enum Role {
        ADMIN, USER
    }

    private String fname;
    private String lname;
    @Column(nullable = false,unique = true)
    private String usr;
    @Column(nullable = false)
    private String pwd;
    private String email;
    private String tel;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private boolean deleted = false;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFname(JsonString fname) {
        setFname(fname==null ? this.fname : fname.getString());
    }
    
    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setLname(JsonString lname) {
        setLname(lname==null ? this.lname : lname.getString());
    }
    
    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    @JsonbTransient
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setPwd(JsonString pwd) {
        setPwd(pwd == null ? this.pwd : pwd.getString());
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail(JsonString email) {
        setEmail(email == null ? this.email : email.getString());
    }
    
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setTel(JsonString tel) {
        setTel(tel == null ? this.tel : tel.getString());
    }
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    
}
