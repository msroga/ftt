package pl.ftt.core.component.table.colums;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;

import java.io.Serializable;

public class StyledPropertyColumn<T extends Serializable> extends PropertyColumn<T, String>
{
   public StyledPropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression)
   {
      super(displayModel, sortProperty, propertyExpression);
   }

   public StyledPropertyColumn(IModel<String> displayModel, String propertyExpression)
   {
      this(displayModel, null, propertyExpression);
   }

   @Override
   public String getCssClass()
   {
      return null;//CssUtils.getCssClass(StyledPropertyColumn.class, getPropertyExpression());
   }
}
