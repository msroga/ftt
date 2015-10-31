package pl.myo.gui.formatter.impl;

import org.apache.wicket.Component;
import pl.myo.domain.User;
import pl.myo.gui.formatter.Formatter;
import pl.myo.gui.formatter.IFormatter;

/**
 * Created by Marek on 2015-05-27.
 */
@Formatter
public class UserFormatter implements IFormatter<User>
{
   @Override
   public String format(User user, Component component)
   {
      return user != null ? user.getName() + " " + user.getSurname() : null;
   }

   @Override
   public Class<User> getTarget()
   {
      return User.class;
   }
}
