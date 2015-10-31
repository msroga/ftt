package pl.myo.gui.formatter.impl;

import org.apache.wicket.Component;
import pl.myo.domain.Tag;
import pl.myo.gui.formatter.Formatter;
import pl.myo.gui.formatter.IFormatter;

/**
 * Created by Marek on 2015-05-30.
 */
@Formatter
public class TagFormatter implements IFormatter<Tag>
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
