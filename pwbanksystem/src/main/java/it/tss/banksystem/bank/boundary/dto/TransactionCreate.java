/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.boundary.dto;

import it.tss.banksystem.bank.boundary.adapters.TransactionTypeAdapter;
import it.tss.banksystem.bank.entity.Transaction;
import java.time.LocalDateTime;
import javax.json.bind.annotation.JsonbTypeAdapter;

/**
 *
 * @author alfonso
 */
public class TransactionCreate {

    @JsonbTypeAdapter(TransactionTypeAdapter.class)
    public Transaction.Type type;
    public double amount;
    public AccountViewLink transfer;

}
