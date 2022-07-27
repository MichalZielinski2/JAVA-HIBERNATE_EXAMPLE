package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ServerConnection {

    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;
    public Session session;
    private static ServerConnection instance = new ServerConnection();


    private ServerConnection(){
        registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
        System.out.println("================================================");
        session = sessionFactory.openSession();
    }

    public static ServerConnection getInstance(){
        return instance;
    }

    public static void close(){
        getInstance().session.close();
    }

    public void save (Object toSave){
        session.beginTransaction();
        session.save(toSave);
        session.getTransaction().commit();

    }




}
