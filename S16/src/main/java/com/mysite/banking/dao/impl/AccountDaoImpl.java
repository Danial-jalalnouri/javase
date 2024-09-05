package com.mysite.banking.dao.impl;

import com.mysite.banking.dao.AccountDao;
import com.mysite.banking.model.Account;
import com.mysite.banking.service.DatabaseManager;
import com.mysite.banking.service.exception.UpdateException;
import com.mysite.banking.service.impl.DatabaseManagerImpl;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class AccountDaoImpl  implements AccountDao {

    private DatabaseManager databaseManager;

    private static final AccountDaoImpl INSTANCE;

    public static AccountDaoImpl getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new AccountDaoImpl();
    }

    private AccountDaoImpl(){
        databaseManager = DatabaseManagerImpl.getInstance();
    }

    @Override
    public Integer save(Account account) {
        try(Session session = databaseManager.getSession()){
            session.beginTransaction();
            session.save(account);
            session.getTransaction().commit();
            return account.getId();
        }
    }

    @Override
    public void delete(Account account) {
        account.setDeleted(true);
        update(account);
    }

    @Override
    public void update(Account account) {
        try(Session session = databaseManager.getSession()){
            session.beginTransaction();
            session.update(account);
            session.getTransaction().commit();
        } catch (Exception ex) {
            throw new UpdateException("Update exception, Please retry.", ex);
        }
    }

    @Override
    public Account getById(Integer id) {
        try(Session session = databaseManager.getSession()){
            return session.get(Account.class, id);
        }
    }

    @Override
    public List<Account> getByStatus(Boolean deleted) {
        try(Session session = databaseManager.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
            Root<Account> root = criteriaQuery.from(Account.class);
            criteriaQuery.select(root);
            if(deleted != null)
                criteriaQuery.where(criteriaBuilder.equal(root.get("deleted"), deleted));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Account> getByCustomerId(Integer customerId) {
        try(Session session = databaseManager.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
            Root<Account> root = criteriaQuery.from(Account.class);

            Predicate customerIdPredicate = criteriaBuilder.equal(root.get("customerId"), customerId);
            Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"), false);

            Predicate finalPredicate = criteriaBuilder.and(
                    customerIdPredicate,
                    deletedPredicate
            );

            criteriaQuery.select(root).where(finalPredicate);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Account> getAll() {
        return getByStatus(null);
    }
}
