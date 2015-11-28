package pl.ftt.domain;

import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Marek on 2015-11-12.
 */
@Entity
@Table(name = "\"schedulde_exception\"")
public class ScheduldeException extends AbstractEntity
{
   public static final String FIELD_CONNECTION = "connection";

   public static final String FIELD_EXCEPTION_DATE = "exceptionDate";

   @ManyToOne(optional = false)
   @JoinColumn(name = "connection_id", nullable = false)
   @NotNull
   private Connection connection;

   @Column(name = "exception_date", nullable = false)
   @NotNull
   private LocalDate exceptionDate;

   public Connection getConnection()
   {
      return connection;
   }

   public void setConnection(Connection connection)
   {
      this.connection = connection;
   }

   public LocalDate getExceptionDate()
   {
      return exceptionDate;
   }

   public void setExceptionDate(LocalDate exceptionDate)
   {
      this.exceptionDate = exceptionDate;
   }
}
