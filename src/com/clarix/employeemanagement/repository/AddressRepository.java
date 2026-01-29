package com.clarix.employeemanagement.repository;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.clarix.employeemanagement.model.Address;
import com.clarix.employeemanagement.util.HibernateUtil;

public class AddressRepository {
    public Address saveAddress(Address address) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {

             Transaction transaction = session.beginTransaction();
             session.saveOrUpdate(address);
             transaction.commit();

             return address;
        }
    }
}