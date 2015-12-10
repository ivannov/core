package org.jboss.forge.addon.testing.ui;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.testing.TestingFacet;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.hints.InputType;
import org.jboss.forge.addon.ui.input.UISelectOne;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;

/**
 * @author Ivan St. Ivanov
 */
public class TestSetupCommandImpl extends AbstractProjectCommand implements TestSetupCommand
{
   @Inject
   @WithAttributes(shortName = 'f', label = "Test Framework", type = InputType.DROPDOWN)
   private UISelectOne<TestingFacet> testFramework;

   @Inject
   @WithAttributes(shortName = 'v', label = "Test Framework Version", type = InputType.DROPDOWN)
   private UISelectOne<String> version;

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata.from(super.getMetadata(context), getClass())
               .category(Categories.create("Testing"))
               .name("Testing: Setup")
               .description("This addon will help you setup a unit test framework for your project");
   }

   @Override
   public void initializeUI(final UIBuilder uiBuilder) throws Exception
   {
      uiBuilder.add(testFramework)
               .add(version);

      testFramework.setEnabled(true);
      testFramework.setItemLabelConverter(source -> buildFrameworkName(uiBuilder, source));
      testFramework.setRequired(true);

      version.setRequired(true);
      version.setEnabled(() -> testFramework.hasValue());
      version.setValueChoices(this::getAvailableVersions);
      version.setDefaultValue(this::getDefaultFrameworkVersion);
   }

   private String buildFrameworkName(UIBuilder uiBuilder, TestingFacet source)
   {
      if (source == null)
      {
         return null;
      }
      if (uiBuilder.getUIContext().getProvider().isGUI())
      {
         return source.getFrameworkName();
      }
      return source.getFrameworkName().toLowerCase();
   }

   private String getDefaultFrameworkVersion()
   {
      final TestingFacet testingFacet = this.testFramework.getValue();
      return version.isEnabled() ? getLatestNonSnapshotVersion(testingFacet.getAvailableVersions()).get() : null;
   }

   private Iterable<String> getAvailableVersions()
   {
      if (version.isEnabled())
      {
         return testFramework.getValue().getAvailableVersions();
      }
      return Collections.emptyList();
   }

   private Optional<String> getLatestNonSnapshotVersion(List<String> dependencies)
   {
      if (dependencies == null || dependencies.isEmpty())
      {
         return Optional.empty();
      }
      return dependencies.stream()
               .filter(dep -> !dep.endsWith("SNAPSHOT"))
               .reduce((leftDep, rightDep) -> rightDep);
   }

   @Override
   public Result execute(UIExecutionContext uiExecutionContext) throws Exception
   {
      return Results.success("Framework: " + testFramework.getValue() + ", Version: " + version.getValue());
   }

   @Override
   protected boolean isProjectRequired()
   {
      return true;
   }

   @Override
   public boolean isEnabled(UIContext context)
   {
      return super.isEnabled(context);
   }

   @Inject
   private ProjectFactory projectFactory;

   @Override
   protected ProjectFactory getProjectFactory()
   {
      return projectFactory;
   }

}
