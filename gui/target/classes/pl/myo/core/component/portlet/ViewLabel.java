package pl.myo.core.component.portlet;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

public class ViewLabel extends Label
{
   private final IModel[] models;

   private IValuesFormatter valuesFormatter;

   private boolean escapeHtml = true;

   public ViewLabel(String id, Component component, IModel... models)
   {
      this(id, new DefaultValuesFormatter(component), models);
   }

   public ViewLabel(String id, IValuesFormatter valuesFormatter, IModel... models)
   {
      super(id);
      Args.notNull(models, "models");
      Args.notNull(valuesFormatter, "valuesFormatter");
      this.models = models;
      this.valuesFormatter = valuesFormatter;
   }

   @Override
   public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
   {
      Object[] objects = new Object[models.length];
      int i = 0;
      for (IModel model : models)
      {
         Object object = model.getObject();
         objects[i] = object;
         i++;
      }

      String label = valuesFormatter.format(objects);
//      label = StringUtils.removeIsoControls(label); // breaks java script
      if (escapeHtml)
      {
         label = Strings.escapeMarkup(label, false, false).toString();
      }
      replaceComponentTagBody(markupStream, openTag, label);
   }

   public IValuesFormatter getValuesFormatter()
   {
      return valuesFormatter;
   }

   public void setValuesFormatter(IValuesFormatter valuesFormatter)
   {
      this.valuesFormatter = valuesFormatter;
   }

   public boolean isEscapeHtml()
   {
      return escapeHtml;
   }

   public void setEscapeHtml(boolean escapeHtml)
   {
      this.escapeHtml = escapeHtml;
   }
}
