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

import org.junit.Test;
import org.ops4j.pax.exam.player.Player;
import org.ops4j.pax.exam.testforge.SingleClassProvider;
import org.ops4j.pax.exam.testforge.WaitForService;
import org.osgi.service.log.LogService;
import org.slf4j.LoggerFactory;

public class OSGiTest {


    @Test
    public void lessonTest()
        throws Exception
    {
        new Player().with(
            options(
                mavenBundle().groupId( "org.ops4j.pax.logging" ).artifactId( "pax-logging-service" ).version( "1.6.2" ),
                mavenBundle().groupId( "org.apache.geronimo.specs" ).artifactId( "geronimo-jta_1.1_spec" ).version( "1.1" ).start(),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-kernel" ).version( "1.3" ).start()
            )
        )
        .test( WaitForService.class, LogService.class.getName(), 5000 )
        // set skip systembundle=true because equinox is indeed loading LoggerFactory from a different source.
        .test( SingleClassProvider.class, LoggerFactory.class.getName(), true )
        .play();

    }
}
