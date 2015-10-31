package pl.myo.core.component.window;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.myo.core.component.form.BootstrapEditor;
import pl.myo.core.component.form.BootstrapListMultipleChoice;
import pl.myo.core.component.form.TextFieldWithLabel;
import pl.myo.core.component.window.DefaultWindow;
import pl.myo.domain.Tag;
import pl.myo.domain.Topic;
import pl.myo.domain.TopicTypeEnum;
import pl.myo.domain.User;
import pl.myo.service.ITagService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 2015-05-28.
 */
public abstract class TopicWindow extends DefaultWindow
{
   private final IModel<Topic> topicModel = new Model<>();

   private final ListModel<Tag> tagsModel = new ListModel<>();

   private TextFieldWithLabel titleField;

   private BootstrapEditor contentField;

   @SpringBean
   private ITagService tagService;

   public TopicWindow(String id, IModel<String> header)
   {
      super(id, header);

      titleField = new TextFieldWithLabel("titleField", new PropertyModel<String>(topicModel, Topic.FIELD_TITLE),
              new ResourceModel("topic.title"), true);
      form.add(titleField);

      contentField = new BootstrapEditor("contentField", new PropertyModel<String>(topicModel, Topic.FIELD_MESSAGE),
              new ResourceModel("topic.message"), "", true);
      form.add(contentField);

      List<Tag> choises = tagService.findAllActive();
      BootstrapListMultipleChoice tagsField = new BootstrapListMultipleChoice<Tag>("tagsField", tagsModel,
              new ResourceModel("topic.tags"), new ListModel<>(choises), false);
      form.add(tagsField);
   }

   @Override
   protected Component getFocusComponent()
   {
      return titleField;
   }

   @Override
   public void onSubmit(AjaxRequestTarget target)
   {
      onSubmit(target, topicModel.getObject(), tagsModel.getObject());
   }

   protected abstract void onSubmit(AjaxRequestTarget target, Topic topic, List<Tag> tags);

   @Override
   public void show(AjaxRequestTarget target)
   {
      this.show(target, new Topic());
   }

   public void show(AjaxRequestTarget target, Topic topic)
   {
      super.show(target);
      topicModel.setObject(topic);
   }
}
