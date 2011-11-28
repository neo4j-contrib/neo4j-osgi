Neo4j - OSGi components
========================

This is a boilerplate Neo4j bundle consisting of the Neo4j Community distribution and some sample tests using this bundle. For use in production scenarios, consider adjusting the bundle build to your needs, and test thoroughly.

## Build 

First, build the osgi+friendly branch of Neo4j Community:

    git co git@github.com:neo4j/community.git
    cd community
    git branch -t bundle-friendly-classloader origin/bundle-friendly-classloader
    mvn clean install
    cd ..

Then, the Neo4j OSGi project:

    git co git://github.com/neo4j/neo4j-osgi.git
    cd neo4j-osgi
    mvn clean install
  
  