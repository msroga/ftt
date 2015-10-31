package pl.myo.gui.formatter;

import org.apache.wicket.Component;

public interface IFormatter<T>
{
   String format(T obj, Component component);

   Class<T> getTarget();
}
