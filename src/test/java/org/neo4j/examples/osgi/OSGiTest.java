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

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.felix;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.LibraryOptions.junitBundles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestAddress;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.ops4j.pax.exam.testforge.SingleClassProvider;
import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;

/**
 * This is what's probably most known to Pax Exam 1.x users.
 * You can recognize the "Junit Driver" approach by the @RunWith() annotation at class level.
 *
 * This overloads JUnit4s default runner so that Pax Exam is in full control of:
 * - the test roaster
 * - the test invokation
 *
 * So whats the test roaster ?
 * You know methods annotated with @Test annotations from JUnit4 API, right ?
 * This is the standard roaster. Those will appear in your Test Runner when launching this class in your IDE using "Run with JUnit..".
 *
 * But: You learned (in Lesson 1) that with Pax Exam you might have your tests executed more than once in different Test Container instances.
 * (Remember the TestContainerFactory.parse() returning a list of TestContainers ?)
 *
 * So wouldn't it be nice to have that reflected in your JUnit Roaster ?
 * Thats what the @RunWith(JUnit4TestRunner.class) does. Pax Exam re-aranges the JUnit4 Roaster and gives you a single entry for each physical test.
 * In this lesson we are using the NativeTestContainer implementation (see the pom.xml), and additional put two OSGi Frameworks to it: Felix and Equinox.
 * You will see each of the @Test methods below twice. Once for each framework.
 *
 * The @Configuration is a desclarative way of what you did manually in the previous lessons. Now you only return Option[] in any @Configuration-annotated method
 * and you are set.
 * A probe will be generated underneath with every @Test put into it.
 *
 * Important:
 * It might be subtle at first, but it is very important to understand that this test class is also the class that will end up in your probe.
 * Just because you use it to initially kick of the tests (see, you press "Run with JUnit" on this class) it does not mean the tests will run in the same instance of this class.
 * Underneath, the @Tests are invoked on a fresh instance of this class insight the OSGi Container (which might be a totally different JVM).
 * So @Tests should be rather side-effect and stateless and aware of package visibility inside the OSGi container.
 */

@RunWith( JUnit4TestRunner.class )

/**
 * NEW & Optional:
 * You can annotate your class with the @ExamReactorStrategy to overwrite the default strategy:
 * @ExamReactorStrategy( AllConfinedStagedReactorFactory.class )
 * This is the default setting.
 * It resembles the way Exam 1.x worked: a new TestContainer instance for every test in your probe(s).
 * Depending on the TestContainerFactory you use (pom.xml!) this may be slower than every other strategy.
 * But its probably also the most side-effect free solution.
 * Lets do the math how many test containers are launched (one after another):
 * 2 tests x 2 test containers in pom = 4 launches
 *
 *
 * or
 *
 * @ExamReactorStrategy( EagerSingleStagedReactorFactory.class )
 * This is the other extreme to AllConfinedStagedReactorFactory. It uses one TestContainer (for all of your tests).
 * Important: You will still get of cause two test container instances for every physical container (like Felix + Equinox).
 * Its just that the Felix container will be started once, all your tests are running against it, then it will shutdown.
 * So in this specific example, you will have two test container launches (one for Felix and another for Equinox.
 * This does not change when adding more Tests to this TestCase.
 *
 *
 */
@ExamReactorStrategy( AllConfinedStagedReactorFactory.class )
public class OSGiTest {

    @Configuration()
    public Option[] config()
    {
        return options(
            junitBundles(),
            felix()        );
    }

    /**
     * Just like any other Test in previous lessons, they can receive an instance of BundleContext plus optional arguments.
     * Because you have Test Setup (@Configuration method) and Tests (this method) side by side, there is no point passing additional arguments.
     *
     * @param ctx BundleContext injected. Must be first argument, if any.
     */
    @Test
    public void withBC( BundleContext ctx )
    {
        assertThat( ctx, is( notNullValue() ) );
        System.out.println( "BundleContext of bundle injected: " + ctx.getBundle().getSymbolicName() );

    }

    @Test
    public void without()
    {
        System.out.println( "------- HERE!" );
    }
   
    
    @Test
    public TestAddress prebuilt( TestProbeBuilder builder )
    {
        return builder.addTest( SingleClassProvider.class, (Object)LoggerFactory.class.getName() );
    }
}
