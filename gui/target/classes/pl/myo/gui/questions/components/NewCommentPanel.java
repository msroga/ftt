package pl.myo.gui.questions.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import pl.myo.core.component.form.AbstractLabelComponent;
import pl.myo.core.component.form.BootstrapComponentVisitor;
import pl.myo.core.component.form.BootstrapEditor;
import pl.myo.domain.Comment;

/**
 * Created by Marek on 2015-05-30.
 */
public abstract class NewCommentPanel extends Panel
{
   private final IModel<Comment> model = new Model<>();

   public NewCommentPanel(String id)
   {
      super(id);
      setOutputMarkupId(true);
      final Form form = new Form("form");
      add(form);

      BootstrapEditor editor = new BootstrapEditor("editor", new PropertyModel<String>(model, Comment.FIELD_MESSAGE), true);
      editor.setLabelVisible(false);
      editor.setSize(12);
      form.add(editor);

      AjaxSubmitLink submitButton = new AjaxSubmitLink("submit")
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            NewCommentPanel.this.onSubmit(target, model.getObject());
            model.setObject(new Comment());
            target.add(NewCommentPanel.this);
         }

         @Override
         protected void onError(AjaxRequestTarget target, Form<?> form)
         {
            super.onError(target, form);
            target.add(form);
         }

         @Override
         protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
         {
            super.updateAjaxAttributes(attributes);
            form.visitChildren(AbstractLabelComponent.class, new BootstrapComponentVisitor(attributes));
         }
      };
      form.add(submitButton);
      form.setDefaultButton(submitButton);
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      model.setObject(new Comment());
   }

   protected abstract void onSubmit(AjaxRequestTarget target, Comment comment);
}

