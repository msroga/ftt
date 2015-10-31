package pl.myo.core.component.form;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;
import pl.myo.core.component.field.EnumDropDownField;

/**
 * Created by Marek on 2015-05-27.
 */
public class EnumDropDownWithLabel<T extends Enum<T>> extends AbstractLabelComponent<EnumDropDownField<T>>
{
   private final EnumDropDownField<T> enumDropDownField;

   public EnumDropDownWithLabel(String id, IModel<T> model, boolean required, Class<T> enumClass, T... exclude)
   {
      this(id, model, null, required, enumClass, exclude);
   }

   public EnumDropDownWithLabel(String id, IModel<T> model, IModel<String> labelModel, boolean required,
                                      Class<T> enumClass, T... exclude)
   {
      super(id, labelModel);
      enumDropDownField = new EnumDropDownField<T>("dropDownChoice", model, enumClass, exclude);
      enumDropDownField.setNullValid(false);
      enumDropDownField.setRequired(required);
      container.add(enumDropDownField);
   }

   public EnumDropDownWithLabel(String id, IModel<T> model, IModel<String> labelModel, boolean required,
                                      T... include)
   {
      super(id, labelModel);
      enumDropDownField = new EnumDropDownField<T>("dropDownChoice", model, include);
      enumDropDownField.setNullValid(false);
      enumDropDownField.setRequired(required);
      container.add(enumDropDownField);
   }

   @Override
   public EnumDropDownField<T> getField()
   {
      return enumDropDownField;
   }

   public EnumDropDownWithLabel setNullValid(boolean nullValid)
   {
      enumDropDownField.setNullValid(nullValid);
      return this;
   }

   public void add(IValidator<T> validator)
   {
      enumDropDownField.add(validator);
   }

   public void add(IValidator<T>... validators)
   {
      enumDropDownField.add(validators);
   }

   public EnumDropDownWithLabel setRequired(boolean required)
   {
      enumDropDownField.setRequired(required);
      return this;
   }

   public void setChoiceRenderer(IChoiceRenderer renderer)
   {
      enumDropDownField.setChoiceRenderer(renderer);
   }
}
