package pl.ftt.core.component.table.colums;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public abstract class IconColumn<T> extends AbstractColumn<T, String>
{
   public static final String COLUMN_IDENTIFIER = "iconColumn";

   private static final long serialVersionUID = 7016193003496251310L;

   private final String icon;

   private String cssClazz;

   public IconColumn(IModel<String> displayModel, String icon)
   {
      this(displayModel, icon, null);
   }

   public IconColumn(IModel<String> displayModel, String icon, String cssClazz)
   {
      super(displayModel);
      this.icon = icon;
      this.cssClazz = cssClazz;
   }

   @Override
   public void populateItem(Item cellItem, String componentId, IModel rowModel)
   {
      cellItem.add(new IconPanel(componentId, rowModel));
   }

   public abstract void onClick(AjaxRequestTarget target, T object);

   private class IconPanel extends Panel
   {
      private static final long serialVersionUID = 1L;

      public IconPanel(String id, final IModel model)
      {
         super(id);

         AjaxLink<Void> link = new AjaxLink("link", model)
         {
            private static final long serialVersionUID = -8077304318154652238L;

            @Override
            public void onClick(AjaxRequestTarget target)
            {
               IconColumn.this.onClick(target, (T) model.getObject());
            }

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
               super.updateAjaxAttributes(attributes);
               IconColumn.this.updateAjaxAttributes(attributes, (T) model.getObject());
            }

            @Override
            protected void onComponentTag(ComponentTag tag)
            {
               super.onComponentTag(tag);
               if (StringUtils.isNotBlank(cssClazz))
               {
                  tag.put("class", cssClazz);
               }
            }
         };
         Label label = new Label("icon");
         label.add(AttributeModifier.replace("class", icon));
         link.add(label);
         add(link);
      }
   }

   protected void updateAjaxAttributes(AjaxRequestAttributes attributes, T object)
   {
   }
}
