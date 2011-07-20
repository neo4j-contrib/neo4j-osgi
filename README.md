Neo4j - OSGi components
========================

This is a boilerplate Neo4j bundle consisting of the Neo4j Community distribution and some sample tests using this bundle. For use in production scenarios, consider adjusting the bundle build to your needs, and test thoroughly.

## Build

In order to execute the tests, you right now need to build Pax Exam and Pax Tinybundles as there are SNAPSHOT dependencies to them:

  git co git://github.com/neo4j/neo4j-osgi.git
  git co git@github.com:ops4j/org.ops4j.pax.exam2.git
  git co git://github.com/ops4j/org.ops4j.pax.tinybundles.git
  cd org.ops4j.pax.exam2
  mvn clean install
  cd ../org.ops4j.pax.tinybundles
  mvn clean install
  cd ../neo4j-osgi
  mvn clean install
  
  