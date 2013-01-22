/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.ui.hints;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public enum InputTypes implements InputType
{
   CHECKBOX,
   RADIO_GROUP,
   
   TEXTAREA,
   TEXTBOX,
   
   FILE_PICKER,
   MULTI_FILE_PICKER,
   
   SELECT_ONE,
   SELECT_MANY,
   
   PASSWORD;
}
