package org.jboss.forge.addon.testing;

import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.testing.framework.TestFramework;

/**
 * @author Ivan St. Ivanov
 */
public interface TestingFacet extends ProjectFacet
{
   TestFramework getTestFramework();
}
