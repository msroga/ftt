package pl.myo.core.component.table;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

public class SimpleTableHeader extends GenericPanel<String>
{
   protected String icon;

   public SimpleTableHeader(String id, IModel<String> caption)
   {
      this(id, caption, "fa fa-table");
   }

   public SimpleTableHeader(String id, IModel<String> caption, String icon)
   {
      super(id, caption);
      this.icon = icon;
      setOutputMarkupId(true);

      Label label = new Label("caption", caption);
      add(label);

      add(new WebMarkupContainer("icon")
      {
         @Override
         protected void onComponentTag(ComponentTag tag)
         {
            super.onComponentTag(tag);
            tag.put("class", SimpleTableHeader.this.icon);
         }
      });
   }

   public String getIcon()
   {
      return icon;
   }

   public void setIcon(String icon)
   {
      this.icon = icon;
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      setVisible(getModel() != null);
   }
}
