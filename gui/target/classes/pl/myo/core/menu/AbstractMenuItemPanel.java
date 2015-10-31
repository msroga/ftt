package pl.myo.core.menu;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;

public class AbstractMenuItemPanel extends Panel {

	public AbstractMenuItemPanel(String id, MenuItem menuItem) 
	{
		super(id);
		AbstractLink link;
		if (menuItem.getSubMenuItems().isEmpty()) {
			link = new BookmarkablePageLink("link", menuItem.getTarget());
		} else {
			link = new ExternalLink("link", "#");
		}
		link.add(AttributeModifier.replace("title",
				new ResourceModel(menuItem.getResourceKey())));
		EmptyPanel icon = new EmptyPanel("icon");
		String iconName = menuItem.getIconName();
		if (!StringUtils.isBlank(iconName)) {
			icon.add(AttributeModifier.replace("class", "fa fa-lg fa-fw "
					+ iconName));
		}
		link.add(icon);
		Label label = new Label("label", new ResourceModel(
				menuItem.getResourceKey()));
		link.add(label);
		add(link);
	}

}
