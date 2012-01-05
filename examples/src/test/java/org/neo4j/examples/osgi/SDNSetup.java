/**
 * Copyright (c) 2012 "Neo Technology,"
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

import org.ops4j.pax.exam.Option;

import static org.ops4j.pax.exam.CoreOptions.*;

/**
 * @author Toni Menzel (toni@okidokiteam.com)
 */
public class SDNSetup {

    public static Option[] sdnOptions()
        {
            return options(
                autoWrap(),
                equinox(),
               // vmOptions("-Xdebug -Xrunjdwp:transport=dt_socket,address=127.0.0.1:8000"),
                repository("https://oss.sonatype.org/content/groups/ops4j/"),
                profile("spring.dm").version("1.2.1"),
                cleanCaches(),
                mavenBundle().groupId( "org.neo4j" ).artifactId( "neo4j-osgi-bundle" ).version( "0.1-SNAPSHOT" ),
                mavenBundle().groupId( "net.sourceforge.cglib" ).artifactId( "com.springsource.net.sf.cglib" ).version( "2.2.0" ),
                mavenBundle().groupId( "org.aspectj" ).artifactId( "com.springsource.org.aspectj.runtime" ).version( "1.6.8.RELEASE" ),
                mavenBundle().groupId( "org.aopalliance" ).artifactId( "com.springsource.org.aopalliance" ).version( "1.0.0" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-context" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-beans" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-core" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-tx" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-aop" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-expression" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-asm" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.springframework" ).artifactId( "spring-aspects" ).version( "3.0.6.RELEASE" ),
                mavenBundle().groupId( "org.apache.geronimo.specs" ).artifactId( "geronimo-jta_1.1_spec" ).version( "1.1.1" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.beanutils" ).version( "1.8.0" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.collections" ).version( "3.2.1" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.codec" ).version( "1.4.0" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.digester" ).version( "1.8.1" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "net.junisphere.commons-jxpath" ).version( "1.3" ),
                mavenBundle().groupId( "org.apache.commons" ).artifactId( "com.springsource.org.apache.commons.lang" ).version( "2.5.0" ),
                mavenBundle().groupId( "org.apache.ant" ).artifactId( "com.springsource.org.apache.tools.ant" ).version( "1.8.1" ),
                mavenBundle().groupId( "commons-configuration" ).artifactId( "commons-configuration" ).version( "1.6" ),
                mavenBundle().groupId( "javax.servlet" ).artifactId( "javax.servlet" ).version( "3.0.0.v201103241009" ),
                mavenBundle().groupId( "javax.servlet" ).artifactId( "javax.servlet.jsp" ).version( "2.2.0.v201103241009" ),
                mavenBundle().groupId( "javax.el" ).artifactId( "javax.el" ).version( "2.2.0.v201105051105" ),
                mavenBundle().groupId( "javax.mail" ).artifactId( "com.springsource.javax.mail" ).version( "1.4.1" ),
                mavenBundle().groupId( "org.jdom" ).artifactId( "com.springsource.org.jdom" ).version( "1.1.0" ),
                mavenBundle().groupId( "org.objectweb.jotm" ).artifactId( "com.springsource.org.objectweb.jotm" ).version( "2.0.10" ),
                mavenBundle().groupId( "org.objectweb.howl" ).artifactId( "com.springsource.org.objectweb.howl" ).version( "1.0.2" ),
                mavenBundle().groupId( "org.springframework.data" ).artifactId( "spring-data-commons-core" ).version( "1.2.0.RELEASE" ),
                mavenBundle().groupId( "org.springframework.data" ).artifactId( "spring-data-neo4j" ).version( "2.0.0.RELEASE" ),
                mavenBundle().groupId( "org.springframework.data" ).artifactId( "spring-data-neo4j-aspects" ).version( "2.0.0.RELEASE" ),
                mavenBundle().groupId( "org.neo4j.examples.osgi" ).artifactId( "sdn-test-bundle" ).version( "1.6.0.BUILD-SNAPSHOT" )
            );
        }
}
