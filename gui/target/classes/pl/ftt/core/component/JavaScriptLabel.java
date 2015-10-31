package pl.ftt.core.component;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.template.JavaScriptTemplate;
import org.apache.wicket.util.template.TextTemplate;

import java.util.Map;

public class JavaScriptLabel extends Label
{
   public JavaScriptLabel(String id, TextTemplate textTemplate)
   {
      this(id, textTemplate, null);
   }

   public JavaScriptLabel(String id, TextTemplate textTemplate, Map<String, String> values)
   {
      super(id);
      String javaScript = new JavaScriptTemplate(textTemplate).asString(values);
      setDefaultModel(new Model(javaScript));
      setEscapeModelStrings(false);
   }

}
