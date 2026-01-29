package com.clarix.employeemanagement.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (null == sessionFactory) {
            sessionFactory = new Configuration()
                    .configure("hibernate-mapping.xml").buildSessionFactory();
        }

        return sessionFactory;
    }
}