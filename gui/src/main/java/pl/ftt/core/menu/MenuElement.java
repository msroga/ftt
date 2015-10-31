package pl.ftt.core.menu;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.wicket.markup.html.WebPage;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface MenuElement
{
   String resourceKey();

   int index() default 0;

   String iconName() default "";

   Class<? extends WebPage> parent() default EmptyParent.class;
}
