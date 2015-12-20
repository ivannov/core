package org.jboss.forge.addon.testing.facet;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;

/**
 * @author Ivan St. Ivanov
 */
public class TestNGTestingFacetImpl extends AbstractTestingFacetImpl implements TestNGTestingFacet
{
   @Override
   public String getFrameworkName()
   {
      return "TestNG";
   }

   @Override
   protected DependencyBuilder buildFrameworkDependency()
   {
      return DependencyBuilder.create()
                  .setGroupId("org.testng")
                  .setArtifactId("testng")
                  .setScopeType("test");
   }
}
