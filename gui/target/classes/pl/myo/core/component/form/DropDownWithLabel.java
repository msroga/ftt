package pl.myo.core.component.form;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.IValidator;

import java.util.List;

/**
 * Created by Marek on 2015-05-27.
 */
public class DropDownWithLabel<T> extends AbstractLabelComponent<DropDownChoice<T>>
{
   private DropDownChoice<T> dropDownChoice;

   public DropDownWithLabel(String id, IModel<T> model, List<? extends T> choices, boolean required)
   {
      this(id, model, null, choices, required);
   }

   public DropDownWithLabel(final String id, IModel<T> model, IModel<String> labelModel, List<? extends T> choices,
                            boolean required)
   {
      super(id, labelModel);
      dropDownChoice = new DropDownChoice<T>("dropDownChoice", model, choices, new ChoiceRenderer<T>())
      {
         protected String getNullValidDisplayValue()
         {
            String option = super.getNullValidDisplayValue();
            if (Strings.isEmpty(option))
            {
               return getLocalizer().getString(id + ".nullValid", this, "");
            }
            return option;
         }
      };
      dropDownChoice.setRequired(required);
      container.add(dropDownChoice);
   }

   public DropDownWithLabel(String id, IModel<T> model, IModel<? extends List<? extends T>> choices,
                            boolean required)
   {
      this(id, model, null, choices, required);
   }

   public DropDownWithLabel(final String id, IModel<T> model, IModel<String> labelModel,
                            IModel<? extends List<? extends T>> choices,
                            boolean required)
   {
      super(id, labelModel);
      dropDownChoice = new DropDownChoice<T>("dropDownChoice", model, choices, new ChoiceRenderer<T>())
      {
         protected String getNullValidDisplayValue()
         {
            String option = super.getNullValidDisplayValue();
            if (Strings.isEmpty(option))
            {
               return getLocalizer().getString(id + ".nullValid", this, "");
            }
            return option;
         }
      };
      dropDownChoice.setRequired(required);
      container.add(dropDownChoice);
   }

   @Override
   public DropDownChoice<T> getField()
   {
      return dropDownChoice;
   }

   public void setNullValid(boolean nullValid)
   {
      dropDownChoice.setNullValid(nullValid);
   }

   public void add(IValidator<T> validator)
   {
      dropDownChoice.add(validator);
   }

   public void add(IValidator<T>... validators)
   {
      dropDownChoice.add(validators);
   }

   public void setRequired(boolean required)
   {
      dropDownChoice.setRequired(required);
   }

   public void setChoiceRenderer(IChoiceRenderer<? super T> renderer)
   {
      dropDownChoice.setChoiceRenderer(renderer);
   }
}