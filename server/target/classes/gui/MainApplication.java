package gui;

import com.googlecode.wicket.jquery.ui.settings.JQueryLibrarySettings;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;
import pl.ftt.core.menu.MenuHolder;
import pl.ftt.gui.connections.ConnectionsPage;
import pl.ftt.gui.formatter.FormatterScanner;
import pl.ftt.gui.login.LoginPage;
import pl.ftt.service.IConfigurationService;

@Component(value = "wicketApplication")
public class MainApplication extends WebApplication
{
   private static final String DEFAULT_ENCODING = "UTF-8";

   private volatile boolean initialized = false;

   @SpringBean
   private IConfigurationService configurationService;

   @Override
   public Class getHomePage()
   {

      return ConnectionsPage.class; // return default page
   }

   @Override
   protected void init()
   {
      super.init();
      if (!initialized)
      {
         configureDependencies();
         setListeners();
         configureApplicationSettings();

         initialized = true;
      }
   }

   protected void configureDependencies()
   {
      getComponentInstantiationListeners().add(new SpringComponentInjector(this));
      Injector.get().inject(this);
   }

   protected void setListeners()
   {
      new AnnotatedMountScanner().scanPackage("pl.ftt.gui").mount(this);
      mount(new MountedMapper(configurationService.getConfiguration("security.login.path"), LoginPage.class));
      new FormatterScanner().scanPackages("pl.ftt.gui.formatter.impl");
      MenuHolder.getInstance().scanPackages("pl.ftt.gui");
   }

   protected void configureApplicationSettings()
   {
      getMarkupSettings().setDefaultMarkupEncoding(DEFAULT_ENCODING);
      getRequestCycleSettings().setResponseRequestEncoding(DEFAULT_ENCODING);
      getMarkupSettings().setStripWicketTags(true);
      getMarkupSettings().setCompressWhitespace(true);
      if (getConfigurationType() == RuntimeConfigurationType.DEPLOYMENT)
      {
         getDebugSettings().setAjaxDebugModeEnabled(false);
//         getApplicationSettings().setPageExpiredErrorPage(ErrorSessionExpiredPage.class);
//         getApplicationSettings().setAccessDeniedPage(Error403Page.class);
//         getApplicationSettings().setInternalErrorPage(Error500Page.class);
         getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
      }

      String contextPath = WebApplication.get().getServletContext().getContextPath();
      JQueryLibrarySettings jQueryLibrarySettings = new JQueryLibrarySettings();
      jQueryLibrarySettings.setJQueryReference(new UrlResourceReference(Url.parse(contextPath
              + "/static/js/libs/jquery-2.0.2.min.js")));
      jQueryLibrarySettings.setJQueryUIReference(null);
      setJavaScriptLibrarySettings(jQueryLibrarySettings);
   }
}
