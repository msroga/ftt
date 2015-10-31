package pl.ftt.core.component;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import pl.ftt.gui.formatter.Formatters;

import java.io.Serializable;

public class DefaultLabel extends Label
{
   private static final long serialVersionUID = -1493329988184176208L;

   private String nullValue = "-";

   private Component component;

   private boolean escapeHtml = true;

   public DefaultLabel(String id, IModel<?> model, Component component)
   {
      super(id, model);
      this.component = component;
   }

   public DefaultLabel(String id, IModel<?> model)
   {
      this(id, model, null);
   }

   public DefaultLabel(String id, Serializable label, Component component)
   {
      super(id, label);
      this.component = component;
   }

   public DefaultLabel(String id, Serializable label)
   {
      this(id, label, null);
   }

   public DefaultLabel(String id, String label, Component component)
   {
      super(id, label);
      this.component = component;
   }

   public DefaultLabel(String id, String label)
   {
      this(id, label, null);
   }

   @Override
   public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag)
   {
      Object object = getDefaultModelObject();
      String newValue = Formatters.format(object, component);
      if (newValue == null)
      {
         newValue = nullValue;
      }
      else if (newValue != null && escapeHtml)
      {
         newValue = StringEscapeUtils.escapeHtml4(newValue);
      }
      replaceComponentTagBody(markupStream, openTag, newValue);
   }

   public String getNullValue()
   {
      return nullValue;
   }

   public void setNullValue(String nullValue)
   {
      this.nullValue = nullValue;
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
