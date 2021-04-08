/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.boundary.dto.UserCreate;
import it.tss.banksystem.bank.boundary.dto.UserList;
import it.tss.banksystem.bank.boundary.dto.UserViewFull;
import it.tss.banksystem.bank.control.UserStore;
import it.tss.banksystem.bank.entity.User;
import javax.annotation.PostConstruct;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
 * @author alfonso
 */
@DenyAll
@Path("/users")
public class UsersResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserStore store;

    @Context
    private ResourceContext resource;

    @Context
    SecurityContext securityCtx;

    @Inject
    JsonWebToken jwt;

    @PostConstruct
    public void init() {
        System.out.println(uriInfo.getPath());
        System.out.println(uriInfo.getBaseUri());
        System.out.println(uriInfo.getAbsolutePath());
        System.out.println();
    }

    @SecurityRequirement(name = "jwt")
    @RolesAllowed({"ADMIN"})
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserList search(@QueryParam("start") int start, @QueryParam("maxResult") int maxResult, @QueryParam("lname") String lname) {
        return store.searchFullView(start, maxResult, lname);
    }

    @SecurityRequirement(name = "jwt")
    @RolesAllowed({"ADMIN", "USER"})
    @Path("{userId}")
    public UserResource find(@PathParam("userId") Long id) {
        boolean isUserRole = securityCtx.isUserInRole(User.Role.USER.name());
        if (isUserRole && (jwt == null || jwt.getSubject() == null || Long.parseLong(jwt.getSubject()) != id)) {
            throw new ForbiddenException(Response.status(Response.Status.FORBIDDEN).entity("Access forbidden: role not allowed").build());
        }
        UserResource sub = resource.getResource(UserResource.class);
        sub.setUserId(id);
        return sub;
    }

    @PermitAll
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid UserCreate u) {
        User saved = store.create(new User(u));
        return Response.status(Response.Status.CREATED)
                .entity(new UserViewFull(saved))
                .build();
    }

}
