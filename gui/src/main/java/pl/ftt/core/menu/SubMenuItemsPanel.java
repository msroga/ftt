package pl.ftt.core.menu;

public class SubMenuItemsPanel extends AbstractMenuItemPanel
{

	public SubMenuItemsPanel(String id, MenuItem menuItem) 
	{
		super(id, menuItem);
		add(new MenuPanel("menuPanel", menuItem.getSubMenuItems()));
	}

}
