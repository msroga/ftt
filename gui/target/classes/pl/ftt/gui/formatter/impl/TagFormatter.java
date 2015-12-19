package pl.ftt.gui.formatter.impl;

import org.apache.wicket.Component;
import pl.ftt.domain.Tag;
import pl.ftt.gui.formatter.Formatter;
import pl.ftt.gui.formatter.IFormatter;

/**
 * Created by Marek on 2015-12-19.
 */
@Formatter
public class TagFormatter  implements IFormatter<Tag>
{
   @Override
   public String format(Tag obj, Component component)
   {
      return obj.getName();
   }

   @Override
   public Class<Tag> getTarget()
   {
      return Tag.class;
   }
}
