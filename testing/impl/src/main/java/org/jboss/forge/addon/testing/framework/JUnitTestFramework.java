package org.jboss.forge.addon.testing.framework;

import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.Dependency;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.CoordinateBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivan St. Ivanov
 */
public class JUnitTestFramework implements TestFramework
{
   @Override
   public String getFrameworkName()
   {
      return "JUnit";
   }

   @Override
   public List<Coordinate> getFrameworkCoordinates()
   {
      Coordinate junitCoordinate = buildJUnitCoordinate();
      return Collections.singletonList(junitCoordinate);
   }

   @Inject
   private DependencyResolver resolver;

   @Override
   public List<String> getAvailableVersions()
   {
      final List<Coordinate> availableCoordinates = resolver.resolveVersions(
               DependencyQueryBuilder.create(buildJUnitCoordinate()));
      List<String> availableVersions = new ArrayList<>(availableCoordinates.size());
      for (Coordinate coordinate : availableCoordinates) {
         availableVersions.add(coordinate.getVersion());
      }
      return availableVersions;
   }

   private CoordinateBuilder buildJUnitCoordinate()
   {
      return DependencyBuilder.create()
               .setGroupId("junit")
               .setArtifactId("junit")
               .setScopeType("test")
               .getCoordinate();
   }
}
