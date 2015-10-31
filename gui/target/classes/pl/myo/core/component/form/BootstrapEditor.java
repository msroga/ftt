package pl.myo.core.component.form;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.IValidator;

import java.util.Random;

public class BootstrapEditor extends AbstractLabelComponent<TextArea<String>>
{
   private static final Random RANDOM = new Random();

   private TextArea<String> textArea;

   public static final String AUTOGROW = "initCKEditorAutoGrow";

   public static final String READONLY = "initCKEditorReadOnly";

   public static final String WITH_KEYS = "initCKEditorWithKeys";

   public static final String DEFAULT = "initCKEditor";

   private String initFunction = DEFAULT;

   private final String editorId;

   public BootstrapEditor(String id, IModel<String> model, IModel<String> labelModel, String cssClass, boolean required,
         IValidator<? super String>... validators)
   {
      super(id, labelModel);

      textArea = new TextArea<String>("textArea", model);
      textArea.setRequired(required);
      if (validators != null)
      {
         textArea.add(validators);
      }
      container.add(textArea);
      editorId = "editor" + Math.abs(RANDOM.nextInt());
      textArea.add(new AttributeModifier("id", editorId));
   }

   public BootstrapEditor(String id, IModel<String> model, boolean required, IValidator<? super String>... validators)
   {
      this(id, model, null, null, required, validators);
   }

   @Override
   public TextArea<String> getField()
   {
      return textArea;
   }

   public void setRows(int rows)
   {
      textArea.add(new AttributeModifier("rows", rows));
   }

   public void add(IValidator<? super String> validator)
   {
      textArea.add(validator);
   }

   public void add(IValidator<? super String>... validators)
   {
      textArea.add(validators);
   }

   public void setRequired(boolean required)
   {
      textArea.setRequired(required);
   }

   @Override
   public void renderHead(IHeaderResponse response)
   {
      super.renderHead(response);
      String language = getSession().getLocale().getLanguage();
      response.render(OnDomReadyHeaderItem.forScript(initFunction + "('" + editorId + "','" + language + "');"
            + "var editor = CKEDITOR.instances." + editorId + ";"));
   }

   public String getEditorId()
   {
      return editorId;
   }

   public void setInitFunction(String initFunction)
   {
      this.initFunction = initFunction;
   }

   @Override
   public String getBeforeScript()
   {
      return "var editor = CKEDITOR.instances." + editorId + "; $('#" + editorId + "').val(editor.getData());";
   }
}
