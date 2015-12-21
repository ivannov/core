package org.jboss.forge.addon.testing.facet;

import org.jboss.forge.addon.dependencies.Dependency;
import org.jboss.forge.addon.projects.ProjectFacet;

import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
public interface TestingFacet extends ProjectFacet
{
   String getFrameworkName();

   List<Dependency> getFrameworkDependencies();

   List<String> getAvailableVersions();

   void setFrameworkVersion(String version);
}
