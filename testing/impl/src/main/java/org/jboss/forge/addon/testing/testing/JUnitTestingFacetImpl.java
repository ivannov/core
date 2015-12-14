package org.jboss.forge.addon.testing.testing;

import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;

import javax.inject.Inject;

/**
 * @author Ivan St. Ivanov
 */
public class JUnitTestingFacetImpl extends AbstractTestingFacetImpl
{
   @Override
   public String getFrameworkName()
   {
      return "JUnit";
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
               .setGroupId("junit")
               .setArtifactId("junit")
               .setScopeType("test");
   }
}
