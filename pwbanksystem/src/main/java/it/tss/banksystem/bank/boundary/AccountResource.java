/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.boundary.dto.AccountUpdate;
import it.tss.banksystem.bank.boundary.dto.AccountView;
import it.tss.banksystem.bank.boundary.dto.TransactionCreate;
import it.tss.banksystem.bank.boundary.dto.TransactionList;
import it.tss.banksystem.bank.boundary.dto.TransactionView;
import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.control.TransactionStore;
import it.tss.banksystem.bank.entity.Account;
import it.tss.banksystem.bank.entity.Transaction;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alfonso
 */
@RolesAllowed({"ADMIN", "USER"})
public class AccountResource {

    @Inject
    private AccountStore store;

    @Inject
    private TransactionStore transactionStore;

    @Context
    private ResourceContext resource;

    private Long accountId;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AccountView find() {
        Account found = store.find(accountId).orElseThrow(() -> new NotFoundException());
        return new AccountView(found);

    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountView update(AccountUpdate a) {
        Account account = store.find(accountId).orElseThrow(() -> new NotFoundException());
        Account updated = store.update(account, a);
        return new AccountView(updated);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete() {
        Account account = store.find(accountId).orElseThrow(() -> new NotFoundException());
        store.delete(accountId);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionList transactions() {
        return transactionStore.searchByAccountView(accountId);
    }

    @POST
    @Path("transactions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(TransactionCreate t) {
        Account account = store.find(accountId).orElseThrow(() -> new NotFoundException());
        Account transfer = t.transfer == null ? null : store.find(t.transfer.id).orElseThrow(() -> new NotFoundException());
        Transaction saved = transactionStore.create(new Transaction(t, account, transfer));
        return Response.status(Response.Status.CREATED).entity(new TransactionView(saved)).build();
    }

    /*
    get/set
     */
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

}
