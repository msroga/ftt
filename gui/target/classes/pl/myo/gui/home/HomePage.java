package pl.myo.gui.home;


import org.wicketstuff.annotation.mount.MountPath;
import pl.myo.core.mappings.MyoApiMappings;
import pl.myo.core.menu.MenuElement;
import pl.myo.core.pages.AbstractMenuPage;
import pl.myo.gui.home.components.DoctorsRatioPanel;
import pl.myo.gui.home.components.OwnPatientsPanel;
import pl.myo.gui.home.components.OwnTopicsPanel;

@MountPath(MyoApiMappings.HOME_PAGE)
@MenuElement(resourceKey = "menu.administration.home", iconName = "fa-home", index = 10)
public class HomePage extends AbstractMenuPage
{
   private DoctorsRatioPanel doctorsRatioPanel;

   private OwnPatientsPanel ownPatientsPanel;

   private OwnTopicsPanel ownTopicsPanel;

   public HomePage()
   {
      doctorsRatioPanel = new DoctorsRatioPanel("doctorsRatioPanel", loggedUserModel);
      bodyContainer.add(doctorsRatioPanel);

      ownPatientsPanel = new OwnPatientsPanel("ownPatientsPanel", loggedUserModel);
      bodyContainer.add(ownPatientsPanel);

      ownTopicsPanel = new OwnTopicsPanel("ownTopicsPanel", loggedUserModel);
      bodyContainer.add(ownTopicsPanel);
   }
}
