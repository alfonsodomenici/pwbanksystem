/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.security.boundary;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

/**
 *
 * @author alfonso
 */
@Path("/test")
@DenyAll
public class Test {

    @Inject
    System.Logger LOG;

    @Resource
    ManagedExecutorService executor;

    @Inject
    @Claim(standard = Claims.sub)
    private String userId;

    @Inject
    JsonWebToken jwt;

    @Context
    SecurityContext securityContext;

    @PostConstruct
    public void init() {
        LOG.log(System.Logger.Level.INFO, jwt);
    }

    @Counted
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
        LOG.log(System.Logger.Level.INFO, "L'utente ha ruolo USER ? " + securityContext.isUserInRole("USER"));
        LOG.log(System.Logger.Level.INFO, "User ID: " + userId);
        return "all users ok..";
    }

    @GET
    @Path("longSync")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public Response longSync() throws InterruptedException {
        Thread.sleep(10000);
        return Response.ok("long task sync ok...").build();
    }

    @GET
    @Path("longAsync")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public void longAsync(@Suspended AsyncResponse response) throws InterruptedException {
        executor.execute(() -> {
            try {
                Thread.sleep(10000);
                response.resume("Welcome to async world!");
            } catch (InterruptedException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @GET
    @Path("longAsync1")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> longAsync1() throws InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        executor.execute(() -> {
            try {
                Thread.sleep(10000);
                future.complete("long task async ok... ");
            } catch (InterruptedException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return future;
    }

    @GET
    @Path("longAsync2")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<String> longAsync2() throws InterruptedException {
        return CompletableFuture.supplyAsync(this::longTask, executor);
    }

    private String longTask() {
        try {
            Thread.sleep(10000);
            return "long task executed...";
        } catch (InterruptedException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException("long task execution problem...");
        }
    }
}
