package pl.myo.core.component.table.toolbar;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import pl.myo.core.component.table.AbstractToolbar;
import pl.myo.core.component.table.DataTable;
import pl.myo.core.component.table.FilterDecorator;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.OpenSearchDescription;

public abstract class FilterToolbar<T extends IFilter> extends AbstractToolbar
{
   protected final Form form;

   protected AjaxSubmitLink submitButton;

   protected final FilterDecorator filterDecorator;

   public FilterToolbar(final DataTable<?> table)
   {
      super(table);
      form = new Form("form");

      submitButton = new AjaxSubmitLink("submitButton", form)
      {
         private static final long serialVersionUID = 8040559353279729326L;

         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            table.refresh(target);
         }
      };
      form.add(submitButton);
      form.setDefaultButton(submitButton);
      add(form);

      filterDecorator = new FilterDecorator()
      {
         private static final long serialVersionUID = 8878588507970365948L;

         @Override
         protected void onUpdate(AjaxRequestTarget target)
         {
            table.refresh(target);
         }
      };

      AjaxLink refreshButton = new AjaxLink("refreshButton")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            form.visitChildren(FormComponent.class, new IVisitor<FormComponent, Void>()
            {
               @Override
               public void component(FormComponent object, IVisit<Void> visit)
               {
                  object.setDefaultModelObject(null);
                  object.modelChanged();
               }
            });
            target.add(form);
            table.refresh(target);
         }
      };
      form.add(refreshButton);
   }

   @Override
   protected void onInitialize()
   {
      super.onInitialize();
      registerFilters(table, new PropertyModel(
            table.getDataProvider().getFilterState(),
            OpenSearchDescription.FIELD_FILTER));
   }

   public void addFilter(final Component filter)
   {
      addFilter(filter, null);
   }

   public void addFilter(final Component filter, IModel<String> placeholder)
   {
      filterDecorator.decorate(filter, placeholder);
      form.add(filter);
   }

   public abstract void registerFilters(DataTable<?> table, IModel<T> filterModel);
}
