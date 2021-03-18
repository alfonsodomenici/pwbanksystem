/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.entity.Account;
import it.tss.banksystem.bank.entity.User;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author tss
 */
@Path("/accounts")
public class AccountsResource {

    @Inject
    AccountStore store;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Account> search() {
        return store.search();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Long id) {
        Account found = store.find(id).orElseThrow(() -> new NotFoundException());
        return Response.ok().entity(found).build();
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Account a) {
        Account saved = store.create(a);
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
    }

    @PATCH
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, JsonObject json) {
        Account account = store.find(id).orElseThrow(() -> new NotFoundException());
        Account updated = store.update(account, json);
        return Response.ok().entity(account).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        Account account = store.find(id).orElseThrow(() -> new NotFoundException());
        store.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    } 
}
