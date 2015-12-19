package pl.ftt.domain.rel;

import pl.ftt.domain.AbstractEntity;
import pl.ftt.domain.Connection;
import pl.ftt.domain.Tag;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-11-12.
 */
@Entity
@Table(name = "\"connection_tag_relation\"")
public class ConnectionTagRelation extends AbstractEntity
{
   public static final String FIELD_CONNECTION = "connection";

   public static final String FIELD_TAG = "tag";

   @ManyToOne(optional = false)
   @JoinColumn(name = "connection_id", nullable = false)
   @NotNull
   private Connection connection;

   @ManyToOne(optional = false)
   @JoinColumn(name = "tag_id", nullable = false)
   @NotNull
   private Tag tag;

   public ConnectionTagRelation()
   {
   }

   public ConnectionTagRelation(Connection connection, Tag tag)
   {
      this.connection = connection;
      this.tag = tag;
   }

   public Connection getConnection()
   {
      return connection;
   }

   public void setConnection(Connection connection)
   {
      this.connection = connection;
   }

   public Tag getTag()
   {
      return tag;
   }

   public void setTag(Tag tag)
   {
      this.tag = tag;
   }
}
