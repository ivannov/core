package org.jboss.forge.addon.testing.ui;

import org.jboss.forge.addon.convert.Converter;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ui.AbstractProjectCommand;
import org.jboss.forge.addon.testing.framework.TestFramework;
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

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Ivan St. Ivanov
 */
public class TestSetupCommandImpl extends AbstractProjectCommand implements TestSetupCommand
{

   @Inject
   @WithAttributes(shortName = 'f', label = "Test Framework", type = InputType.DROPDOWN)
   private UISelectOne<TestFramework> testFramework;

   @Inject
   @WithAttributes(shortName = 'v', label = "Test Framework Version", type = InputType.DROPDOWN)
   private UISelectOne<String> version;

   @Override
   public UICommandMetadata getMetadata(UIContext context) {
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
      testFramework.setItemLabelConverter(new Converter<TestFramework , String>() {
         @Override
         public String convert(TestFramework source) {
            if(source == null) {
               return null;
            }
            if (uiBuilder.getUIContext().getProvider().isGUI()) {
               return source.getFrameworkName();
            }
            return source.getFrameworkName().toLowerCase();
         }
      });
      testFramework.setRequired(true);

      version.setRequired(true);
      version.setEnabled(new Callable<Boolean>() {
         @Override
         public Boolean call() throws Exception {
            return testFramework.hasValue();
         }
      });
      version.setValueChoices(new Callable<Iterable<String>>() {
         @Override
         public Iterable<String> call() throws Exception {
            if (version.isEnabled()) {
               return testFramework.getValue().getAvailableVersions();
            }
            return Collections.emptyList();
         }
      });
      version.setDefaultValue(new Callable<String>() {
         @Override
         public String call() throws Exception {
            final TestFramework testFramework = TestSetupCommandImpl.this.testFramework.getValue();
            return version.isEnabled() ? getLatestNonSnapshotVersion(testFramework.getAvailableVersions()) : null;
         }
      });
   }

   private String getLatestNonSnapshotVersion(List<String> dependencies)
   {
      if (dependencies == null || dependencies.isEmpty())
      {
         return null;
      }
      for (int i = dependencies.size() - 1; i >= 0; i--)
      {
         String dep = dependencies.get(i);
         if (!dep.endsWith("SNAPSHOT"))
         {
            return dep;
         }
      }
      return dependencies.get(dependencies.size() - 1);
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

   @Inject
   private ProjectFactory projectFactory;

   @Override
   protected ProjectFactory getProjectFactory()
   {
      return projectFactory;
   }

}
