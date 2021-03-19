/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.control;

import it.tss.banksystem.bank.entity.Transaction;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author tss
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class TransactionStore {
    
    @Inject
    AccountStore accountStore;
    
    @PersistenceContext
    EntityManager em;
     
    public List<Transaction> searchByAccount(Long accountId){
        return em.createQuery("select e from Transaction e where e.account.id= :accountId or e.transfer.id= :accountId order by e.createdOn", Transaction.class)
                .setParameter("accountId", accountId)
                .getResultStream()
                .peek(v -> this.createDescr(accountId, v))
                .collect(Collectors.toList());
    }

    public Transaction create(Transaction t){
        switch(t.getType()){
            case DEPOSIT:
               return this.deposit(t);
            case WITHDRAWAL:
                return this.withdrawal(t);
            case TRANSFER:
                return transfer(t);
                default:
                    throw new UnsupportedOperationException("Operazione non supportata...");
        }
    }
    
    private Transaction deposit(Transaction t) {
        accountStore.deposit(t.getAccount().getId(), t.getAmount());
        return em.merge(t);
    }

    private Transaction withdrawal(Transaction t) {
        accountStore.withdrawal(t.getAccount().getId(), t.getAmount());
        return em.merge(t);
    }

    private Transaction transfer(Transaction t) {
        accountStore.transfer(t.getAccount().getId(), t.getTransfer().getId(), t.getAmount());
        return em.merge(t);
    }
    
    private void createDescr(Long accountId, Transaction t){
        if(t.getType()==Transaction.Type.TRANSFER){
            t.setDescr(Objects.equals(t.getTransfer().getId(), accountId) ? "RICEVUTO" : "EFFETTUATO");
        }
    }
}
