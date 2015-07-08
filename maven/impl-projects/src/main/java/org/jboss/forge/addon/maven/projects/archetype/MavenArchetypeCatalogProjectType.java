/**
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.maven.projects.archetype;

import java.util.Arrays;

import javax.inject.Inject;

import org.jboss.forge.addon.maven.archetype.ArchetypeCatalogFactoryRegistry;
import org.jboss.forge.addon.maven.projects.MavenFacet;
import org.jboss.forge.addon.maven.projects.archetype.ui.ArchetypeCatalogSelectionWizardStep;
import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.projects.AbstractProjectType;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.ui.context.UIContext;
import org.jboss.forge.addon.ui.wizard.UIWizardStep;

/**
 * Maven Archetype project type
 * 
 * @author <a href="ggastald@redhat.com">George Gastaldi</a>
 */
public class MavenArchetypeCatalogProjectType extends AbstractProjectType
{

    @Inject
    private ArchetypeCatalogFactoryRegistry catalogRegistry;
    
    @Override
    public String getType()
    {
        return "From Archetype Catalog";
    }

    @Override
    public Class<? extends UIWizardStep> getSetupFlow()
    {
        return ArchetypeCatalogSelectionWizardStep.class;
    }

    @Override
    public Iterable<Class<? extends ProjectFacet>> getRequiredFacets()
    {
        return Arrays
            .<Class<? extends ProjectFacet>> asList(MetadataFacet.class, JavaSourceFacet.class,
                                                    MavenFacet.class);
    }

    @Override
    public int priority()
    {
        return 10001;
    }

    @Override
    public String toString()
    {
        return "from-archetype-catalog";
    }

    @Override
    public boolean isEnabled(UIContext context)
    {
        return catalogRegistry.hasArchetypeCatalogFactories();
    }

}
