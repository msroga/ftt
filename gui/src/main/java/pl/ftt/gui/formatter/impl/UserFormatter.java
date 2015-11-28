package pl.ftt.gui.formatter.impl;

import org.apache.wicket.Component;
import pl.ftt.domain.User;
import pl.ftt.gui.formatter.Formatter;
import pl.ftt.gui.formatter.IFormatter;

/**
 * Created by Marek on 2015-11-19.
 */
@Formatter
public class UserFormatter implements IFormatter<User>
{
   @Override
   public String format(User obj, Component component)
   {
      return obj.getFirstName() + " " + obj.getLastName();
   }

   @Override
   public Class<User> getTarget()
   {
      return User.class;
   }
}
