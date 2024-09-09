package com.mysite.banking.dao.impl;

import com.mysite.banking.dao.ATMStockDao;
import com.mysite.banking.model.ATMStock;
import com.mysite.banking.service.DatabaseManager;
import com.mysite.banking.service.exception.UpdateException;
import com.mysite.banking.service.impl.DatabaseManagerImpl;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ATMStockDaoImpl implements ATMStockDao {

    private DatabaseManager databaseManager;

    private static final ATMStockDaoImpl INSTANCE;

    public static ATMStockDaoImpl getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new ATMStockDaoImpl();
    }

    private ATMStockDaoImpl(){
        databaseManager = DatabaseManagerImpl.getInstance();
    }

    @Override
    public Long save(ATMStock atmStock) {
        try(Session session = databaseManager.getSession()){
            session.beginTransaction();
            session.save(atmStock);
            session.getTransaction().commit();
            return atmStock.getId();
        }
    }

    @Override
    public void update(ATMStock atmStock) {
        try(Session session = databaseManager.getSession()){
            session.beginTransaction();
            session.update(atmStock);
            session.getTransaction().commit();
        } catch (Exception ex) {
            throw new UpdateException("Update exception, Please retry.", ex);
        }
    }

    @Override
    public List<ATMStock> getAll() {
        try(Session session = databaseManager.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ATMStock> criteriaQuery = criteriaBuilder.createQuery(ATMStock.class);
            Root<ATMStock> root = criteriaQuery.from(ATMStock.class);
            criteriaQuery.select(root);

            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("denomination")));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<ATMStock> getByDenomination(int denomination) {
        try(Session session = databaseManager.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ATMStock> criteriaQuery = criteriaBuilder.createQuery(ATMStock.class);
            Root<ATMStock> root = criteriaQuery.from(ATMStock.class);

            Predicate denominationPredicate = criteriaBuilder.equal(root.get("denomination"), denomination);

            Predicate finalPredicate = criteriaBuilder.and(
                    denominationPredicate
            );

            criteriaQuery.select(root).where(finalPredicate);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
