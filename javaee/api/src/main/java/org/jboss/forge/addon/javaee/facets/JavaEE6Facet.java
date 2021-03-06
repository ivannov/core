/**
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.forge.addon.javaee.facets;

import static org.jboss.forge.addon.projects.stacks.StackBuilder.stack;

import org.jboss.forge.addon.javaee.cdi.CDIFacet_1_0;
import org.jboss.forge.addon.javaee.ejb.EJBFacet_3_1;
import org.jboss.forge.addon.javaee.faces.FacesFacet_2_0;
import org.jboss.forge.addon.javaee.jaxws.JAXWSFacet;
import org.jboss.forge.addon.javaee.jms.JMSFacet_1_1;
import org.jboss.forge.addon.javaee.jpa.JPAFacet_2_0;
import org.jboss.forge.addon.javaee.jta.JTAFacet_1_1;
import org.jboss.forge.addon.javaee.rest.RestFacet_1_1;
import org.jboss.forge.addon.javaee.servlet.ServletFacet_3_0;
import org.jboss.forge.addon.javaee.validation.ValidationFacet;
import org.jboss.forge.addon.parser.java.facets.JavaSourceFacet;
import org.jboss.forge.addon.projects.stacks.Stack;
import org.jboss.forge.addon.projects.stacks.StackFacet;

/**
 * Configures a project to depend on the JavaEE 6 libraries
 * 
 * @author <a href="ggastald@redhat.com">George Gastaldi</a>
 */
public interface JavaEE6Facet extends JavaEESpecFacet
{
   /**
    * The {@link Stack} associated with this {@link StackFacet}
    */
   public static final Stack STACK = stack("Java EE 6")
            .includes(JavaEE6Facet.class)
            .includes(JavaSourceFacet.class)
            .includes(CDIFacet_1_0.class)
            .includes(EJBFacet_3_1.class)
            .includes(FacesFacet_2_0.class)
            .includes(JAXWSFacet.class)
            .includes(JMSFacet_1_1.class)
            .includes(JPAFacet_2_0.class)
            .includes(JTAFacet_1_1.class)
            .includes(RestFacet_1_1.class)
            .includes(ServletFacet_3_0.class)
            .includes(ValidationFacet.class);
}
