package ru.kusart;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import ru.kusart.dto.Employee;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {
        Configuration cfg = new Configuration().addAnnotatedClass(Employee.class);
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(hibernateProperties_H2())
                .build();
        final Connection connection = serviceRegistry
                .getService(ConnectionProvider.class)
                .getConnection();
        System.out.println(connection.getMetaData().getDatabaseProductName());
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    org.h2.tools.Server.startWebServer(connection);
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
        SessionFactory sf = cfg.buildSessionFactory(serviceRegistry);
        Session s = sf.openSession();
        Transaction tx = s.beginTransaction();
        Employee e1 = Employee.builder().empName("TED").build();
        Employee e2 = Employee.builder().empName("TES SD").build();
        s.save(e1);
        s.save(e2);
        Query query = s.createNativeQuery("select get_nal.nextval from dual");
        Object nextId = query.getSingleResult();
        System.out.println(nextId);
        Query query1 = s.createNativeQuery("INSERT INTO EMPLOYEE values (" + nextId + ", 'TEST')");
        Object obj = query1.executeUpdate();
        tx.commit();
    }

    private static Properties hibernateProperties_H2(){
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb");
        properties.setProperty("hibernate.connection.username", "sa");
        properties.setProperty("hibernate.connection.password", "");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");
        return properties;
    }
}
