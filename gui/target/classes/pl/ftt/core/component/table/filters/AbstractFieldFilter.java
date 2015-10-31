package pl.ftt.core.component.table.filters;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class AbstractFieldFilter<T> extends Panel
{
   public static final String DEFAULT_CSS_CLASS = "filter-container";

   protected final int size;

   protected final WebMarkupContainer container;

   private String cssClass = DEFAULT_CSS_CLASS;

   public AbstractFieldFilter(String id, IModel<?> model, int size, IModel<Boolean> negationModel)
   {
      super(id, model);
      this.size = size;
      container = new WebMarkupContainer("container", negationModel)
      {
         @Override
         protected void onComponentTag(ComponentTag tag)
         {
            super.onComponentTag(tag);
            boolean value = Boolean.TRUE.equals(getDefaultModelObject());
            tag.put("class", value ? "filter-field input-group col-sm-12 negation-filter"
                  : "filter-field col-sm-12 input-group");
         }
      };
      container.setOutputMarkupId(true);
      add(container);
   }

   public abstract T getFilterField();

   @Override
   protected void onComponentTag(ComponentTag tag)
   {
      super.onComponentTag(tag);
      tag.put("class", cssClass + " col-sm-" + size);
   }

   public int getSize()
   {
      return size;
   }

   public String getCssClass()
   {
      return cssClass;
   }

   public void setCssClass(String cssClass)
   {
      this.cssClass = cssClass;
   }

   public boolean isDecorated()
   {
      return true;
   }
}
