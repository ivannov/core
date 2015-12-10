package org.jboss.forge.addon.testing.testing;

import org.jboss.forge.addon.projects.facets.AbstractProjectFacet;
import org.jboss.forge.addon.testing.TestingFacet;

/**
 * @author Ivan St. Ivanov
 */
public abstract class AbstractTestingFacetImpl extends AbstractProjectFacet implements TestingFacet
{
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
}
