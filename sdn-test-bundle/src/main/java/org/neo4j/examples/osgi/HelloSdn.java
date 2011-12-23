package org.neo4j.examples.osgi;

import org.neo4j.examples.osgi.model.Movie;
import org.neo4j.examples.osgi.model.Person;
import org.neo4j.examples.osgi.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.node.Neo4jHelper;
import org.springframework.stereotype.Component;

@Component
public class HelloSdn {
	
	private static final Logger LOG = LoggerFactory.getLogger(HelloSdn.class);
	
	@Autowired
	private Neo4jTemplate neo4j;
	
	@Autowired
	private PersonRepository personRepository;
	
	public void testPersons(Person a, Person b) {
    	if (!a.equals(b)) {
    		throw new RuntimeException("error comparing sdn entities");
    	}
    	if (!a.getName().equals(b.getName())) {
    		throw new RuntimeException("error comparing name of sdn person entity");
    	}
	}
	
	public void test() {
		LOG.info("Starting tests...");
		
		LOG.info("Cleaning DB");
    	Neo4jHelper.cleanDb(neo4j);

    	LOG.info("Creating entities");
    	Person james = new Person("james");
    	Movie foo = new Movie("foo");

    	james.addToFavorites(foo);
    	LOG.info("Persisting entities");
    	james.persist();
    	
    	LOG.info("Reading entities");
    	Person p = personRepository.findOne(james.getNodeId());
    	testPersons(p, james);
    	
    	Person p2 = personRepository.findByPropertyValue("name", "james");
    	testPersons(p2, james);
    	
    	LOG.info("Tests finished");
	}
}
