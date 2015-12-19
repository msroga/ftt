package pl.ftt.gui.administration.componnents;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import pl.ftt.core.component.DefaultLabel;
import pl.ftt.core.component.portlet.AbstractReadOnlyPanel;
import pl.ftt.core.component.portlet.BootstrapViewLabel;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Station;
import pl.ftt.domain.Tag;
import pl.ftt.domain.rel.ConnectionStationRelation;

/**
 * Created by Marek on 2015-12-17.
 */
public class ConnectionReadOnlyPanel extends AbstractReadOnlyPanel<Connection>
{
   private final ListModel<ConnectionStationRelation> stationsListModel;

   private final ListModel<Tag> tagsModel;

   public ConnectionReadOnlyPanel(String id, IModel<Connection> model,
                                  final ListModel<ConnectionStationRelation> stationsListModel,
                                  final ListModel<Tag> tagsModel)
   {
      super(id, model);
      this.stationsListModel = stationsListModel;
      this.tagsModel = tagsModel;

      BootstrapViewLabel identifier = newViewLabel("identifier", new ResourceModel("connection.identifier"),
              new PropertyModel<String>(model, Connection.FIELD_IDENTIFIER));
      identifier.setLabelSize(4).setSize(8);
      add(identifier);

      BootstrapViewLabel comment = newViewLabel("comment", new ResourceModel("connection.comment"),
              new PropertyModel<String>(model, Connection.FIELD_COMMENT));
      comment.setLabelSize(4).setSize(8);
      add(comment);

      ListView<ConnectionStationRelation> stationsView = new ListView<ConnectionStationRelation>("stationsView",
           stationsListModel)
      {
         @Override
         protected void populateItem(ListItem<ConnectionStationRelation> components)
         {
            IModel<ConnectionStationRelation> model = components.getModel();
            components.add(new Label("station", new PropertyModel<String>(model,
                    ConnectionStationRelation.FIELD_STATION + "." + Station.FIELD_NAME)));
            components.add(new DefaultLabel("arrival", new PropertyModel<String>(model,
                    ConnectionStationRelation.FIELD_ARRIVAL_TIME)));
            components.add(new DefaultLabel("departure", new PropertyModel<String>(model,
                    ConnectionStationRelation.FIELD_DEPARTURE_TIME)));
         }
      };
      add(stationsView);

      ListView<Tag> tagsView = new ListView<Tag>("tagsView",
              tagsModel)
      {
         @Override
         protected void populateItem(ListItem<Tag> components)
         {
            IModel<Tag> model = components.getModel();
            components.add(new DefaultLabel("tag", model));
         }
      };
      add(tagsView);
   }
}
