package pl.myo.gui.user.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.joda.time.DateTime;
import pl.myo.core.component.table.SimpleTableHeader;
import pl.myo.core.component.window.DefaultWindow;
import pl.myo.domain.User;
import pl.myo.service.IUserService;

/**
 * Created by Marek on 2015-06-13.
 */
public abstract class OpinionTableHeader extends SimpleTableHeader
{
   @SpringBean
   private IUserService userService;

   public OpinionTableHeader(String id, IModel<String> caption, IModel<User> loggedModel, IModel<User> doctorModel)
   {
      this(id, caption, loggedModel, doctorModel, "fa fa-table");
   }

   public OpinionTableHeader(String id, IModel<String> caption, final IModel<User> loggedModel,
                             final IModel<User> doctorModel, String icon)
   {
      super(id, caption, icon);
      AjaxLink addLink = new AjaxLink("addLink")
      {
         @Override
         public void onClick(AjaxRequestTarget target)
         {
            onAddClick(target);
         }

         @Override
         protected void onConfigure()
         {
            super.onConfigure();
            User logged = loggedModel.getObject();
            User doctor = doctorModel.getObject();
            //utworzony mniej niz dwa tygodnie temu i nie przypisany do lekarza nie moze dodawac opini
            //todo property for days
            if (logged.getCreatedDate().isAfter(DateTime.now().minusDays(14)) && userService.isAssigned(logged, doctor))
            {
               setVisible(false);
            }
            else
            {
               setVisible(true);
            }

         }
      };
      add(addLink);
   }

   protected abstract void onAddClick(AjaxRequestTarget target);

}
