/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.control;

import it.tss.banksystem.bank.entity.Account;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author alfonso
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class AccountStore {

    @PersistenceContext
    private EntityManager em;

    public Optional<Account> find(Long id) {
        Account found = em.find(Account.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public List<Account> search() {
        return em.createQuery("select e from Account e where e.deleted=false order by e.user.usr", Account.class)
                .getResultList();
    }

    public List<Account> findByUser(Long id) {
        return em.createQuery("select e from Account e where e.user.id= :user_id", Account.class)
                .setParameter("user_id", id)
                .getResultList();
    }

    public Account create(Account account) {
        return em.merge(account);
    }

    /**
     * Update only overDraft property
     *
     * @param account
     * @param json
     * @return
     */
    public Account update(Account account, JsonObject json) {
        Account result = account;
        if (json.getJsonNumber("overDraft") != null) {
            account.setOverDraft(json.getJsonNumber("overDraft").longValue());
            result = em.merge(account);
        }
        return result;
    }
    
    public void delete(Long id) {
        Account found = em.find(Account.class, id);
        found.setDeleted(true);
        em.merge(found);
    }
}
