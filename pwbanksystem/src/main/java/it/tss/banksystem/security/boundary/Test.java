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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

/**
 *
 * @author alfonso
 */
@Path("/test")
@DenyAll
public class Test {

    @Inject
    @Claim(standard = Claims.sub)
    private String userId;

    @Inject
    JsonWebToken jwt;

    @Context
    SecurityContext securityContext;

    @PostConstruct
    public void init() {
        System.out.println(jwt);
    }

    @GET
    @Path("all")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String testAll() {
        return "all ok..";
    }

    @SecurityRequirement(name = "jwt")
    @GET
    @Path("admin")
    @RolesAllowed({"ADMIN"})
    @Produces(MediaType.TEXT_PLAIN)
    public String testAdmin() {
        return "all admin ok..";
    }

    @SecurityRequirement(name = "jwt")
    @GET
    @Path("user")
    @RolesAllowed({"USER", "ADMIN"})
    @Produces(MediaType.TEXT_PLAIN)
    public String testUser() {
        System.out.println("L'utente ha ruolo USER ? " + securityContext.isUserInRole("USER"));
        System.out.println("User ID: " + userId);
        return "all users ok..";
    }

}
