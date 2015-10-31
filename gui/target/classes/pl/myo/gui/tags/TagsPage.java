package pl.myo.gui.tags;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import pl.myo.core.authentication.AuthorizeType;
import pl.myo.core.component.ConfirmationCallListener;
import pl.myo.core.component.table.DataTable;
import pl.myo.core.component.table.EntityProvider;
import pl.myo.core.component.table.colums.DefaultColumn;
import pl.myo.core.component.table.colums.IconColumn;
import pl.myo.core.component.table.colums.StyledPropertyColumn;
import pl.myo.core.mappings.MyoApiMappings;
import pl.myo.core.menu.MenuElement;
import pl.myo.core.notification.Notification;
import pl.myo.core.pages.AbstractMenuPage;
import pl.myo.core.pages.PageContext;
import pl.myo.domain.Tag;
import pl.myo.domain.User;
import pl.myo.domain.filters.OpenSearchDescription;
import pl.myo.domain.filters.TagFilter;
import pl.myo.gui.tags.components.TagTableHeader;
import pl.myo.gui.tags.components.TagWindow;
import pl.myo.service.ITagService;

import java.util.ArrayList;
import java.util.List;

@AuthorizeType(doctor = true)
@MountPath(MyoApiMappings.TAGS_PAGE)
@MenuElement(resourceKey = "menu.administration.tags", iconName = "fa-tags", index = 90)
public class TagsPage extends AbstractMenuPage
{
   @SpringBean
   private ITagService tagService;

   private DataTable<Tag> table;

   private TagWindow tagWindow;

   public TagsPage()
   {
      super();
      List<IColumn<Tag, String>> columns = new ArrayList<>();
      columns.add(new StyledPropertyColumn<Tag>(new ResourceModel("tag.name"), Tag.FIELD_NAME, Tag.FIELD_NAME));
      columns.add(new DefaultColumn<Tag>(new ResourceModel("tag.active"), Tag.FIELD_ACTIVE, Tag.FIELD_ACTIVE));
      if (loggedUserModel.getObject().isAdministrator())
      {
         columns.add(new IconColumn<Tag>(null, "fa fa-times", "text-danger")
         {
            @Override
            public void onClick(AjaxRequestTarget target, Tag object)
            {
               tagService.delete(object);
               Notification.success(getString("success.tag.deleted"));
               target.add(table);
            }

            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes, Tag object)
            {
               super.updateAjaxAttributes(attributes, object);
               String message = getString("delete.entity.prompt");
               attributes.getAjaxCallListeners().add(new ConfirmationCallListener(message, object));
            }
         });
      }

      OpenSearchDescription<Tag> osd = new OpenSearchDescription<>();
      osd.setFilter(new TagFilter());
      EntityProvider<Tag> provider = new EntityProvider<>(tagService, osd);

      table = new DataTable<Tag>("table", columns, new ResourceModel("tags.table.header"), provider,
              true)
      {
         @Override
         protected Panel newCaption(String id, IModel<String> caption)
         {
            return new TagTableHeader(id, caption)
            {
               @Override
               protected void onAddTag(AjaxRequestTarget target)
               {
                  tagWindow.show(target);
               }
            };
         }
      };
      table.setOutputMarkupId(true);
//      table.addToolbar(new TagFilterToolbar(table));
      bodyContainer.add(table);

      tagWindow = new TagWindow("tagWindow", new ResourceModel("tag.window.add.header"))
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Tag tag)
         {
            tagService.save(tag);
            Notification.success(getString("success.tag.created"));
            table.refresh(target);
         }
      };
      bodyContainer.add(tagWindow);
   }
}
