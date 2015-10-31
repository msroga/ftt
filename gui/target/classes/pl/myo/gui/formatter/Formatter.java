package pl.myo.gui.formatter;

import java.lang.annotation.*;

@Target(
{
   ElementType.TYPE
})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Formatter
{

}
