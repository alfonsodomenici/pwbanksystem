/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.control.TransactionStore;
import it.tss.banksystem.bank.entity.Account;
import it.tss.banksystem.bank.entity.Transaction;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author alfonso
 */
public class TransactionsResource {

    @Inject
    AccountStore accountStore;

    @Inject
    TransactionStore store;
    
    private Long userId;
    private Long accountid;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> transactions(){
        return store.searchByAccount(accountid);
    }

    public Transaction create(Transaction t){
        Account account = accountStore.find(accountid).orElseThrow(() -> new NotFoundException());
        t.setAccount(account);
        return store.create(t);
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

    public Long getAccountid() {
        return accountid;
    }

    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }
    
    
}
