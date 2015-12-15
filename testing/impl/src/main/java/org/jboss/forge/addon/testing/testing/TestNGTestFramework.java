package org.jboss.forge.addon.testing.testing;

import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.testing.TestNGTestingFacet;

import javax.inject.Inject;

/**
 * @author Ivan St. Ivanov
 */
public class TestNGTestFramework extends AbstractTestingFacetImpl implements TestNGTestingFacet
{
   @Override
   public String getFrameworkName()
   {
      return "TestNG";
   }

   @Inject
   private DependencyResolver resolver;

   @Override
   protected DependencyResolver getDependencyResolver()
   {
      return resolver;
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
