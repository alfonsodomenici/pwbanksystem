/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.control;

import it.tss.banksystem.bank.boundary.dto.AccountCreate;
import it.tss.banksystem.bank.boundary.dto.UserViewFull;
import it.tss.banksystem.bank.boundary.dto.UserList;
import it.tss.banksystem.bank.boundary.dto.UserUpdate;
import it.tss.banksystem.bank.boundary.dto.UserViewLink;
import it.tss.banksystem.bank.entity.Account;
import it.tss.banksystem.bank.entity.User;
import it.tss.banksystem.security.boundary.AuthenticationFailed;
import it.tss.banksystem.security.boundary.AuthenticationSuccess;
import it.tss.banksystem.security.control.SecurityEncoding;
import it.tss.banksystem.slowdown.Slowerer;
import it.tss.banksystem.trace.Logged;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author alfonso
 */
@Logged
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class UserStore {

    @Inject
    System.Logger log;

    @PersistenceContext
    private EntityManager em;

    @Inject
    AccountStore accountStore;

    @Inject
    @ConfigProperty(name = "maxResult", defaultValue = "10")
    int maxResult;

    @Inject
    @AuthenticationSuccess
    private Event<String> authSuccess;

    @Inject
    @AuthenticationFailed
    private Event<String> authFailed;

    public Optional<User> find(Long id) {
        log.log(System.Logger.Level.INFO, "find user " + id);
        User found = em.find(User.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    private TypedQuery<User> searchQuery(boolean deleted, String lname) {
        return em.createQuery("select e from User e where e.deleted= :deleted and e.lname like :lname order by e.usr ", User.class)
                .setParameter("deleted", deleted)
                .setParameter("lname", lname == null ? "%" : "%" + lname + "%");
    }

    
    public List<User> searchAll() {
        return searchQuery(false, null).getResultList();
    }

    public List<User> search(int start, int maxResult, String lname) {

        return searchQuery(false, lname)
                .setFirstResult(start)
                .setMaxResults(maxResult == 0 ? this.maxResult : maxResult)
                .getResultList();
    }

    public long searchCount(String lname) {
        return em.createQuery("select COUNT(e) from User e where e.deleted=false and e.lname like :lname", Long.class)
                .setParameter("lname", lname == null ? "%" : "%" + lname + "%")
                .getSingleResult();
    }

    public Optional<UserViewFull> findView(Long id) {
        return find(id).map(UserViewFull::new);
    }

    public UserList searchView(int start, int maxResult, String lname) {
        UserList result = new UserList();
        result.total = searchCount(lname);
        result.data = search(start, maxResult, lname).stream().map(UserViewLink::new).collect(Collectors.toList());
        return result;
    }

    @Slowerer
    public UserList searchFullView(int start, int maxResult, String lname) {
        UserList result = new UserList();
        result.total = searchCount(lname);
        result.data = search(start, maxResult, lname).stream().map(UserViewFull::new).collect(Collectors.toList());
        return result;
    }

    public User create(User u) {
        u.setPwd(SecurityEncoding.shaHash(u.getPwd()));
        User saved = em.merge(u);
        Account account = new Account(new AccountCreate(), saved);
        accountStore.create(account);
        return saved;
    }

    public User update(User user, UserUpdate u) {
        user.setFname(u);
        user.setLname(u);
        user.setEmail(u);
        user.setTel(u);
        user.setPwd(u);
        return em.merge(user);
    }

    public void delete(Long id) {
        User found = em.find(User.class, id);
        found.setDeleted(true);
        em.merge(found);
        accountStore.findByUser(id).stream().map(Account::getId).forEach(accountStore::delete);
    }

    public Optional<User> findByUserAndPwd(String usr, String pwd) {
        try {
            User found = em.createNamedQuery(User.LOGIN, User.class)
                    .setParameter("usr", usr)
                    .setParameter("pwd", SecurityEncoding.shaHash(pwd))
                    .getSingleResult();
            authSuccess.fire("Login ok...");
            return Optional.of(found);
        } catch (NoResultException ex) {
            authFailed.fire("Login failed for user :" + usr);
            return Optional.empty();
        }
    }

}
