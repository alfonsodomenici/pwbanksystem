/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.control;

import it.tss.banksystem.bank.entity.Account;
import it.tss.banksystem.bank.entity.User;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author alfonso
 */
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class UserStore {

    @PersistenceContext
    private EntityManager em;

    @Inject
    AccountStore accountStore;

    @Inject
    @ConfigProperty(name = "maxResult", defaultValue = "10")
    int maxResult;
    
    public Optional<User> find(Long id) {
        User found = em.find(User.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public List<User> search() {
        return search(0, maxResult);
    } 
    
    public List<User> search(int start, int maxResult) {
        return em.createQuery("select e from User e where e.deleted=false order by e.usr ", User.class)
                .setFirstResult(start)
                .setMaxResults(maxResult)
                .getResultList();
    }

    public User create(User u) {
        User saved = em.merge(u);
        Account account = new Account(0d, 0l, saved);
        accountStore.create(account);
        return saved;
    }

    public User update(User user, JsonObject json) {
        user.setFname(json.getJsonString("fname"));
        user.setLname(json.getJsonString("lname"));
        user.setEmail(json.getJsonString("email"));
        user.setTel(json.getJsonString("tel"));
        user.setPwd(json.getJsonString("pwd"));
        return em.merge(user);
    }

    public void delete(Long id) {
        User found = em.find(User.class, id);
        found.setDeleted(true);
        em.merge(found);
        accountStore.findByUser(id).stream().map(Account::getId).forEach(accountStore::delete);
    }

}
