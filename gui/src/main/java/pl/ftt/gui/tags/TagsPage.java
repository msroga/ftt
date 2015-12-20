package pl.ftt.gui.tags;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.wicketstuff.annotation.mount.MountPath;
import pl.ftt.core.authentication.AuthorizeType;
import pl.ftt.core.component.ConfirmationCallListener;
import pl.ftt.core.component.table.DataTable;
import pl.ftt.core.component.table.EntityProvider;
import pl.ftt.core.component.table.colums.DefaultColumn;
import pl.ftt.core.component.table.colums.IconColumn;
import pl.ftt.core.component.table.colums.StyledPropertyColumn;
import pl.ftt.core.mappings.FttApiMappings;
import pl.ftt.core.menu.MenuElement;
import pl.ftt.core.notification.Notification;
import pl.ftt.core.pages.AbstractMenuPage;
import pl.ftt.domain.Tag;
import pl.ftt.domain.filters.OpenSearchDescription;
import pl.ftt.domain.filters.TagFilter;
import pl.ftt.gui.tags.components.TagFilterToolbar;
import pl.ftt.gui.tags.components.TagWindow;
import pl.ftt.service.ITagService;

import java.util.ArrayList;
import java.util.List;

@MountPath(FttApiMappings.PRIVATE_TAGS_PAGE)
@AuthorizeType(administrator = true, user = true)
@MenuElement(resourceKey = "menu.tags", iconName = "fa-tag", index = 30)
public class TagsPage extends AbstractMenuPage
{
   @SpringBean
   private ITagService tagService;

   private DataTable<Tag> table;

   private TagWindow tagWindow;

   public TagsPage()
   {
      List<IColumn<Tag, String>> columns = new ArrayList<>();
      columns.add(new StyledPropertyColumn<Tag>(new ResourceModel("tag.name"), Tag.FIELD_NAME, Tag.FIELD_NAME));
      columns.add(new StyledPropertyColumn<Tag>(new ResourceModel("tag.description"), Tag.FIELD_DESCRIPTION, Tag.FIELD_DESCRIPTION));
      columns.add(new DefaultColumn<Tag>(new ResourceModel("tag.active"), Tag.FIELD_ACTIVE));
      columns.add(new IconColumn<Tag>(new ResourceModel("delete.column"), "fa fa-times")
      {
         @Override
         public void onClick(AjaxRequestTarget target, Tag tag)
         {
            if (tag.isActive())
            {
               try
               {
                  tagService.delete(tag);
                  Notification.success(getString("entity.deleted"));
               }
               catch (DataIntegrityViolationException | ConstraintViolationException e)
               {
                  tag.setActive(false);
                  tagService.update(tag);
                  Notification.warn(getString("entity.delete.failed"));
               }
               table.refresh(target);
            }
            else
            {
               Notification.warn(getString("entity.inactive"));
            }
         }

         @Override
         protected void updateAjaxAttributes(AjaxRequestAttributes attributes, Tag tag)
         {
            super.updateAjaxAttributes(attributes, tag);
            String message = getString("entity.delete.prompt");
            attributes.getAjaxCallListeners().add(new ConfirmationCallListener(message, tag));
         }
      });


      OpenSearchDescription<Tag> osd = new OpenSearchDescription<>();
      TagFilter tagFilter = new TagFilter();
      tagFilter.setActive(true);
      osd.setFilter(tagFilter);
      EntityProvider provider = new EntityProvider<Tag>(tagService, osd);


      table = new DataTable<Tag>("table", columns, new ResourceModel("tags.table.header"), provider, 25, true)
      {
         @Override
         protected void onRowClick(AjaxRequestTarget target, IModel<Tag> model)
         {
            tagWindow.show(target, model.getObject());
         }
      };
      table.addToolbar(new TagFilterToolbar(table)
      {
         @Override
         protected void onAdd(AjaxRequestTarget target)
         {
            tagWindow.show(target);
         }
      });
      bodyContainer.add(table);

      tagWindow = new TagWindow("tagWindow", new ResourceModel("tag.window.header"))
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Tag tag)
         {
            if (tag.getId() == null)
            {
               tagService.save(tag);
               Notification.success(getString("success.tag.saved"));
            }
            else
            {
               tagService.update(tag);
               Notification.success(getString("success.tag.updated"));
            }
            table.refresh(target);
         }
      };
      bodyContainer.add(tagWindow);
   }
}
