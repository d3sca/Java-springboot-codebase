package annotation.sendNotificationOnSuccess.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to send notification event when it execute successfully
 * the method must has a return value and transactional
 * input: event title
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SendNotificationOnSuccess {
    // notification message
    String message();
    // where to search for entityId value
    SearchIn searchIn() default SearchIn.nothing;
    /*
    if the entityId variable name, if the variable is inside object write the full path to it
    ex: "input.data"
    */
    String entityIdFiledPath() default "";
}
