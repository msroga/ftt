package pl.myo.core.component.form;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

/**
 * Created by Marek on 2015-06-13.
 */
public class TextAreaWithLabel<T> extends AbstractLabelComponent<TextArea<T>>
{
   private TextArea<T> textArea;

   public TextAreaWithLabel(String id, IModel<T> model, boolean required, IValidator<? super T>... validators)
   {
      this(id, model, null, required, validators);
   }

   public TextAreaWithLabel(String id, IModel<T> model, IModel<String> labelModel, boolean required, IValidator<? super T>... validators)
   {
      super(id, labelModel);

      textArea = new TextArea<T>("textArea", model);
      textArea.setRequired(required);
      if (validators != null)
      {
         textArea.add(validators);
      }
      container.add(textArea);
   }

   @Override
   public TextArea<T> getField()
   {
      return textArea;
   }

   public void setRows(int rows)
   {
      textArea.add(new AttributeModifier("rows", rows));
   }

   public void add(IValidator<? super T> validator)
   {
      textArea.add(validator);
   }

   public void add(IValidator<? super T>... validators)
   {
      textArea.add(validators);
   }

   public void setPlaceholder(IModel<String> placeholder)
   {
      textArea.add(new AttributeModifier("placeholder", placeholder));
   }

   public void setRequired(boolean required)
   {
      textArea.setRequired(required);
   }
}
