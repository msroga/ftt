package pl.ftt.gui.login;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.apache.wicket.util.template.PackageTextTemplate;
import pl.ftt.core.component.FeedbackPanel;
import pl.ftt.core.component.JavaScriptLabel;
import pl.ftt.core.component.ResourceMap;
import pl.ftt.core.pages.AbstractPage;
import pl.ftt.service.IConfigurationService;

import java.util.Map;

public class LoginPage extends AbstractPage
{
   @SpringBean
   private IConfigurationService configurationService;

   private Form loginForm;


   public LoginPage()
   {
      super();

      loginForm = new Form("signInForm")
      {
         @Override
         protected void onComponentTag(ComponentTag tag)
         {
            super.onComponentTag(tag);
            tag.put(
                    "action",
                    WebApplication.get().getServletContext().getContextPath()
                            + configurationService.getConfiguration("security.auth.path"));
            tag.put("method", "post");
         }
      };
      loginForm.add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(loginForm)));
      add(loginForm);

      Map<String, String> map = new ResourceMap(this, null, "empty.login", "empty.password");
      add(new JavaScriptLabel("script", new PackageTextTemplate(LoginPage.this.getClass(), "LoginPage.js"), map));

      AjaxLink registerLink = new AjaxLink("registerLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
//            setResponsePage(RegisterPage.class);
         }
      };
      loginForm.add(registerLink);
   }

   public LoginPage(PageParameters pageParameters)
   {
      this();
      StringValue errorValue = pageParameters.get("failure");
      if (!errorValue.isNull() && errorValue.toBoolean())
      {
         loginForm.error(getString("login.failed.wrong.login.or.password"));
      }
   }
}
