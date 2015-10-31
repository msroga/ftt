package pl.myo.core.component.portlet;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.EnumChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.IValidator;
import org.joda.time.LocalDate;
import pl.myo.domain.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEditablePanel<T extends AbstractEntity> extends AbstractReadOnlyPanel<T>
{
   protected Form form;

   protected List<AbstractFormValidator> validators = new ArrayList<AbstractFormValidator>();

   protected IFormSubmittingComponent defaultSubmitButton;

   public AbstractEditablePanel(String id, IModel<T> model)
   {
      super(id, model);
   }

   @Override
   protected void onInitialize()
   {
      super.onInitialize();
      form = findParent(Form.class);
      for (AbstractFormValidator validator : validators)
      {
         form.add(validator);
      }
      if (defaultSubmitButton != null)
      {
         form.setDefaultButton(defaultSubmitButton);
      }
   }

   public void addFormValidator(AbstractFormValidator validator)
   {
      validators.add(validator);
   }

   public IFormSubmittingComponent getDefaultSubmitButton()
   {
      return defaultSubmitButton;
   }

   public void setDefaultSubmitButton(IFormSubmittingComponent defaultSubmitButton)
   {
      this.defaultSubmitButton = defaultSubmitButton;
   }
}
