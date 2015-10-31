package pl.myo.core.component.form;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

/**
 * Created by Marek on 2015-05-27.
 */
public class PasswordFieldWithLabel extends AbstractLabelComponent<PasswordTextField>
{
   private final PasswordTextField passwordTextField;

   public PasswordFieldWithLabel(String id, IModel<String> model, boolean required, IValidator<String>... validators)
   {
      this(id, model, null, required, true, validators);
   }

   public PasswordFieldWithLabel(String id, IModel<String> model, IModel<String> labelModel, boolean required,
                                 boolean resetPassword, IValidator<String>... validators)
   {
      super(id, labelModel);
      passwordTextField = new PasswordTextField("passwordField", model);
      passwordTextField.setResetPassword(resetPassword);
      passwordTextField.setRequired(required);
      if (validators != null)
      {
         passwordTextField.add(validators);
      }
      container.add(passwordTextField);
   }

   @Override
   public PasswordTextField getField()
   {
      return passwordTextField;
   }

   public void add(IValidator<String> validator)
   {
      passwordTextField.add(validator);
   }

   public void add(IValidator<String>... validators)
   {
      passwordTextField.add(validators);
   }

   public void setRequired(boolean required)
   {
      passwordTextField.setRequired(required);
   }
}

