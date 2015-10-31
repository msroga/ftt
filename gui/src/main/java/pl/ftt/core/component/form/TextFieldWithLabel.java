package pl.ftt.core.component.form;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

/**
 * Created by Marek on 2015-05-26.
 */
public class TextFieldWithLabel<T> extends AbstractLabelComponent<TextField<T>>
{
   private final TextField<T> textField;

   public TextFieldWithLabel(String id, IModel<T> model, boolean required, IValidator<? super T>... validators)
   {
      this(id, model, null, required, validators);
   }

   public TextFieldWithLabel(String id, IModel<T> model, IModel<String> labelModel, boolean required,
                             IValidator<? super T>... validators)
   {
      super(id, labelModel);

      textField = new TextField<T>("textField", model);
      textField.setRequired(required);
      if (validators != null)
      {
         textField.add(validators);
      }
      container.add(textField);
   }

   public void add(IValidator<? super T> validator)
   {
      textField.add(validator);
   }

   public void setType(Class<?> type)
   {
      textField.setType(type);
   }

   public void add(IValidator<? super T>... validators)
   {
      textField.add(validators);
   }

   public void setRequired(boolean required)
   {
      textField.setRequired(required);
   }

   public void setPlaceholder(IModel<String> placeholder)
   {
      textField.add(new AttributeModifier("placeholder", placeholder));
   }

   @Override
   public TextField<T> getField()
   {
      return textField;
   }
}
