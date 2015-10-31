package pl.ftt.core.component.form;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.lang.Args;
import pl.ftt.core.component.FeedbackPanel;

public abstract class AbstractLabelComponent<T extends Component> extends Panel
{
   private final int DEFAULT_SIZE = 10; // sumuje sie do 12

   protected int size = DEFAULT_SIZE;

   protected int labelSize = 12 - DEFAULT_SIZE;

   protected IModel<String> resourceModel;

   protected boolean labelVisible = true;

   protected FeedbackPanel feedbackPanel;

   protected WebMarkupContainer container;

   protected String cssClass = "form-group";

   public AbstractLabelComponent(String id, IModel<String> resourceModel)
   {
      super(id);
      this.resourceModel = resourceModel;
      container = new WebMarkupContainer("container");
      add(container);
   }

   @Override
   protected void onInitialize()
   {
      super.onInitialize();
      Label label = newLabel("label");
      add(label);

      T component = getField();
      appendContainerClass();
      container.add(feedbackPanel = new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(component)));
   }

   protected void appendContainerClass()
   {
      container.add(AttributeAppender.append("class", "col-sm-" + size));
   }

   protected Label newLabel(String id)
   {
      Label label = new Label(id, newLabelModel());
      label.setVisible(labelVisible);
      label.add(AttributeAppender.append("class", "col-sm-" + labelSize));
      return label;
   }

   protected IModel<String> newLabelModel()
   {
      IModel<String> labelModel = resourceModel != null ? resourceModel : new ResourceModel(getId());
      return labelModel;
   }

   public abstract T getField();

   public int getSize()
   {
      return size;
   }

   public AbstractLabelComponent setSize(int size)
   {
      Args.isTrue(size >= 1 && size <= 12, "invalid size");
      this.size = size;
      return this;
   }

   public boolean isLabelVisible()
   {
      return labelVisible;
   }

   public AbstractLabelComponent setLabelVisible(boolean labelVisible)
   {
      this.labelVisible = labelVisible;
      return this;
   }

   public String getBeforeScript()
   {
      return null;
   }

   public String getAfterScript()
   {
      return null;
   }

   @Override
   protected void onComponentTag(ComponentTag tag)
   {
      super.onComponentTag(tag);
      tag.put("class", cssClass);
      if (feedbackPanel.anyMessage(FeedbackMessage.ERROR))
      {
         tag.append("class", "has-error", " ");
      }
      else if (feedbackPanel.anyMessage(FeedbackMessage.WARNING))
      {
         tag.append("class", "has-warning", " ");
      }
      else if (feedbackPanel.anyMessage(FeedbackMessage.SUCCESS) || feedbackPanel.anyMessage(FeedbackMessage.INFO))
      {
         tag.append("class", "has-success", " ");
      }
   }

   public int getLabelSize()
   {
      return labelSize;
   }

   public AbstractLabelComponent setLabelSize(int labelSize)
   {
      Args.isTrue(size >= 1 && size <= 12, "invalid size");
      this.labelSize = labelSize;
      return this;
   }

   public String getCssClass()
   {
      return cssClass;
   }

   public void setCssClass(String cssClass)
   {
      this.cssClass = cssClass;
   }
}
