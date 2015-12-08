package org.jboss.forge.addon.testing.framework;

import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
public class TestNGTestFramework implements TestFramework
{
   @Override
   public String getFrameworkName()
   {
      return "TestNG";
   }

   @Override
   public List<Coordinate> getFrameworkCoordinates()
   {
      Coordinate testngCoordinate = buildTestNGCoordinate();
      return Collections.singletonList(testngCoordinate);
   }

   @Inject
   private DependencyResolver resolver;

   @Override public List<String> getAvailableVersions()
   {
      final List<Coordinate> availableCoordinates = resolver.resolveVersions(
               DependencyQueryBuilder.create(buildTestNGCoordinate()));
      List<String> availableVersions = new ArrayList<>(availableCoordinates.size());
      for (Coordinate coordinate : availableCoordinates) {
         availableVersions.add(coordinate.getVersion());
      }
      return availableVersions;

   }

   private Coordinate buildTestNGCoordinate()
   {
      return DependencyBuilder.create()
                  .setGroupId("org.testng")
                  .setArtifactId("testng")
                  .setScopeType("test")
                  .getCoordinate();
   }
}
