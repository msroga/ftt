package pl.myo.gui.user;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import pl.myo.core.component.portlet.BootstrapViewLabel;
import pl.myo.core.notification.Notification;
import pl.myo.core.pages.AbstractDetailsPage;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.Opinion;
import pl.myo.domain.User;
import pl.myo.gui.user.components.AddOpinionWindow;
import pl.myo.gui.user.components.UserDetailsPanel;
import pl.myo.gui.user.components.UserOpinionPanel;
import pl.myo.service.IOpinionService;

import java.io.Serializable;

/**
 * Created by Marek on 2015-05-27.
 */
//@AuthorizeInstantiation(AuthorityKeys.EDIT_USER_VIEW)
//@AuthorizeType(employee = true, client = true, subcontractor = true)
public class UserDetailsPage extends AbstractDetailsPage<User>
{
   public static final String EDIT_PORTLET = "EDIT_PORTLET";

   private UserDetailsPanel detailsPanel;

   private UserOpinionPanel opinionPanel;

   private AddOpinionWindow opinionWindow;

   private final IModel<User> model = new Model<>();

   @SpringBean
   private IOpinionService opinionService;

   public UserDetailsPage()
   {
      this(new PageContext<User>(new User()).putParam(EDIT_PORTLET, true));
   }

   public UserDetailsPage(PageContext<User> context)
   {
      super();
      Serializable editParam = context.getParams().get(EDIT_PORTLET);
      Boolean editPortlet = editParam == null ? false : (Boolean) editParam;
      model.setObject(context.getContext());
      detailsPanel = new UserDetailsPanel("details", model, editPortlet);
      bodyContainer.add(detailsPanel);

      opinionPanel = new UserOpinionPanel("opinions", model, loggedUserModel)
      {
         @Override
         protected void onAddClick(AjaxRequestTarget target, User doctor)
         {
            if (!opinionService.isOpinioned(loggedUserModel.getObject(), doctor))
            {
               opinionWindow.show(target, doctor);
            }
            else
            {
               Notification.warn(getString("warn.opinion.is.created"));
            }
         }
      };
      bodyContainer.add(opinionPanel);

      opinionWindow = new AddOpinionWindow("opinionWindow", new ResourceModel("add.opinion.window.header"))
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Opinion opinion)
         {
            opinionService.save(opinion);
            Notification.success(getString("success.opinion.created"));
            target.add(opinionPanel);
         }
      };
      bodyContainer.add(opinionWindow);
   }
}
