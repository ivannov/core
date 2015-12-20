package org.jboss.forge.addon.testing.facet;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;

/**
 * @author Ivan St. Ivanov
 */
public class JUnitTestingFacet extends AbstractTestingFacet implements TestingFacet
{
   @Override
   public String getFrameworkName()
   {
      return "JUnit";
   }

   @Override
   protected DependencyBuilder buildFrameworkDependency()
   {
      return DependencyBuilder.create()
               .setGroupId("junit")
               .setArtifactId("junit")
               .setScopeType("test");
   }
}
