package org.neo4j.examples.osgi;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ContextRefreshedListener implements
		ApplicationListener<ContextRefreshedEvent> {

	private HelloSdn helloSdn;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		helloSdn.test();
	}

	public void setHelloSdn(HelloSdn helloSdn) {
		this.helloSdn = helloSdn;
	}
}
