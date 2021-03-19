/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.control.UserStore;
import it.tss.banksystem.bank.entity.User;
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
public class UserResource {

    @Inject
    UserStore store;

    @Context
    ResourceContext resource;

    private Long id;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response find() {
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        return Response.ok().entity(user).build();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response update( JsonObject json) {
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        User updated = store.update(user, json);
        return Response.ok().entity(updated).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete() {
        User user = store.find(id).orElseThrow(() -> new NotFoundException());
        store.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @Path("accounts")
    public UserAccountsResource accounts(){
        UserAccountsResource sub = resource.getResource(UserAccountsResource.class);
        sub.setUserId(id);
        return sub;
    }
    
    /*
    get/set
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
