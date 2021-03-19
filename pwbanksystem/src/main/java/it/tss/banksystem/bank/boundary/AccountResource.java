/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.entity.Account;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
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
public class AccountResource {

    @Inject
    AccountStore store;

    @Context
    ResourceContext resource;

    private Long userId;
    private Long id;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response find() {
        Account found = store.find(id).orElseThrow(() -> new NotFoundException());
        return Response.ok().entity(found).build();

    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(JsonObject json) {
        Account account = store.find(id).orElseThrow(() -> new NotFoundException());
        Account updated = store.update(account, json);
        return Response.ok().entity(account).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete() {
        Account account = store.find(id).orElseThrow(() -> new NotFoundException());
        store.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Path("transactions")
    public TransactionsResource transactions() {
        TransactionsResource sub = resource.getResource(TransactionsResource.class);
        sub.setUserId(userId);
        sub.setAccountid(id);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
