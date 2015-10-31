package pl.myo.gui.user.components;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.myo.core.component.form.DropDownWithLabel;
import pl.myo.core.component.form.EnumDropDownWithLabel;
import pl.myo.core.component.form.TextAreaWithLabel;
import pl.myo.core.component.window.DefaultWindow;
import pl.myo.domain.Opinion;
import pl.myo.domain.OpinionTypeEnum;
import pl.myo.domain.User;

import java.util.Arrays;

/**
 * Created by Marek on 2015-06-13.
 */
public abstract class AddOpinionWindow extends DefaultWindow
{
   private IModel<Opinion> model = new Model<>();

   public AddOpinionWindow(String id, IModel<String> header)
   {
      super(id, header);
      EnumDropDownWithLabel typeField = new EnumDropDownWithLabel<OpinionTypeEnum>("typeField",
              new PropertyModel<OpinionTypeEnum>(model, Opinion.FIELD_TYPE),
              new ResourceModel("opinion.type"), true, OpinionTypeEnum.class);
      form.add(typeField);

      TextAreaWithLabel<String> messageField = new TextAreaWithLabel<String>("messageField",
              new PropertyModel<String>(model, Opinion.FIELD_MESSAGE), new ResourceModel("opinion.message"), true);
      form.add(messageField);
   }

   @Override
   protected Component getFocusComponent()
   {
      return null;
   }

   @Override
   public void onSubmit(AjaxRequestTarget target)
   {
      onSubmit(target, model.getObject());
   }

   protected abstract void onSubmit(AjaxRequestTarget target, Opinion opinion);

   public void show(AjaxRequestTarget target, User doctor)
   {
      super.show(target);
      Opinion opinion = new Opinion();
      opinion.setDoctor(doctor);
      model.setObject(opinion);
   }
}
