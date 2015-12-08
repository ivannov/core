package org.jboss.forge.addon.testing;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.testing.framework.TestFramework;

/**
 * @author Ivan St. Ivanov
 */
public class TestingFacetImpl implements TestingFacet
{

   private TestFramework testFramework;

   public TestingFacetImpl(TestFramework testFramework)
   {
      this.testFramework = testFramework;
   }

   @Override
   public TestFramework getTestFramework()
   {
      return testFramework;
   }

   @Override
   public Project getFaceted()
   {
      return null;
   }

   @Override
   public boolean install()
   {
      return false;
   }

   @Override
   public boolean isInstalled()
   {
      return false;
   }

   @Override
   public boolean uninstall()
   {
      return false;
   }
}
