package pl.myo.gui.articles;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import pl.myo.core.component.DefaultLabel;
import pl.myo.core.pages.AbstractDetailsPage;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.Comment;
import pl.myo.domain.Topic;

/**
 * Created by Marek on 2015-05-28.
 */
public class ArticleDetailsPage extends AbstractDetailsPage<Topic>
{
   private final IModel<Topic> model = new Model<>();

   public ArticleDetailsPage(PageContext<Topic> pageContext)
   {
      super();
      model.setObject(pageContext.getContext());
      Label titleLabel = new Label("titleLabel", new PropertyModel<>(model, Topic.FIELD_TITLE));
      bodyContainer.add(titleLabel);
      DefaultLabel dateLabel = new DefaultLabel("dateLabel", new PropertyModel<>(model, Topic.FIELD_CREATED));
      bodyContainer.add(dateLabel);
      DefaultLabel userLabel = new DefaultLabel("userLabel", new PropertyModel<>(model, Topic.FIELD_USER));
      bodyContainer.add(userLabel);

      Label messageLabel = new Label("messageLabel", new PropertyModel<>(model, Topic.FIELD_MESSAGE));
      messageLabel.setEscapeModelStrings(false);
      bodyContainer.add(messageLabel);
   }
}
