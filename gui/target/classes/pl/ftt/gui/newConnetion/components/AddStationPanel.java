package pl.ftt.gui.newConnetion.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.LocalTime;
import pl.ftt.core.component.field.ClockPickerField;
import pl.ftt.domain.Station;



/**
 * Created by Marek on 2015-11-24.
 */
public abstract class AddStationPanel extends GenericPanel<Station>
{
   private IModel<LocalTime> timeModel = new Model<>();

   public AddStationPanel(String id, IModel<Station> model)
   {
      super(id, model);
      Form form = new Form("form");

      TextField<String> nameField = new TextField<String>("name", new PropertyModel<String>(model, Station.FIELD_NAME));
      form.add(nameField);

      ClockPickerField timeField = new ClockPickerField("time", timeModel);
      form.add(timeField);

      AjaxSubmitLink addLink = new AjaxSubmitLink("addLink", form)
      {
         @Override
         protected void onSubmit(AjaxRequestTarget target, Form<?> form)
         {
            super.onSubmit(target, form);
            onAdd();
         }
      };
      form.add(addLink);

      add(form);
   }

   protected abstract void onAdd();

   @Override
   protected void onConfigure()
   {
      super.onConfigure();
      setModelObject(new Station());
      timeModel.setObject(null);
   }
}
