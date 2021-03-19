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
public class TransactionTypeAdapter implements JsonbAdapter<Transaction.Type, String> {

    @Override
    public String adaptToJson(Transaction.Type t) throws Exception {
        return t.name();
    }

    @Override
    public Transaction.Type adaptFromJson(String json) throws Exception {
        return Transaction.Type.valueOf(json);
    }
    
}
