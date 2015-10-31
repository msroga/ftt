package pl.ftt.core.component.field;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;
import pl.ftt.domain.utils.EnumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumDropDownField<T extends Enum<T>> extends DropDownChoice<T>
{
   private static final long serialVersionUID = 9018603396362505817L;

   public EnumDropDownField(String id, IModel<T> model, Class<T> enumClass, T... exclude)
   {
      this(id, model, enumClass, new EnumChoiceRenderer(), exclude);
   }

   public EnumDropDownField(final String id, IModel<T> model, T... include)
   {
      super(id, model, (List<T>) null, new EnumChoiceRenderer());
      List<T> enums = new ArrayList<T>();
      enums.addAll(Arrays.asList(include));
      setChoices(enums);
   }

   public EnumDropDownField(final String id, IModel<T> model, Class<T> enumClass, IChoiceRenderer<T> choiceRenderer,
                            T... exclude)
   {
      super(id, model, (List<T>) null, choiceRenderer);
      List<T> enums = EnumUtils.exclude(enumClass, exclude);
      setChoices(enums);
   }
}
