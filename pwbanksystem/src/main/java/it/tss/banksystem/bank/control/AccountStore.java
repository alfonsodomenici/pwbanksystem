/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.tss.banksystem.bank.control;

import it.tss.banksystem.BankException;
import it.tss.banksystem.bank.boundary.dto.AccountList;
import it.tss.banksystem.bank.boundary.dto.AccountUpdate;
import it.tss.banksystem.bank.boundary.dto.AccountView;
import it.tss.banksystem.bank.boundary.dto.AccountViewSearch;
import it.tss.banksystem.bank.entity.Account;
import it.tss.banksystem.trace.Logged;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author alfonso
 */
@Logged
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class AccountStore {

    //private System.Logger log = System.getLogger(AccountStore.class.getName());
    
    @Inject
    System.Logger log ;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    @ConfigProperty(name = "maxResult", defaultValue = "10")
    int maxResult;

    public Optional<Account> find(Long id) {
        log.log(System.Logger.Level.INFO, "find account " + id);
        Account found = em.find(Account.class, id);
        return found == null ? Optional.empty() : Optional.of(found);
    }

    public List<Account> search(Double min, Double max, int start, int maxResult) {
        /*
        return em.createQuery("select e from Account e where e.deleted=false and e.balance>= :min and e.balance<= :max order by e.user.usr", Account.class)
        .setParameter("min", min==null ? Double.MIN_VALUE : min)
        .setParameter("max", max == null ? Double.MAX_VALUE : max)
        .getResultList();
         */
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);
        query.select(root)
                .where(searchPredicate(cb, root, min, max));
        return em.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(maxResult== 0 ? this.maxResult : maxResult)
                .getResultList();
    }

    public long searchCount(Double min, Double max) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root root = query.from(Account.class);
        query.select(cb.count(root)).where(searchPredicate(cb, root, min, max));
        return em.createQuery(query).getSingleResult();
    }

    public Predicate searchPredicate(CriteriaBuilder cb, Root<Account> root, Double min, Double max) {
        Predicate result = cb.conjunction();
        result = cb.and(cb.equal(root.get("deleted"), false));
        if (min != null) {
            result = cb.and(result, cb.greaterThanOrEqualTo(root.get("balance"), min));
        }
        if (max != null) {
            result = cb.and(result, cb.lessThanOrEqualTo(root.get("balance"), max));
        }
        return result;
    }

    public List<Account> findByUser(Long userId) {
        return em.createQuery("select e from Account e where e.user.id= :userId", Account.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Optional<AccountView> findView(Long id) {
        return find(id).map(AccountView::new);
    }

    public AccountList searchView(Double min, Double max, int start, int maxResult) {
        AccountList result = new AccountList();
        result.data = search(min, max, start, maxResult).stream().map(AccountViewSearch::new).collect(Collectors.toList());
        result.total = result.data.size();
        return result;
    }

    public AccountList findByUserView(Long userId) {
        AccountList result = new AccountList();
        result.data = findByUser(userId).stream().map(AccountView::new).collect(Collectors.toList());
        result.total = result.data.size();
        return result;
    }

    public Account create(Account account) {
        return em.merge(account);
    }

    /**
     * Update only overDraft property
     *
     * @param account
     * @param a
     * @return
     */
    public Account update(Account account, AccountUpdate a) {
        account.setOverDraft(a.overDraft);
        return em.merge(account);
    }

    public void delete(Long id) {
        Account found = em.find(Account.class, id);
        found.setDeleted(true);
        em.merge(found);
    }

    public double totalBalance() {
        return em.createNamedQuery(Account.ALL, Account.class)
                .getResultStream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public double avgBalance() {
        return em.createNamedQuery(Account.ALL, Account.class)
                .getResultStream()
                .mapToDouble(Account::getBalance)
                .average().getAsDouble();
    }
    
    public void deposit(Long accountId, Double amount) {
        Account account = find(accountId).orElseThrow(() -> new BankException("Account inesistente"));
        account.setBalance(account.getBalance() + amount);
        em.merge(account);
    }

    public void withdrawal(Long accountId, Double amount) {
        Account account = find(accountId).orElseThrow(() -> new BankException("Account inesistente"));
        checkWithdrawal(account, amount);
        account.setBalance(account.getBalance() - amount);
        em.merge(account);
    }

    public void transfer(Long accountId, Long transferId, Double amount) {
        Account account = find(accountId).orElseThrow(() -> new BankException("Account inesistente"));
        checkWithdrawal(account, amount);
        Account transfer = find(transferId).orElseThrow(() -> new BankException("Transfer Account inesistente"));
        account.setBalance(account.getBalance() - amount);
        transfer.setBalance(transfer.getBalance() + amount);
        em.merge(account);
        em.merge(transfer);
    }

    private void checkWithdrawal(Account account, Double amount) {
        if (account.getBalance() + account.getOverDraft() < amount) {
            throw new BankException("Saldo insufficiente");
        }
    }
}
