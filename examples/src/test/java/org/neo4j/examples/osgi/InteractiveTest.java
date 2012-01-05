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
package org.neo4j.examples.osgi;

import org.ops4j.pax.exam.ExamSystem;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestContainer;
import org.ops4j.pax.exam.spi.PaxExamRuntime;

import static org.neo4j.examples.osgi.OSGiTest.*;
import static org.neo4j.examples.osgi.SDNSetup.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.*;

/**
 * @author Toni Menzel (toni@okidokiteam.com)
 */
public class InteractiveTest {

    public static void main( String[] args )
               throws Exception
       {
           String gogoVersion = "0.8.0";

           Option[] options = combine(

               sdnOptions(),

               mavenBundle().groupId( "org.apache.felix" ).artifactId( "org.apache.felix.gogo.runtime" ).version( gogoVersion ),
               mavenBundle().groupId( "org.apache.felix" ).artifactId( "org.apache.felix.gogo.shell" ).version( gogoVersion ),
               mavenBundle().groupId( "org.apache.felix" ).artifactId( "org.apache.felix.gogo.command" ).version( gogoVersion ),
               workingDirectory( System.getProperty( "user.dir" ) + "/target/server" )
           );

           ExamSystem system = PaxExamRuntime.createServerSystem( options );
           TestContainer container = PaxExamRuntime.createContainer( system );
           container.start();

           // after the server has finished bootstrapping everything, you can issue GogoCommands inside the IDE, debugging and mingle with bundle resolve problems.
       }
}
