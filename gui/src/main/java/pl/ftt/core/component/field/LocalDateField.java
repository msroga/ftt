package pl.ftt.core.component.field;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.AbstractTextComponent.ITextFormatProvider;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.joda.time.LocalDate;
import pl.ftt.core.converter.DateConverter;
import pl.ftt.gui.formatter.impl.LocalDateFormatter;

import java.util.Random;

public class LocalDateField extends TextField<LocalDate> implements ITextFormatProvider
{
   private static final Random RANDOM = new Random();

   private final DateConverter dateConverter;

   private final String datePickerId;

   public LocalDateField(String id, IModel<LocalDate> model, String pattern)
   {
      this(id, model, DateConverter.forClass(LocalDate.class, pattern));
   }

   public LocalDateField(String id, IModel<LocalDate> model)
   {
      this(id, model, DateConverter.forClass(LocalDate.class, LocalDateFormatter.DEFAULT_LOCAL_DATE_FORMAT));
   }

   private LocalDateField(String id, IModel<LocalDate> model, DateConverter dateConverter)
   {
      super(id, model);
      this.dateConverter = dateConverter;

      setType(LocalDate.class);
      datePickerId = "datePicker" + RANDOM.nextInt();
      setOutputMarkupId(true);
      setMarkupId(datePickerId);
   }

   @SuppressWarnings("unchecked")
   @Override
   public <C> IConverter<C> getConverter(Class<C> clazz)
   {
      if (LocalDate.class.isAssignableFrom(clazz))
      {
         return dateConverter;
      }
      else
      {
         return super.getConverter(clazz);
      }
   }

   @Override
   public String getTextFormat()
   {
      return dateConverter.getJQueryDatePattern(getLocale());
   }

   public void addPlaceholder(IModel<String> placeholder)
   {
      add(new AttributeModifier("placeholder", placeholder));
   }

   @Override
   public void renderHead(IHeaderResponse response)
   {
      super.renderHead(response);
      response.render(
              OnDomReadyHeaderItem.forScript(
                      "$('#" + datePickerId + "').datepicker({language: '" + getLocale()
                              .getLanguage() + "', autoclose: true, orientation: 'bottom', format: '" + getTextFormat() + "'})"));
   }
}
