package org.jboss.forge.addon.testing.framework;

import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.Dependency;

import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
public interface TestFramework
{

   String getFrameworkName();

   List<Coordinate> getFrameworkCoordinates();

   List<String> getAvailableVersions();
}
