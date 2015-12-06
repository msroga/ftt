package pl.ftt.core.component.form;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.validation.IValidator;
import pl.ftt.core.renderer.IStyledChoiceRenderer;

public class SearchableListChoice<T> extends AbstractLabelComponent<DropDownChoice<T>>
{
   public static final int DEFAULT_MIN_INPUT_LENGTH = 0;

   private DropDownChoice<T> dropDownChoice;

   private boolean styledRenderer;

   private int minInputLength = DEFAULT_MIN_INPUT_LENGTH;

   public SearchableListChoice(String id, IModel<T> model, ListModel<T> choices, boolean required)
   {
      this(id, model, null, choices, required);
   }

   public SearchableListChoice(String id, IModel<T> model, IModel<String> labelModel, ListModel<T> choices,
         boolean required)
   {
      super(id, labelModel);
      dropDownChoice = new DropDownChoice<T>("dropDownChoice", model, choices, new ChoiceRenderer<T>())
      {
         @Override
         public void renderHead(IHeaderResponse response)
         {
            super.renderHead(response);
            response.render(OnDomReadyHeaderItem.forScript(
                    "$('#" + dropDownChoice.getMarkupId() + "').select2({minimumInputLength: " + minInputLength + "})"));
         }

         @Override
         protected void setOptionAttributes(AppendingStringBuffer buffer, T choice, int index, String selected)
         {
            super.setOptionAttributes(buffer, choice, index, selected);
            if (styledRenderer)
            {
               IChoiceRenderer<? super T> renderer = getChoiceRenderer();
               IStyledChoiceRenderer styledChoiceRenderer = (IStyledChoiceRenderer<T>) renderer;
               String clazz = styledChoiceRenderer.getOptionClass(choice);
               if (clazz != null)
               {
                  buffer.append("class=\"" + clazz + "\"");
               }
            }
         }

         @Override
         protected String getNullValidDisplayValue()
         {
            String currentOption = super.getNullValidDisplayValue();
            return SearchableListChoice.this.getNullValidDisplayValue(currentOption);
         }
      };
      dropDownChoice.setRequired(required);
      styledRenderer = dropDownChoice.getChoiceRenderer() instanceof IStyledChoiceRenderer;
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

   public String getNullValidDisplayValue(String currentOption)
   {
      return currentOption;
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
      styledRenderer = renderer instanceof IStyledChoiceRenderer;
   }

   public SearchableListChoice<T> setMinInputLength(int minInputLength)
   {
      this.minInputLength = minInputLength;
      return this;
   }
}
