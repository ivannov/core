package org.jboss.forge.addon.testing.facet;

import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;

/**
 * @author Ivan St. Ivanov
 */
public class TestNGTestingFacet extends AbstractTestingFacet implements TestingFacet
{

   public static final String TEST_NG_FRAMEWORK_NAME = "TestNG";
   public static final String TEST_NG_GROUP_ID = "org.testng";
   public static final String TEST_NG_ARTIFACT_ID = "testng";
   public static final String TEST_NG_SCOPE = "test";

   @Override
   public String getFrameworkName()
   {
      return TEST_NG_FRAMEWORK_NAME;
   }

   @Override
   protected DependencyBuilder buildFrameworkDependency()
   {
      return DependencyBuilder.create()
                  .setGroupId(TEST_NG_GROUP_ID)
                  .setArtifactId(TEST_NG_ARTIFACT_ID)
                  .setScopeType(TEST_NG_SCOPE);
   }
}
