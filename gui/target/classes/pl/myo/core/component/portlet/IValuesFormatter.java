package pl.myo.core.component.portlet;

import java.io.Serializable;

public interface IValuesFormatter extends Serializable
{
   String format(Object[] objects);
}
