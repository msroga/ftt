package pl.ftt.core.authentication;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizeType
{
   boolean administrator() default true;

   boolean user() default false;

   boolean doctor() default false;
}
