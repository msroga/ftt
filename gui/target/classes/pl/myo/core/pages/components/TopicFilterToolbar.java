package pl.myo.core.pages.components;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;
import pl.myo.core.component.table.DataTable;
import pl.myo.core.component.table.FilterDecorator;
import pl.myo.core.component.table.toolbar.FilterToolbar;
import pl.myo.core.renderer.DefaultChoiceRenderer;
import pl.myo.core.renderer.IStyledChoiceRenderer;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.OpenSearchDescription;
import pl.myo.domain.filters.TopicFilter;
import pl.myo.domain.filters.UserFilter;
import pl.myo.service.ITagService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Marek on 2015-05-30.
 */
public abstract class TopicFilterToolbar extends Panel
{
   @SpringBean
   private ITagService tagService;

   private final ListModel<Tag> tagsChoisesModel = new ListModel<>();

   private final IModel<TopicFilter> filterModel = new Model<>();

   protected final Form form;

   protected AjaxSubmitLink submitButton;

   protected final FilterDecorator filterDecorator;

   public TopicFilterToolbar(String id, TopicFilter filter, final boolean readOnly)
   {
      super(id);
      filterModel.setObject(filter);
      tagsChoisesModel.setObject(tagService.findAllActive());
      form = new Form("form");

      submitButton = new AjaxSubmitLink("submitButton", form)
      {
         private static final long serialVersionUID = 8040559353279729326L;

         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            onRefresh(target);
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
            onRefresh(target);
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
            onRefresh(target);
         }
      };
      form.add(refreshButton);

      AjaxLink addTopic = new AjaxLink("addTopic")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onAdd(target);
         }

         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            setVisible(!readOnly);
         }
      };
      form.add(addTopic);
   }

   protected abstract void onAdd(AjaxRequestTarget target);

   protected abstract void onRefresh(AjaxRequestTarget target);

   @Override
   protected void onInitialize()
   {
      super.onInitialize();
      registerFilters(filterModel);
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

   public void registerFilters(IModel<TopicFilter> filterModel)
   {
      TextField<String> titleFilter = new TextField<String>("titleFilter", new PropertyModel<String>(
              filterModel,
              TopicFilter.FIELD_TITLE));
      addFilter(titleFilter, new ResourceModel("topic.title"));

      ListMultipleChoice<Tag> tagsFilter = new ListMultipleChoice<Tag>("tagsFilter",
              new PropertyModel<List<Tag>>(filterModel, TopicFilter.FIELD_TAGS), tagsChoisesModel)
      {
         @Override
         public void renderHead(IHeaderResponse response)
         {
            super.renderHead(response);
            response.render(OnDomReadyHeaderItem.forScript("$('#" + this.getMarkupId() + "').select2()"));
         }
      };
      tagsFilter.setChoiceRenderer(new DefaultChoiceRenderer());
      tagsFilter.setOutputMarkupId(true);
      addFilter(tagsFilter, new ResourceModel("topic.tags"));

      DropDownChoice<Boolean> activeFilter = new DropDownChoice<Boolean>("activeFilter",
              new PropertyModel<Boolean>(filterModel, TopicFilter.FIELD_ACTIVE),
              Arrays.asList(Boolean.TRUE, Boolean.FALSE));
      activeFilter.setNullValid(false);
      activeFilter.setChoiceRenderer(new ChoiceRenderer<Boolean>()
      {
         @Override
         public Object getDisplayValue(Boolean object)
         {
            return getString("ActiveFilter." + object.toString());
         }
      });
      addFilter(activeFilter, new ResourceModel("topic.active"));
   }
}
