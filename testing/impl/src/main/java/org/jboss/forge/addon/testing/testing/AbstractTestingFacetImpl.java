package org.jboss.forge.addon.testing.testing;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.testing.TestingFacet;

/**
 * @author Ivan St. Ivanov
 */
public abstract class AbstractTestingFacetImpl implements TestingFacet
{

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
