package pl.ftt.core.component;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.EmptyPanel;

public class FeedbackPanel extends org.apache.wicket.markup.html.panel.FeedbackPanel
{
   public FeedbackPanel(String id)
   {
      super(id);
   }

   public FeedbackPanel(String id, IFeedbackMessageFilter filter)
   {
      super(id, filter);
   }

   protected String getContainerClass(final FeedbackMessage message)
   {
      switch ( message.getLevel())
      {
         case FeedbackMessage.INFO:
            return "has-success";

         case FeedbackMessage.SUCCESS:
            return "has-success";

         case FeedbackMessage.WARNING:
            return "has-warning";

         case FeedbackMessage.ERROR:
            return "has-error";

         default:
            return "has-success";
      }
   }

   protected String getIconClass(FeedbackMessage message)
   {
      switch ( message.getLevel())
      {
         case FeedbackMessage.INFO:
            return "fa-info";

         case FeedbackMessage.SUCCESS:
            return "fa-checks";

         case FeedbackMessage.WARNING:
            return "fa-warning";

         case FeedbackMessage.ERROR:
            return "fa-times";

         default:
            return "fa-info";
      }
   }

   @Override
   protected String getCSSClass(FeedbackMessage message)
   {
      return null; //not used, because it would be applied onto both 'message' and 'label'
   }

   @Override
   protected Component newMessageDisplayComponent(String id, FeedbackMessage message)
   {
      Component label = super.newMessageDisplayComponent(id, message);
      WebMarkupContainer container = new WebMarkupContainer("container");
      container.add(AttributeModifier.append("class", this.getContainerClass(message)));
      container.add(new EmptyPanel("icon").add(AttributeModifier.append("class", this.getIconClass(message))));
      container.add(label);
      return container;
   }
}
