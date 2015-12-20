package org.jboss.forge.addon.testing;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.facets.DependencyFacet;
import org.jboss.forge.addon.testing.facet.JUnitTestingFacet;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.forge.furnace.container.simple.Service;
import org.jboss.forge.furnace.container.simple.lifecycle.SimpleContainer;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * @author Ivan St. Ivanov
 */
@RunWith(Arquillian.class)
public class TestingFacetTest
{
   @Deployment
   @AddonDependencies
   public static AddonArchive getDeployment()
   {
      final AddonArchive addonArchive = ShrinkWrap.create(AddonArchive.class)
               .addAsServiceProvider(Service.class, TestingFacetTest.class);
      System.out.println(addonArchive.toString(true));
      return addonArchive;
   }

   private Project project;

   private FacetFactory facetFactory;

   @Before
   public void setUp()
   {
      ProjectFactory projectFactory = SimpleContainer.getServices(getClass().getClassLoader(), ProjectFactory.class)
               .get();
      facetFactory = SimpleContainer.getServices(getClass().getClassLoader(), FacetFactory.class).get();
      project = projectFactory.createTempProject();
   }

   @Test
   public void testInstallJUnitFacetOnEmptyProject() throws Exception
   {
      facetFactory.install(project, JUnitTestingFacet.class);
      DependencyFacet dependencyFacet = project.getFacet(DependencyFacet.class);
      assertTrue(dependencyFacet.hasDirectDependency(DependencyBuilder.create()
               .setGroupId("junit")
               .setArtifactId("junit")
               .setScopeType("test")));
   }
}
