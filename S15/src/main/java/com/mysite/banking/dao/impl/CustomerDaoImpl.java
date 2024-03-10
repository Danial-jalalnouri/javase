package com.mysite.banking.dao.impl;

import com.mysite.banking.dao.CustomerDao;
import com.mysite.banking.model.Customer;
import com.mysite.banking.model.RealCustomer;
import com.mysite.banking.service.DatabaseManager;
import com.mysite.banking.service.impl.DatabaseManagerImpl;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    private DatabaseManager databaseManager;

    private static final CustomerDaoImpl INSTANCE;

    public static CustomerDaoImpl getInstance(){
        return INSTANCE;
    }

    static {
        INSTANCE = new CustomerDaoImpl();
    }

    private CustomerDaoImpl(){
        databaseManager = DatabaseManagerImpl.getInstance();
    }

    @Override
    public Integer save(Customer customer) {
        try(Session session = databaseManager.getSession()){
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
            return customer.getId();
        }
    }

    @Override
    public void delete(Customer customer) {
        customer.setDeleted(true);
        update(customer);
    }

    @Override
    public void update(Customer customer) {
        try(Session session = databaseManager.getSession()){
            session.beginTransaction();
            session.update(customer);
            session.getTransaction().commit();
        }
    }

    @Override
    public Customer getById(Integer id) {
        try(Session session = databaseManager.getSession()){
            return session.get(Customer.class, id);
        }
    }

    @Override
    public List<Customer> getByStatus(boolean deleted) {
        try(Session session = databaseManager.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("deleted"), deleted));
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public List<Customer> getByFamily(String family) {
        try(Session session = databaseManager.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<RealCustomer> root = criteriaQuery.from(RealCustomer.class);

            Predicate familyPredicate = criteriaBuilder.equal(root.get("family"), family);
            Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"), false);

            Predicate finalPredicate = criteriaBuilder.and(
                    familyPredicate,
                    deletedPredicate
            );

            criteriaQuery.select(root).where(finalPredicate);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public Customer getFirstByEmail(String email) {
        try(Session session = databaseManager.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);

            Predicate familyPredicate = criteriaBuilder.equal(root.get("email"), email);
            Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"), false);

            Predicate finalPredicate = criteriaBuilder.and(
                    familyPredicate,
                    deletedPredicate
            );

            criteriaQuery.select(root).where(finalPredicate);

            TypedQuery<Customer> typedQuery = session.createQuery(criteriaQuery);
            typedQuery.setMaxResults(1);
            List<Customer> resultList = typedQuery.getResultList();

            return resultList.isEmpty() ? null : resultList.get(0);
        }
    }

    @Override
    public List<Customer> getByName(String name) {
        try(Session session = databaseManager.getSession()){
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);

            Predicate familyPredicate = criteriaBuilder.equal(root.get("name"), name);
            Predicate deletedPredicate = criteriaBuilder.equal(root.get("deleted"), false);

            Predicate finalPredicate = criteriaBuilder.and(
                    familyPredicate,
                    deletedPredicate
            );

            criteriaQuery.select(root).where(finalPredicate);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
