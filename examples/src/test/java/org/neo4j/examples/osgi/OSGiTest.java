package org.neo4j.examples.osgi;
/**
 * Copyright (c) 2002-2011 "Neo Technology,"
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

import static org.ops4j.pax.exam.CoreOptions.autoWrap;
import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.equinox;
import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.provision;
import static org.ops4j.pax.exam.CoreOptions.repository;
import static org.ops4j.pax.tinybundles.core.TinyBundles.bundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.withBnd;

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


    private static final String NEO4J_OSGI_BUNDLE_VERSION = "1.6.0.BUILD-SNAPSHOT";
    private static final String GERONIMO_JTA_VERSION = "1.1.1";

    @Test
    public void neo4jStartupTest()
        throws Exception
    {
        Player player = new Player().with(
            options(
                autoWrap(),    
                felix(),
                equinox(),
                repository("https://oss.sonatype.org/content/groups/ops4j/"),
                cleanCaches(),
                mavenBundle().groupId( "org.apache.geronimo.specs" ).artifactId( "geronimo-jta_1.1_spec" ).version( GERONIMO_JTA_VERSION ),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-osgi-bundle" ).version( NEO4J_OSGI_BUNDLE_VERSION ),
                provision( bundle()
                        .add (Neo4jActivator.class )
                        .add (MyRelationshipTypes.class )
                        .set( Constants.BUNDLE_ACTIVATOR, Neo4jActivator.class.getName() )
                        .build( withBnd() ) )
            )
        );
        test(player);
    }

    @Test
    public void bundleTest()
        throws Exception
    {
        Player player = new Player().with(
            options(
                autoWrap(), 
               // vmOptions("-Xdebug -Xrunjdwp:transport=dt_socket,address=127.0.0.1:8000"),
                repository("https://oss.sonatype.org/content/groups/ops4j/"),
                felix(),
                equinox(),
                cleanCaches(),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-osgi-bundle" ).version( NEO4J_OSGI_BUNDLE_VERSION ),
                mavenBundle().groupId( "org.apache.geronimo.specs" ).artifactId( "geronimo-jta_1.1_spec" ).version( GERONIMO_JTA_VERSION ),
                mavenBundle().groupId( "org.neo4j.examples.osgi" ).artifactId( "test-bundle" ).version( NEO4J_OSGI_BUNDLE_VERSION )
            )
        );
        test(player);
    }

    private void test(Player player) throws Exception
    {
        player.test( WaitForService.class, GraphDatabaseService.class.getName(), 5000 )
        .test( WaitForService.class, Index.class.getName(), 5000 )
        .test( CountBundles.class,  9)
        .test( BundlesInState.class, Bundle.ACTIVE, Bundle.ACTIVE )
        .play();
    }
}
