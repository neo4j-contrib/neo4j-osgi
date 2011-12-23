package org.neo4j.examples.osgi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements
		ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private HelloSdn hello;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		hello.test();
	}

}
