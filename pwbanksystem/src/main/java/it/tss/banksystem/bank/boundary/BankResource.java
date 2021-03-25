/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.boundary.dto.BankViewStats;
import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.control.UserStore;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author alfonso
 */
@RolesAllowed({"ADMIN"})
@Path("/bank")
public class BankResource {

    @Inject
    private UserStore userStore;

    @Inject
    private AccountStore accountStore;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public BankViewStats stats() {
        return new BankViewStats(userStore.searchCount(null), accountStore.totalBalance(), accountStore.avgBalance());
    }

}
