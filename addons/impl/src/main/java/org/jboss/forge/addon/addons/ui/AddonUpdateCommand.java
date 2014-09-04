package org.jboss.forge.addon.addons.ui;

import org.jboss.forge.addon.ui.command.AbstractUICommand;
import org.jboss.forge.addon.ui.context.UIBuilder;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.context.UIExecutionContext;
import org.jboss.forge.addon.ui.input.UIInput;
import org.jboss.forge.addon.ui.input.UIPrompt;
import org.jboss.forge.addon.ui.metadata.UICommandMetadata;
import org.jboss.forge.addon.ui.metadata.WithAttributes;
import org.jboss.forge.addon.ui.result.Result;
import org.jboss.forge.addon.ui.result.Results;
import org.jboss.forge.addon.ui.util.Categories;
import org.jboss.forge.addon.ui.util.Metadata;
import org.jboss.forge.furnace.Furnace;
import org.jboss.forge.furnace.addons.AddonId;
import org.jboss.forge.furnace.impl.addons.AddonRepositoryImpl;
import org.jboss.forge.furnace.manager.impl.AddonManagerImpl;
import org.jboss.forge.furnace.manager.impl.request.AddonActionRequestFactory;
import org.jboss.forge.furnace.manager.maven.addon.MavenAddonDependencyResolver;
import org.jboss.forge.furnace.manager.request.AddonActionRequest;
import org.jboss.forge.furnace.manager.request.InstallRequest;
import org.jboss.forge.furnace.manager.spi.AddonInfo;
import org.jboss.forge.furnace.repositories.AddonDependencyEntry;
import org.jboss.forge.furnace.repositories.AddonRepository;

import javax.inject.Inject;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

public class AddonUpdateCommand extends AbstractUICommand
{

   private final Furnace furnace;
   private final MavenAddonDependencyResolver resolver;
   private AddonManagerImpl addonManager;

   @Inject
   @WithAttributes(label = "Addon ID")
   private UIInput<String> addonId;

   public AddonUpdateCommand()
   {
      this.furnace = ServiceLoader.load(Furnace.class).iterator().next();
      resolver = new MavenAddonDependencyResolver();
      this.addonManager = new AddonManagerImpl(furnace, resolver);
   }

   @Override
   public void initializeUI(UIBuilder builder) throws Exception
   {
      builder.add(addonId);
   }

   @Override
   public UICommandMetadata getMetadata(UIContext context)
   {
      return Metadata
               .forCommand(this.getClass())
               .name("Addons: Update")
               .description("Updates all or just a single Forge addon")
               .category(Categories.create("Forge"));
   }

   @Override
   public Result execute(UIExecutionContext context) throws Exception
   {
      InstallRequest request;
      if (addonId.getValue() != null)
      {
         request = addonManager.install(
                  addonManager.getAddonId(addonId.getValue(), AddonRepositoryImpl.getRuntimeAPIVersion()));
      }
      else
      {
         AddonIdVersionComparator comparator = new AddonIdVersionComparator();
         List<AddonActionRequest> actions = new LinkedList<>();
         for (AddonRepository repository : furnace.getRepositories())
         {
            List<AddonId> addons = repository.listEnabled();
            for (AddonId addon : addons)
            {
               AddonId[] addonVersions = resolver.resolveVersions(addon.getName()).get();
               Arrays.sort(addonVersions, comparator);
               for (AddonActionRequest actionRequest : addonManager.install(addonVersions[addonVersions.length - 1]).getActions())
               {
                  if (!actions.contains(actionRequest))
                  {
                     actions.add(actionRequest);
                  }
               }
            }
         }
         request = AddonActionRequestFactory.createInstallRequest(new WholeForgeAddonInfo(), actions);
      }

      String message = "The update was cancelled.";
      if (userConfirmed(context, request))
      {
         request.perform();

      }
      return Results.success(message); // TODO return updated addons
   }

   private boolean userConfirmed(UIExecutionContext context, InstallRequest request)
   {
      UIPrompt prompt = context.getPrompt();
      return prompt.promptBoolean(request.toString());
   }

   private static class AddonIdVersionComparator implements Comparator<AddonId>
   {

      @Override
      public int compare(AddonId first, AddonId second)
      {
         return first.getVersion().compareTo(second.getVersion());
      }
   }

   private static class WholeForgeAddonInfo implements AddonInfo
   {

      @Override public AddonId getAddon()
      {
         return null;
      }

      @Override public Set<AddonInfo> getRequiredAddons()
      {
         return null;
      }

      @Override public Set<AddonInfo> getOptionalAddons()
      {
         return null;
      }

      @Override public Set<File> getResources()
      {
         return null;
      }

      @Override public Set<AddonDependencyEntry> getDependencyEntries()
      {
         return null;
      }

      @Override public String toString()
      {
         return "JBossForge";
      }
   }
}
