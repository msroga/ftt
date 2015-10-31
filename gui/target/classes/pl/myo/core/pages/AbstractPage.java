package pl.myo.core.pages;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.head.StringHeaderItem;
import org.apache.wicket.markup.html.WebPage;

public class AbstractPage extends WebPage 
{
	 @Override
	   public void renderHead(IHeaderResponse response)
	   {
	      super.renderHead(response);
	      response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forReference(getApplication()
	            .getJavaScriptLibrarySettings()
	            .getJQueryReference())));
	      response.render(new PriorityHeaderItem(JavaScriptHeaderItem.forScript("var server_context='"
                 + getRequest().getContextPath() + "';", "contextPathScript")));
//	      response.render(StringHeaderItem.forString(DatePickerScriptHolder.getInstance().getScript(getLocale())));
	   }
}
