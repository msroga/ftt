package pl.ftt.gui.tags.components;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.ftt.core.component.form.TextAreaWithLabel;
import pl.ftt.core.component.form.TextFieldWithLabel;
import pl.ftt.core.component.window.DefaultWindow;
import pl.ftt.domain.Tag;

/**
 * Created by Marek on 2015-12-19.
 */
public abstract class TagWindow extends DefaultWindow
{
   private final IModel<Tag> model = new Model<>();

   public TagWindow(String id, IModel<String> header)
   {
      super(id, header);
      TextFieldWithLabel<String> nameField = new TextFieldWithLabel<String>("name",
              new PropertyModel<String>(model, Tag.FIELD_NAME), new ResourceModel("tag.name"), true);
      form.add(nameField);

      TextAreaWithLabel<String> descriptionField = new TextAreaWithLabel<String>("description",
              new PropertyModel<String>(model, Tag.FIELD_DESCRIPTION), new ResourceModel("tag.description"), false);
      form.add(descriptionField);
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

   @Override
   public void show(AjaxRequestTarget target)
   {
      show(target, new Tag());
   }

   public void show(AjaxRequestTarget target, Tag tag)
   {
      super.show(target);
      model.setObject(tag);
   }

   protected abstract void onSubmit(AjaxRequestTarget target, Tag tag);
}
