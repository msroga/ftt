package pl.ftt.core.pages;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.template.PackageTextTemplate;
import pl.ftt.core.component.DefaultLabel;
import pl.ftt.core.component.JavaScriptLabel;
import pl.ftt.core.component.ResourceMap;
import pl.ftt.core.menu.MenuHolder;
import pl.ftt.core.menu.MenuItem;
import pl.ftt.core.menu.MenuPanel;
import pl.ftt.domain.User;
import pl.ftt.gui.login.LoginPage;
import pl.ftt.gui.user.RegisterPage;
import pl.ftt.service.ISecurityService;

import java.util.List;
import java.util.Map;

public class AbstractMenuPage extends AbstractPage 
{
   protected final MenuPanel menuPanel;

   protected final WebMarkupContainer bodyContainer;

	protected final IModel<String> bodyCssClassModel = new Model<>();

   protected final Label userNameLabel;

   protected final IModel<User> loggedUserModel = new Model<>();

   @SpringBean
   protected ISecurityService securityService;

	public AbstractMenuPage() 
	{
		super();
      User user = securityService.getLoggedInUser();
      loggedUserModel.setObject(user);
      Map<String, String> map = new ResourceMap(
              this,
              getLocale(),
              "message.logout",
              "message.logout.info",
              "message.button.yes",
              "message.button.no");

		bodyContainer = new WebMarkupContainer("bodyContainer");
		bodyContainer
				.add(AttributeModifier.replace("class", bodyCssClassModel));
		add(bodyContainer);
		List<MenuItem> menuItems = MenuHolder.getInstance().getFiltered();

      bodyContainer.add(new JavaScriptLabel("messagesScript", new PackageTextTemplate(
              AbstractMenuPage.class,
              "AbstractMenuPage.js"), map));

		menuPanel = new MenuPanel("menuPanel", menuItems);
		bodyContainer.add(menuPanel);

      WebMarkupContainer loggedInUserContainer = new WebMarkupContainer("loggedInUserContainer");
      loggedInUserContainer.setOutputMarkupId(true);
      loggedInUserContainer.setVisible(loggedUserModel.getObject() != null);

      AjaxLink<User> loggedLink = new AjaxLink<User>("loggedLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            //todo profil page
//            setResponsePage(new UserDetailsPage(new PageContext<User>(loggedUserModel.getObject())));
         }
      };
      loggedInUserContainer.add(loggedLink);
      bodyContainer.add(loggedInUserContainer);

      userNameLabel = new DefaultLabel("loggedInUser", loggedUserModel);
      userNameLabel.setOutputMarkupId(true);
      loggedLink.add(userNameLabel);

      WebMarkupContainer unLoggedUserContainer = new WebMarkupContainer("unLoggedUserContainer");
      unLoggedUserContainer.setOutputMarkupId(true);
      unLoggedUserContainer.setVisible(loggedUserModel.getObject() == null);
      bodyContainer.add(unLoggedUserContainer);

      AjaxLink loginLink = new AjaxLink("loginLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            setResponsePage(LoginPage.class);
         }
      };
      unLoggedUserContainer.add(loginLink);


      AjaxLink registerLink = new AjaxLink("registerLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            setResponsePage(RegisterPage.class);
         }
      };
      unLoggedUserContainer.add(registerLink);
	}
}
