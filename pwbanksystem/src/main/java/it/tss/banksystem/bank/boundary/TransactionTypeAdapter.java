/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary;

import it.tss.banksystem.bank.entity.Transaction;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

/**
 *
 * @author alfonso
 */
public class TransactionTypeAdapter implements JsonbAdapter<Transaction.Type, JsonObject> {

    @Override
    public JsonObject adaptToJson(Transaction.Type t) throws Exception {
        return Json.createObjectBuilder()
                .add("id", t.name() )
                .add("denominazione", t.name())
                .build();
    }

    @Override
    public Transaction.Type adaptFromJson(JsonObject json) throws Exception {
        return Transaction.Type.valueOf(json.getString("id"));
    }
    
}
