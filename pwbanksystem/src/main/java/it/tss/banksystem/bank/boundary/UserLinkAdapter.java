/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.control.UserStore;
import it.tss.banksystem.bank.entity.User;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

/**
 *
 * @author tss
 */
public class UserLinkAdapter implements JsonbAdapter<User, JsonObject> {

    @Inject
    UserStore store;
    
    @Override
    public JsonObject adaptToJson(User u) throws Exception {
        return Json.createObjectBuilder()
                .add("id", u.getId())
                .build();
    }

    @Override
    public User adaptFromJson(JsonObject json) throws Exception {
        return store.find(json.getJsonNumber("id").longValue()).get();
    }
    
}
