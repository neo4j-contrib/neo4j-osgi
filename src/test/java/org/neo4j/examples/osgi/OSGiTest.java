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

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;
import static org.ops4j.pax.exam.CoreOptions.provision;
import static org.ops4j.pax.tinybundles.core.TinyBundles.bundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.withBnd;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.options.extra.AutoWrapOption;
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
                CoreOptions.autoWrap(),    
                CoreOptions.felix(),
                CoreOptions.cleanCaches(),
                mavenBundle().groupId( "org.ops4j.pax.logging" ).artifactId( "pax-logging-service" ).version( "1.6.2" ),
                mavenBundle().groupId( "javax.transaction" ).artifactId( "com.springsource.javax.transaction" ).version( "1.1.0" ),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-kernel" ).version( "1.4-SNAPSHOT" ),
                //TODO: This is not working, the wrap:mvn URL seems to be not supported in the underlying framework
//                wrappedBundle(mavenBundle().groupId( "org.apache.lucene" ).artifactId( "lucene-core" ).version( "3.1.0" )),
//                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-lucene-index" ).version( "1.4-SNAPSHOT" ),
                provision( bundle( withBnd() ) 
                        .add (Neo4jActivator.class )
                        .set( Constants.BUNDLE_ACTIVATOR, Neo4jActivator.class.getName() )
                        .build() )
            )
        )
        .test( WaitForService.class, GraphDatabaseService.class.getName(), 5000 )
        .test( CountBundles.class, 10 )
        .test( BundlesInState.class, Bundle.ACTIVE, Bundle.ACTIVE )
        .play();

    }
}
