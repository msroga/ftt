package pl.ftt.core.component.form;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.validation.IValidator;
import pl.ftt.core.renderer.DefaultChoiceRenderer;
import pl.ftt.core.renderer.IStyledChoiceRenderer;

public class BootstrapListMultipleChoice<T> extends AbstractLabelComponent<ListMultipleChoice<T>>
{
   private ListMultipleChoice listMultipleChoice;

   private boolean styledRenderer;

   public BootstrapListMultipleChoice(String id, ListModel<T> selectedModel, ListModel<T> choices, boolean required)
   {
      this(id, selectedModel, null, choices, required);
   }

   public BootstrapListMultipleChoice(String id, ListModel<T> selectedModel, IModel<String> labelModel,
         ListModel<T> choices, boolean required)
   {
      super(id, labelModel);
      listMultipleChoice = new ListMultipleChoice<T>("listMultipleChoice", selectedModel, choices)
      {
         @Override
         public void renderHead(IHeaderResponse response)
         {
            super.renderHead(response);
            response.render(OnDomReadyHeaderItem.forScript("$('#" + listMultipleChoice.getMarkupId() + "').select2()"));
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
      };
      listMultipleChoice.setChoiceRenderer(new DefaultChoiceRenderer());
      listMultipleChoice.setOutputMarkupId(true);
      listMultipleChoice.setRequired(required);
      styledRenderer = listMultipleChoice.getChoiceRenderer() instanceof IStyledChoiceRenderer;
      container.add(listMultipleChoice);
   }

   @Override
   public ListMultipleChoice<T> getField()
   {
      return listMultipleChoice;
   }

   public void add(IValidator<T> validator)
   {
      listMultipleChoice.add(validator);
   }

   public void add(IValidator<T>... validators)
   {
      listMultipleChoice.add(validators);
   }

   public void setPlaceholder(IModel<String> placeholder)
   {
      listMultipleChoice.add(AttributeModifier.replace("data-placeholder", placeholder));
   }

   public void setRequired(boolean required)
   {
      listMultipleChoice.setRequired(required);
   }

   public void setChoiceRenderer(IChoiceRenderer<? super T> renderer)
   {
      styledRenderer = renderer instanceof IStyledChoiceRenderer;
      listMultipleChoice.setChoiceRenderer(renderer);
   }
}
