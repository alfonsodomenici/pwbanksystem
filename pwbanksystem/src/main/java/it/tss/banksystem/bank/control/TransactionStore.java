/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.control;

import it.tss.banksystem.bank.entity.Transaction;
import java.util.List;
import javax.enterprise.context.RequestScoped;
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
    
    @PersistenceContext
    EntityManager em;
     
    public List<Transaction> searchByAccount(Long accountId){
        return em.createQuery("select e from Transaction e where e.account.id= :accountId order by e.createdOn", Transaction.class)
                .setParameter("accountId", accountId)
                .getResultList();
    }

    public Transaction create(Transaction t) {
        return em.merge(t);
    }
}
