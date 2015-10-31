package pl.myo.core.component.views;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.MapModel;
import pl.myo.core.component.table.grid.AbstractPageableView;
import pl.myo.core.component.table.grid.DataViewBase;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.User;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Marek on 2015-05-30.
 */
public abstract class TopicsView extends DataViewBase<Topic>
{
   private final MapModel<Long, List<Tag>> tagsMapModel;

   private final IModel<User> loggedModel;

   private final boolean readOnly;

   public TopicsView(String id, IDataProvider<Topic> dataProvider, MapModel<Long, List<Tag>> tagsMapModel, IModel<User> loggedModel, boolean readOnly)
   {
      super(id, dataProvider);
      this.tagsMapModel = tagsMapModel;
      this.readOnly = readOnly;
      this.loggedModel = loggedModel;
   }

   @Override
   protected void populateItem(Item<Topic> item)
   {
      item.add(new TopicItemPanel("topicPanel", item.getModel(), tagsMapModel, loggedModel, readOnly)
      {
         @Override
         protected void onEdit(AjaxRequestTarget target, Topic topic)
         {
            TopicsView.this.onEdit(target, topic);
         }

         @Override
         protected void onArchive(AjaxRequestTarget target, Topic topic)
         {
            TopicsView.this.onArchive(target, topic);
         }

         @Override
         protected void onDelete(AjaxRequestTarget target, Topic topic)
         {
            TopicsView.this.onDelete(target, topic);
         }

         @Override
         protected void onClick(AjaxRequestTarget target, IModel<Topic> model)
         {
            TopicsView.this.onClick(target, model);
         }
      });
   }

   protected abstract void onClick(AjaxRequestTarget target, IModel<Topic> model);

   protected abstract void onEdit(AjaxRequestTarget target, Topic topic);

   protected abstract void onArchive(AjaxRequestTarget target, Topic topic);

   protected abstract void onDelete(AjaxRequestTarget target, Topic topic);

}
