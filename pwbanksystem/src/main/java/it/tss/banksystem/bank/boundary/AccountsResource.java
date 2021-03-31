/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.boundary.dto.AccountList;
import it.tss.banksystem.bank.boundary.dto.AccountView;
import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.entity.Account;
import it.tss.banksystem.bank.entity.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

/**
 *
 * @author tss
 */
@SecurityRequirement(name = "jwt")
@DenyAll
@Path("/accounts")
public class AccountsResource {

    @Context
    private ResourceContext resource;

    @Context
    private UriInfo uriInfo;

    @Context
    SecurityContext securityCtx;

    @Inject
    JsonWebToken jwt;
    @Inject
    private AccountStore store;

    @PostConstruct
    public void init() {
        System.out.println(uriInfo.getPath());
        System.out.println(uriInfo.getBaseUri());
        System.out.println(uriInfo.getAbsolutePath());
    }

    @RolesAllowed({"ADMIN"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AccountList search(@QueryParam("start") int start, @QueryParam("maxResult") int maxResult, @QueryParam("minBalance") Double minBalance, @QueryParam("maxBalance") Double maxBalance) {
        return store.searchView(minBalance, maxBalance, start, maxResult);
    }

    @RolesAllowed({"ADMIN", "USER"})
    @Path("{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountResource find(@PathParam("accountId") Long id) {
        Account account = store.find(id).orElseThrow(() -> new NotFoundException());
        boolean isUserRole = securityCtx.isUserInRole(User.Role.USER.name());
        if (isUserRole && (jwt == null || jwt.getSubject() == null || Long.parseLong(jwt.getSubject()) != account.getUser().getId())) {
            throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity("Access forbidden: role not allowed").build());
        }
        AccountResource sub = resource.getResource(AccountResource.class);
        sub.setAccountId(id);
        return sub;
    }

}
