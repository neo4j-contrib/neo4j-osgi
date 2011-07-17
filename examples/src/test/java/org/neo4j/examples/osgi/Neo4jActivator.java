/**
 * Copyright (c) 2002-2011 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.examples.osgi;

import java.util.Properties;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Neo4jActivator implements BundleActivator
{

    private static GraphDatabaseService db;
    private ServiceRegistration serviceRegistration;
    private ServiceRegistration indexServiceRegistration;

    @Override
    public void start( BundleContext context ) throws Exception
    {
        System.out.print("Opening database in embedded mode: ");
        db = new EmbeddedGraphDatabase( "graphdb" );
        System.out.println("OK");
        Transaction tx = db.beginTx();
        try {
            System.out.print("Populating it ... ");
            Node firstNode = db.createNode();
            Node secondNode = db.createNode();
            Relationship relationship = firstNode.createRelationshipTo( secondNode, MyRelationshipTypes.KNOWS );

            firstNode.setProperty( "message", "Hello, " );
            secondNode.setProperty( "message", "world!" );
            relationship.setProperty( "message", "brave Neo4j " );
            System.out.println("OK");

            System.out.print( firstNode.getProperty( "message" ) );
            System.out.print( relationship.getProperty( "message" ) );
            System.out.println( secondNode.getProperty( "message" ) );

            tx.success();
        } 
        catch (Exception e) {
            System.out.println("KO: " + e.getMessage());
        } 
        finally {
            tx.finish();
        }

        serviceRegistration = context.registerService( GraphDatabaseService.class.getName(), db, new Properties() );
        System.out.println("registered " + serviceRegistration.getReference());
        indexServiceRegistration = context.registerService( Index.class.getName(), db.index().forNodes( "nodes" ), new Properties() );
        System.out.println("registered " + indexServiceRegistration.getReference());
 
    }

    @Override
    public void stop( BundleContext context ) throws Exception
    {
        System.out.print("Closing database: ");
        serviceRegistration.unregister();
        indexServiceRegistration.unregister();
        db.shutdown();
        System.out.println("OK");

    }

}
