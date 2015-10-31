package pl.myo.core.renderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

public interface IStyledChoiceRenderer<T> extends IChoiceRenderer<T>
{
   String getOptionClass(T choice);
}
