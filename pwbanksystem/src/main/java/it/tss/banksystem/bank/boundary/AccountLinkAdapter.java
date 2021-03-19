/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.control.AccountStore;
import it.tss.banksystem.bank.control.UserStore;
import it.tss.banksystem.bank.entity.Account;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

/**
 *
 * @author tss
 */
public class AccountLinkAdapter implements JsonbAdapter<Account, JsonObject> {

    @Inject
    AccountStore store;
    
    @Override
    public JsonObject adaptToJson(Account u) throws Exception {
        return Json.createObjectBuilder()
                .add("id", u.getId())
                .build();
    }

    @Override
    public Account adaptFromJson(JsonObject json) throws Exception {
        return store.find(json.getJsonNumber("id").longValue()).get();
    }
    
}
