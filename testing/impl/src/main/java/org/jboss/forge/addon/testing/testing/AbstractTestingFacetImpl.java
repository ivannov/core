package org.jboss.forge.addon.testing.testing;

import org.jboss.forge.addon.dependencies.Coordinate;
import org.jboss.forge.addon.dependencies.Dependency;
import org.jboss.forge.addon.dependencies.DependencyResolver;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.dependencies.builder.DependencyQueryBuilder;
import org.jboss.forge.addon.projects.facets.AbstractProjectFacet;
import org.jboss.forge.addon.projects.facets.DependencyFacet;
import org.jboss.forge.addon.testing.TestingFacet;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ivan St. Ivanov
 */
public abstract class AbstractTestingFacetImpl extends AbstractProjectFacet implements TestingFacet
{
   @Override
   public boolean install()
   {
      final DependencyFacet dependencyFacet = getDependencyFacet();
      getMatchingDependencies(dependencyFacet.getDependencies())
               .forEach(dependencyFacet::removeDependency);
      getFrameworkDependencies().forEach(dependencyFacet::addDirectDependency);
      return true;
   }

   private DependencyFacet getDependencyFacet()
   {
      return getFaceted().getFacet(DependencyFacet.class);
   }

   private Stream<Dependency> getMatchingDependencies(List<Dependency> existingDependencies) {
      final List<Dependency> frameworkCoordinates = getFrameworkDependencies();
      return existingDependencies.stream()
               .filter(existingDependency ->
                        frameworkCoordinates.stream()
                           .anyMatch(
                                 frameworkDependency -> areCoordinatesMatching(
                                    frameworkDependency.getCoordinate(), existingDependency.getCoordinate())));
   }

   private boolean areCoordinatesMatching(Coordinate coordinate1, Coordinate coordinate2)
   {
      return coordinate1.getArtifactId().equals(coordinate2.getArtifactId()) &&
               coordinate1.getGroupId().equals(coordinate2.getGroupId());
   }

   @Override
   public boolean isInstalled()
   {
      return getMatchingDependencies(getDependencyFacet().getDependencies()).count() ==
               getFrameworkDependencies().size();
   }

   @Override
   public List<Dependency> getFrameworkDependencies()
   {
      return Collections.singletonList(buildFrameworkDependency());
   }

   @Override
   public List<String> getAvailableVersions()
   {
      final List<Coordinate> availableCoordinates = getDependencyResolver().resolveVersions(
               DependencyQueryBuilder.create(buildFrameworkDependency().getCoordinate()));
      return availableCoordinates.stream()
               .map(Coordinate::getVersion)
               .collect(Collectors.toList());
   }

   protected abstract DependencyResolver getDependencyResolver();

   protected abstract DependencyBuilder buildFrameworkDependency();
}
