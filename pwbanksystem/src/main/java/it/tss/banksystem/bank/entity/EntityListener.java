/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.entity;

import it.tss.banksystem.bank.control.UserStore;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author tss
 */
public class EntityListener {

    @Inject
    UserStore userStore;

    @Inject
    JsonWebToken jwt;

    @PostConstruct
    public void init() {
        System.out.println("init entity listener..");
    }

    @PrePersist
    public void onPrePersist(AbstractEntity e) {
        if (jwt != null) {
            e.setCreatedBy(userStore.find(Long.parseLong(jwt.getSubject())).get());
        }
        e.setCreatedOn(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate(AbstractEntity e) {
        if (jwt != null) {
            e.setModifiedBy(userStore.find(Long.parseLong(jwt.getSubject())).get());
        }
        e.setModifiedOn(LocalDateTime.now());
    }
}
