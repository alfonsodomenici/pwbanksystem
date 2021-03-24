/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.security.boundary;

import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author alfonso
 */
@Path("/test")
@DenyAll
public class Test {
    
    @Inject
    JsonWebToken jwt;
    
    @PostConstruct
    public void init(){
        System.out.println(jwt);
    }
    
    @GET
    @Path("all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String testAll(){
        return "all ok..";
    }
    
    @GET
    @Path("admin")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.TEXT_PLAIN)
    public String testAdmin(){
        return "all admin ok..";
    }
    
    @GET
    @Path("user")
    @RolesAllowed({"USER"})
    @Produces(MediaType.TEXT_PLAIN)
    public String testUser(){
        return "all users ok..";
    }
    
}
