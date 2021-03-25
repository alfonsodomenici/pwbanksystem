/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.boundary.dto.AccountCreate;
import it.tss.banksystem.bank.boundary.dto.AccountList;
import it.tss.banksystem.bank.boundary.dto.AccountView;
import it.tss.banksystem.bank.boundary.dto.UserUpdate;
import it.tss.banksystem.bank.boundary.dto.UserViewFull;
import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.control.UserStore;
import it.tss.banksystem.bank.entity.Account;
import it.tss.banksystem.bank.entity.User;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alfonso
 */
@RolesAllowed({"ADMIN", "USER"})
public class UserResource {

    @Inject
    private AccountStore accountStore;

    @Inject
    private UserStore store;

    private Long userId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserViewFull find() {
        UserViewFull user = store.findView(userId).orElseThrow(() -> new NotFoundException());
        return user;
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserViewFull update(UserUpdate u) {
        User user = store.find(userId).orElseThrow(() -> new NotFoundException());
        User updated = store.update(user, u);
        return new UserViewFull(user);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete() {
        User user = store.find(userId).orElseThrow(() -> new NotFoundException());
        store.delete(userId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("accounts")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountList accounts() {
        return accountStore.findByUserView(userId);
    }

    @POST
    @Path("accounts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(AccountCreate a) {
        User user = store.find(userId).orElseThrow(() -> new NotFoundException());
        Account saved = accountStore.create(new Account(a, user));
        return Response.status(Response.Status.CREATED)
                .entity(new AccountView(saved))
                .build();
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
