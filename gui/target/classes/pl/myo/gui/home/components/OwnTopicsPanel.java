package pl.myo.gui.home.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.myo.core.component.table.AbstractProvider;
import pl.myo.core.component.table.DataTable;
import pl.myo.core.component.table.colums.DefaultColumn;
import pl.myo.core.component.table.colums.StyledPropertyColumn;
import pl.myo.core.notification.Notification;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.Topic;
import pl.myo.domain.TopicTypeEnum;
import pl.myo.domain.User;
import pl.myo.domain.filters.IFilter;
import pl.myo.domain.filters.OpenSearchDescription;
import pl.myo.domain.filters.SortFilterChain;
import pl.myo.gui.articles.ArticleDetailsPage;
import pl.myo.gui.questions.QuestionDetailPage;
import pl.myo.service.ITopicService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-06-14.
 */
public class OwnTopicsPanel extends GenericPanel<User>
{
   @SpringBean
   private ITopicService topicService;

   public OwnTopicsPanel(String id, IModel<User> model)
   {
      super(id, model);
      List<IColumn<Topic, String>> columns = new ArrayList<>();
      columns.add(new StyledPropertyColumn<Topic>(new ResourceModel("topic.title"), Topic.FIELD_TITLE));
      columns.add(new DefaultColumn<Topic>(new ResourceModel("topic.created"), Topic.FIELD_CREATED));

      OpenSearchDescription<Topic> osd = new OpenSearchDescription<Topic>();
      AbstractProvider<Topic> provider = new AbstractProvider<Topic>(osd)
      {
         @Override
         public List<Topic> find(IFilter filter, SortFilterChain sortFilterChain, int first, int count)
         {
            return topicService.findBy(getModelObject());
         }

         @Override
         public long size(IFilter filter)
         {
            return topicService.countBy(getModelObject());
         }
      };

      DataTable<Topic> table = new DataTable<Topic>("table", columns, new ResourceModel("own.topics.table.header"), provider, 5, true)
      {
         @Override
         protected void onRowClick(AjaxRequestTarget target, IModel<Topic> model)
         {
            super.onRowClick(target, model);
            Topic topic = model.getObject();
            if (topic.getType() == TopicTypeEnum.ARTICLE)
            {
               setResponsePage(new ArticleDetailsPage(new PageContext<Topic>(topic)));
            }
            else
            {
               setResponsePage(new QuestionDetailPage(new PageContext<Topic>(topic)));
            }
         }
      };
      add(table);
   }
}
