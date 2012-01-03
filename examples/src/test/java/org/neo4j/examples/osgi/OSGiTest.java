package org.neo4j.examples.osgi;
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

import static org.ops4j.pax.exam.CoreOptions.autoWrap;
import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.provision;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.index.Index;
import org.ops4j.pax.exam.player.Player;
import org.ops4j.pax.exam.testforge.BundlesInState;
import org.ops4j.pax.exam.testforge.CountBundles;
import org.ops4j.pax.exam.testforge.WaitForService;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

public class OSGiTest {


    @Test
    public void neo4jStartupTest()
        throws Exception
    {
        new Player().with(
            options(
                autoWrap(),    
                felix(),
                cleanCaches(),
                mavenBundle().groupId( "org.ops4j.pax.logging" ).artifactId( "pax-logging-service" ).version( "1.6.2" ),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-osgi-bundle" ).version( "0.1-SNAPSHOT" ),
                provision( bundle()
                        .add (Neo4jActivator.class )
                        .add (MyRelationshipTypes.class )
                        .set( Constants.BUNDLE_ACTIVATOR, Neo4jActivator.class.getName() )
                        .build( withBnd() ) )
            )
        )
        .test( WaitForService.class, GraphDatabaseService.class.getName(), 5000 )
        .test( WaitForService.class, Index.class.getName(), 5000 )
        .test( CountBundles.class,  11)
        .test( BundlesInState.class, Bundle.ACTIVE, Bundle.ACTIVE )
        .play();

    }
    
    @Test
    public void bundleStartupTest()
        throws Exception
    {
        new Player().with(
            options(
                autoWrap(),    
                felix(),
                cleanCaches(),
                mavenBundle().groupId( "org.ops4j.pax.logging" ).artifactId( "pax-logging-service" ).version( "1.6.2" ),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-osgi-bundle" ).version( "0.1-SNAPSHOT" ),
                mavenBundle().groupId( "org.neo4j.examples.osgi" ).artifactId( "test-bundle" ).version( "0.1-SNAPSHOT" )
            )
        )
        .test( WaitForService.class, GraphDatabaseService.class.getName(), 5000 )
        .test( WaitForService.class, Index.class.getName(), 5000 )
        .test( CountBundles.class,  11)
        .test( BundlesInState.class, Bundle.ACTIVE, Bundle.ACTIVE )
        .play();

    }

}
