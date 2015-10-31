package pl.myo.core.component.portlet;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import pl.myo.core.component.form.AbstractLabelComponent;

public class BootstrapViewLabel extends AbstractLabelComponent<ViewLabel>
{
   private final ViewLabel viewLabel;

   public BootstrapViewLabel(String id, IModel<String> labelModel, IValuesFormatter valuesFormatter, IModel<?>... models)
   {
      super(id, labelModel);
      viewLabel = new ViewLabel("viewLabel", valuesFormatter, models);
      container.add(viewLabel);
   }

   public BootstrapViewLabel(String id,IModel<String> labelModel, Component component, IModel... models)
   {
      super(id, labelModel);
      viewLabel = new ViewLabel("viewLabel", component, models);
      container.add(viewLabel);
   }

   @Override
   public ViewLabel getField()
   {
      return viewLabel;
   }

   public boolean isEscapeHtml()
   {
      return viewLabel.isEscapeHtml();
   }

   public BootstrapViewLabel setEscapeHtml(boolean escapeHtml)
   {
      viewLabel.setEscapeHtml(escapeHtml);
      return this;
   }
}
