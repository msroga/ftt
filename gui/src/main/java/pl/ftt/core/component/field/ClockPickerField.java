package pl.ftt.core.component.field;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.convert.IConverter;
import org.joda.time.LocalTime;
import pl.ftt.core.converter.TimeConverter;

import java.util.Random;

/**
 * User: marek
 * Date: 24.02.15
 */
public class ClockPickerField extends TextField<LocalTime>
{
   private static final Random RANDOM = new Random();

   private final TimeConverter timeConverter;

   private final String clockPickerId;

   private boolean autoClose = false;

   private String defaultTime = "now";

   private final IModel<String> doneTextModel;

   public ClockPickerField(String id, IModel<LocalTime> model)
   {
      this(id, model, new TimeConverter());
   }

   public ClockPickerField(String id, IModel<LocalTime> model, TimeConverter timeConverter)
   {
      super(id, model);
      this.timeConverter = timeConverter;
      setType(LocalTime.class);
      clockPickerId = "clockPicker" + RANDOM.nextInt();
      setOutputMarkupId(true);
      setMarkupId(clockPickerId);
      doneTextModel = new ResourceModel("clock.picker.done.text");
   }

   @SuppressWarnings("unchecked")
   @Override
   public <C> IConverter<C> getConverter(Class<C> clazz)
   {
      if (LocalTime.class.isAssignableFrom(clazz))
      {
         return timeConverter;
      }
      else
      {
         return super.getConverter(clazz);
      }
   }

   public void addPlaceholder(IModel<String> placeholder)
   {
      add(new AttributeModifier("placeholder", placeholder));
   }

   @Override
   public void renderHead(IHeaderResponse response)
   {
      super.renderHead(response);
      response.render(OnDomReadyHeaderItem.forScript("$('#" + clockPickerId + "').clockpicker({ "
              + "defaultTime: '" + defaultTime + "', autoclose: " + autoClose + ", donetext: '" + doneTextModel
              .getObject() + "'});"));
   }

   public String getDefaultTime()
   {
      return defaultTime;
   }

   public void setDefaultTime(String defaultTime)
   {
      this.defaultTime = defaultTime;
   }

   public boolean isAutoClose()
   {
      return autoClose;
   }

   public void setAutoClose(boolean autoClose)
   {
      this.autoClose = autoClose;
   }
}
