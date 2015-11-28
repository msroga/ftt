package pl.ftt.core.component.field;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.joda.time.LocalTime;
import pl.ftt.core.converter.TimeConverter;

import java.util.Random;

public class TimePickerField extends TextField<LocalTime>
{
   private static final Random RANDOM = new Random();

   private final TimeConverter timeConverter;

   private final String timePickerId;

   private int minuteStep = 5;

   private boolean showMeridian;

   public TimePickerField(String id, IModel<LocalTime> model)
   {
      this(id, model, new TimeConverter());
   }

   public TimePickerField(String id, IModel<LocalTime> model, TimeConverter timeConverter)
   {
      super(id, model);
      this.timeConverter = timeConverter;
      setType(LocalTime.class);
      timePickerId = "timePicker" + RANDOM.nextInt();
      setOutputMarkupId(true);
      setMarkupId(timePickerId);
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
      response.render(OnDomReadyHeaderItem.forScript("$('#" + timePickerId + "').timepicker({ "
              + "defaultTime: false, showMeridian: " + showMeridian + ", minuteStep: " + minuteStep + " " + "});"));
   }

   public int getMinuteStep()
   {
      return minuteStep;
   }

   public void setMinuteStep(int minuteStep)
   {
      this.minuteStep = minuteStep;
   }

   public boolean isShowMeridian()
   {
      return showMeridian;
   }

   public void setShowMeridian(boolean showMeridian)
   {
      this.showMeridian = showMeridian;
   }
}
