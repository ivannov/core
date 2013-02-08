package org.jboss.forge.aesh.commands;

import org.jboss.aesh.console.Config;
import org.jboss.aesh.util.Parser;
import org.jboss.forge.aesh.ShellContext;
import org.jboss.forge.ui.*;
import org.jboss.forge.ui.base.UICommandMetadataBase;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class LsCommand  implements UICommand
{
   @Inject
   private UIInput<String> about;

    @Inject
    private UIInputMany<File> arguments;

   @Override
   public UICommandMetadata getMetadata()
   {
      return new UICommandMetadataBase("ls", "list files");
   }

   @Override
   public boolean isEnabled(UIContext context)
   {
      return context instanceof ShellContext;
   }

   @Override
   public void initializeUI(UIContext context) throws Exception
   {
      about.setLabel("about");
      about.setRequired(false);
       about.setDefaultValue("");

       about.setCompleter(new UICompleter<String>() {
           @Override
           public Iterable<String> getCompletionProposals(UIInputComponent<?,String> input, String value) {
               List<String> out = new ArrayList<String>();
               out.add("foo1");
               return out;
           }
       });
       context.getUIBuilder().add(about);

       arguments.setLabel("");
       arguments.setRequired(false);

       /* not needed for File
       arguments.setCompleter(new UICompleter<String>() {
           @Override
           public Iterable<String> getCompletionProposals(UIInputComponent<?,String> input, String value) {
               List<String> out = new ArrayList<String>();
               out.add("arguments!");
               return out;
           }
       }); */

       context.getUIBuilder().add(arguments);
   }

   @Override
   public void validate(UIValidationContext context)
   {
   }

   @Override
   public Result execute(UIContext context) throws Exception
   {
       StringBuilder builder = new StringBuilder();
       if(arguments.getValue() != null) {
           Iterator<File> iter = arguments.getValue().iterator();
           while(iter.hasNext()) {
               builder.append(iter.next().getAbsolutePath()+", ");
           }
       }

      return Results.success( listMany(arguments.getValue(),(ShellContext) context));
   }

    private String listMany(Iterable<File> files, ShellContext context) {
       if(arguments.getValue() != null) {
           Iterator<File> iter = arguments.getValue().iterator();
           while(iter.hasNext()) {
               return listLs(iter.next(), context);
           }
       }
        return null;
    }

    private String listLs(File path, ShellContext context) {
       if(path.isDirectory()) {
           List<String> files = new ArrayList<String>();
           for(File f : path.listFiles())
               files.add(f.getName());
           return Parser.formatDisplayList(files,
                   context.getShell().getConsole().getTerminalSize().getHeight(),
                   context.getShell().getConsole().getTerminalSize().getWidth());
       }
       else if(path.isFile())
           return path.getName();
        else
           return null;
    }
}
