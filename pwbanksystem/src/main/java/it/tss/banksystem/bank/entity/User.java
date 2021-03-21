/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.entity;

import it.tss.banksystem.bank.boundary.dto.UserCreate;
import it.tss.banksystem.bank.boundary.dto.UserUpdate;
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
    @Column(nullable = false, unique = true)
    private String usr;
    @Column(nullable = false)
    private String pwd;
    private String email;
    private String tel;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private boolean deleted = false;

    public User() {
    }

    public User(UserCreate u) {
        this.fname = u.fname;
        this.lname = u.lname;
        this.usr = u.usr;
        this.pwd = u.pwd;
        this.email = u.email;
        this.tel = u.tel;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFname(UserUpdate u) {
        setFname(u.fname == null ? this.fname : u.fname);
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setLname(UserUpdate u) {
        setLname(u.lname == null ? this.lname : u.lname);
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setPwd(UserUpdate u) {
        setPwd(u.pwd == null ? this.pwd : u.pwd);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmail(UserUpdate u) {
        setEmail(u.email == null ? this.email : u.email);
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setTel(UserUpdate u) {
        setTel(u.tel == null ? this.tel : u.tel);
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
