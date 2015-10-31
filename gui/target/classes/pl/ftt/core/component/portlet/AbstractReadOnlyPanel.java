package pl.ftt.core.component.portlet;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Args;
import pl.ftt.domain.AbstractEntity;

public abstract class AbstractReadOnlyPanel<T extends AbstractEntity> extends GenericPanel<T>
{
   public AbstractReadOnlyPanel(String id, IModel<T> model)
   {
      super(id, model);
   }

   public BootstrapViewLabel newViewLabel(String id,IModel<String> labelModel, String... expressions)
   {
      return newViewLabel(id, labelModel, new DefaultValuesFormatter(), expressions);
   }

   public BootstrapViewLabel newViewLabel(String id, IModel<String> labelModel, Component component, String... expressions)
   {
      return newViewLabel(id, labelModel, new DefaultValuesFormatter(component), expressions);
   }

   public BootstrapViewLabel newViewLabel(String id, IModel<String> labelModel, IValuesFormatter valuesFormatter, String... expressions)
   {
      Args.notNull(expressions, "expressions are null");
      IModel[] models = new IModel[expressions.length];
      int i = 0;
      for (String expression : expressions)
      {
         models[i] = new PropertyModel(getDefaultModel(), expression);
         i++;
      }

      BootstrapViewLabel viewLabel = new BootstrapViewLabel(id, labelModel, valuesFormatter, models);
      return viewLabel;
   }

   public BootstrapViewLabel newViewLabel(String id, IModel<String> labelModel, IModel<?>... models)
   {
      return newViewLabel(id, labelModel, new DefaultValuesFormatter(), models);
   }

   public BootstrapViewLabel newViewLabel(String id, IModel<String> labelModel, IValuesFormatter valuesFormatter, IModel<?>... models)
   {
      Args.notNull(models, "models are null");
      BootstrapViewLabel viewLabel = new BootstrapViewLabel(id, labelModel, valuesFormatter, models);
      return viewLabel;
   }
}
