package pl.myo.core.component.table.toolbar;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.IPageableItems;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.util.io.IClusterable;

public class NavigationLabel extends Label
{
   private static final long serialVersionUID = 8060663421096966425L;

   private final IPageableItems pageable;

   private final String resourceKey;

   public NavigationLabel(final String id, final IPageableItems pageable, String resourceKey)
   {
      super(id);
      this.pageable = pageable;
      this.resourceKey = resourceKey;
   }

   private static class LabelModelObject implements IClusterable
   {
      private static final long serialVersionUID = 1L;

      private final IPageableItems pageable;

      public LabelModelObject(final IPageableItems table)
      {
         pageable = table;
      }

      @SuppressWarnings("unused")
      public long getCurrentPage()
      {
         return pageable.getCurrentPage() + 1;
      }

      @SuppressWarnings("unused")
      public long getPageCount()
      {
         return pageable.getPageCount();
      }

      @SuppressWarnings("unused")
      public long getItemCount()
      {
         return pageable.getItemCount();
      }
   }

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      if (pageable.getPageCount() > 0)
      {
         setDefaultModel(new StringResourceModel(resourceKey, this, new Model<LabelModelObject>(new LabelModelObject(
               pageable))));
      }
      else
      {
         setDefaultModel(new ResourceModel("no.records.found"));
      }
   }
}
