package pl.ftt.core.component.table.colums;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import pl.ftt.gui.formatter.Formatters;

import java.io.Serializable;

public class DefaultColumn<T extends Serializable> extends PropertyColumn<T, String>
{
   private static final long serialVersionUID = 7291536676669017356L;

   private Component component;

   public DefaultColumn(IModel<String> displayModel, String sortProperty, String propertyExpression,
                        Component component)
   {
      super(displayModel, sortProperty, propertyExpression);
      this.component = component;
   }

   public DefaultColumn(IModel<String> displayModel, String propertyExpression, Component component)
   {
      this(displayModel, null, propertyExpression, component);
   }

   public DefaultColumn(IModel<String> displayModel, String sortProperty, String propertyExpression)
   {
      this(displayModel, sortProperty, propertyExpression, null);
   }

   public DefaultColumn(IModel<String> displayModel, String propertyExpression)
   {
      this(displayModel, null, propertyExpression, null);
   }

   @Override
   public IModel<Object> getDataModel(IModel<T> rowModel)
   {
      final IModel<Object> original = super.getDataModel(rowModel);
      return new AbstractReadOnlyModel()
      {
         private static final long serialVersionUID = -4455890114282998964L;

         @Override
         public String getObject()
         {
            T object = (T) original.getObject();
            return Formatters.format(object, component);
         }
      };
   }

   @Override
   public String getCssClass()
   {
      return "";
   }
}
