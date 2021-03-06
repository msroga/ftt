package pl.myo.core.pages;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.template.PackageTextTemplate;
import pl.myo.core.component.DefaultLabel;
import pl.myo.core.component.JavaScriptLabel;
import pl.myo.core.component.ResourceMap;
import pl.myo.core.menu.MenuHolder;
import pl.myo.core.menu.MenuItem;
import pl.myo.core.menu.MenuPanel;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import pl.myo.domain.User;
import pl.myo.gui.user.UserDetailsPage;
import pl.myo.service.ISecurityService;

public class AbstractMenuPage extends AbstractPage 
{
   @SpringBean
   protected ISecurityService securityService;

	protected final MenuPanel menuPanel;

	protected final WebMarkupContainer bodyContainer;

	protected final IModel<String> bodyCssClassModel = new Model<>();

   protected final Label userNameLabel;

   protected final IModel<User> loggedUserModel = new Model<>();

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

      AjaxLink<User> loggedLink = new AjaxLink<User>("loggedLink", loggedUserModel)
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            setResponsePage(new UserDetailsPage(new PageContext<User>(loggedUserModel.getObject())));
         }
      };
      bodyContainer.add(loggedLink);

      userNameLabel = new DefaultLabel("loggedInUser", loggedUserModel);
      userNameLabel.setOutputMarkupId(true);
      loggedLink.add(userNameLabel);
	}

//   @Override
//   public void renderHead(IHeaderResponse response)
//   {
//      super.renderHead(response);
//      response.render(CssHeaderItem
//              .forCSS("body{ background-image: url('" + getBackgroundBodyImagePath() + "');};", "uniqueBodyBackground"));
//   }
//
//   private String getBackgroundBodyImagePath()
//   {
//      return "static\\img\\Elegant_Background-18.jpg";
//   }
}
