/**
 * Copyright (c) 2002-2012 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.examples.osgi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.ops4j.pax.exam.Inject;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import static org.neo4j.examples.osgi.SDNSetup.*;

/**
 *
 * This is some more lazy test play ground to actually interact with services & bundles from within the container.
 *
 *
 * @author Toni Menzel (toni@okidokiteam.com)
 */
@RunWith( JUnit4TestRunner.class )
@ExamReactorStrategy( AllConfinedStagedReactorFactory.class )
public class SDNTest {


   // @Inject
   // GraphDatabaseService service;

    @Configuration
    public Option[] config()
    {
        return sdnOptions();
    }

    @Test
    public void testServiceAvailability(BundleContext ctx)
        throws InterruptedException
    {
        System.out.println( "Wait for system to settle.. we have time" );
        Thread.sleep( 10000 );
        System.out.println( "Now print the bundles.." );

        for( Bundle b : ctx.getBundles() ) {

            if( b.getState() != 32 ) {
                System.out.println( "Bundle " + b.getBundleId() + " - " + b.getSymbolicName() + " - State: " + b.getState() );

                // try to start it:
                System.out.println( "Hold on.. trying to start bundle " + b.getBundleId() );
                try {
                    b.start();
                    System.out.println( "OK" );
                } catch( BundleException e ) {
                    System.err.println( "Appearingly starting " + b.getBundleId() + " does not work." );
                }
            }
        }
    }

}
