/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.control.UserStore;
import java.math.BigDecimal;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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
    @Path("/stats")
    public JsonObject stats() {
        long numUsers = userStore.searchCount(null);
        double totalDeposit = accountStore.totalDeposit();
        return Json.createObjectBuilder()
                .add("users", numUsers)
                .add("deposits", totalDeposit)
                .build();
    }

}
