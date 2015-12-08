package org.jboss.forge.addon.testing.framework;

import org.jboss.forge.addon.projects.facets.DependencyFacet;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ivan St. Ivanov
 */
public class TestFrameworks
{
   private static Set<TestFramework> knownFrameworks = new HashSet<>();

   static {
      knownFrameworks.add(new JUnitTestFramework());
      knownFrameworks.add(new TestNGTestFramework());
   }

   public static TestFramework getAvailableTestFramework(DependencyFacet dependencyFacet)
   {
      for (TestFramework framework : knownFrameworks)
      {
         if (dependencyFacet.getDependencies().containsAll(framework.getFrameworkCoordinates())) {
            return framework;
         }
      }
      return null;
   }
}
