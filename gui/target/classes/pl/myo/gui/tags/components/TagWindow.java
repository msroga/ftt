package pl.myo.gui.tags.components;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import pl.myo.core.component.form.TextFieldWithLabel;
import pl.myo.core.component.window.DefaultWindow;
import pl.myo.domain.Tag;

/**
 * Created by Marek on 2015-05-28.
 */
public abstract class TagWindow extends DefaultWindow
{
   private final IModel<Tag> tagModel = new Model<>();

   private TextFieldWithLabel nameField;

   public TagWindow(String id, IModel<String> header)
   {
      super(id, header);

      nameField = new TextFieldWithLabel("nameField", new PropertyModel(tagModel, Tag.FIELD_NAME),
              new ResourceModel("tag.name"), true);
      form.add(nameField);
   }

   @Override
   protected Component getFocusComponent()
   {
      return nameField;
   }

   @Override
   public void onSubmit(AjaxRequestTarget target)
   {
      onSubmit(target, tagModel.getObject());
   }

   protected abstract void onSubmit(AjaxRequestTarget target, Tag tag);

   @Override
   public void show(AjaxRequestTarget target)
   {
      super.show(target);
      Tag tag = new Tag();
      tag.setActive(true);
      tagModel.setObject(tag);
   }
}
