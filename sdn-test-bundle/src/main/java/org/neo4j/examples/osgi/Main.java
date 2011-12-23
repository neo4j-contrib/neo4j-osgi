package org.neo4j.examples.osgi;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Dummy main class to test HelloSdn.
 */
public class Main {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
    	        new String[] {"/META-INF/spring/applicationContext.xml"});
		appContext.registerShutdownHook();

		// ContextRefreshedListener will run HelloSdn.test()
	}
}
