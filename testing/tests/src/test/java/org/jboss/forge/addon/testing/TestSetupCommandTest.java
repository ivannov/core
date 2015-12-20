/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.testing;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.testing.ui.TestSetupCommand;
import org.jboss.forge.addon.ui.controller.CommandController;
import org.jboss.forge.addon.ui.test.UITestHarness;
import org.jboss.forge.arquillian.AddonDependencies;
import org.jboss.forge.arquillian.archive.AddonArchive;
import org.jboss.forge.furnace.container.simple.Service;
import org.jboss.forge.furnace.container.simple.lifecycle.SimpleContainer;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 */
@RunWith(Arquillian.class)
public class TestSetupCommandTest
{

   @Deployment
   @AddonDependencies
   public static AddonArchive getDeployment()
   {
      return ShrinkWrap.create(AddonArchive.class).addAsServiceProvider(Service.class, TestSetupCommandTest.class);
   }

   private UITestHarness testHarness;

   private ProjectFactory projectFactory;

   @Before
   public void setUp() throws Exception
   {
      projectFactory = SimpleContainer.getServices(getClass().getClassLoader(), ProjectFactory.class)
               .get();
      testHarness = SimpleContainer.getServices(getClass().getClassLoader(), UITestHarness.class).get();
   }

   @Test
   public void test() throws Exception
   {
      Project project = projectFactory.createTempProject();
      try (CommandController controller = testHarness.createCommandController(TestSetupCommand.class,
               project.getRoot()))
      {
         controller.initialize();
         Assert.assertTrue(controller.hasInput("testFramework"));
      }
   }
}
