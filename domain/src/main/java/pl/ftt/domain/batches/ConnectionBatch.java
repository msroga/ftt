package pl.ftt.domain.batches;

import pl.ftt.domain.ConnectionTypeEnum;

import java.io.Serializable;

/**
 * Created by Marek on 2015-12-19.
 */
public class ConnectionBatch implements Serializable
{
   private Long id;

   private boolean active;

   private String identifier;

   private String comment;

   private ConnectionTypeEnum type;
}
