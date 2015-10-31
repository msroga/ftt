package pl.ftt.core.component.portlet;

import java.io.Serializable;

public interface IValuesFormatter extends Serializable
{
   String format(Object[] objects);
}
