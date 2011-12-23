package org.neo4j.examples.osgi.repositories;

import org.neo4j.examples.osgi.model.Person;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface PersonRepository extends GraphRepository<Person> {

}
