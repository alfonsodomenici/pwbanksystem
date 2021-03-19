/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.control.UserStore;
import it.tss.banksystem.bank.entity.Account;
import it.tss.banksystem.bank.entity.User;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alfonso
 */
public class UserAccountsResource {

    @Inject
    AccountStore store;

    @Inject
    UserStore userStore;

    @Context
    ResourceContext resource;

    private Long userId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> accounts() {
        return store.findByUser(userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Account a) {
        User user = userStore.find(userId).orElseThrow(() -> new NotFoundException());
        a.setUser(user);
        Account saved = store.create(a);
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }

    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountResource find(@PathParam("id") Long id) {
        AccountResource sub = resource.getResource(AccountResource.class);
        sub.setUserId(userId);
        sub.setId(id);
        return sub;
    }

    /*
    get/set
     */
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
