package com.simplilearn.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static SessionFactory factory;
	
	public static SessionFactory buildSessionFactory() {
		
		// 1. Load Configuration
    	StandardServiceRegistry  sService = new StandardServiceRegistryBuilder()
    			.configure("hibernate.annotation.cfg.xml").build();
    	Metadata meta = new MetadataSources(sService).getMetadataBuilder().build();
    	
    	// 2. Create Session factory
    	factory = meta.getSessionFactoryBuilder().build();
    	
    	return factory;
	}
	
}
