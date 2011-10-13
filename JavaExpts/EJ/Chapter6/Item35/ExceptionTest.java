// Annotation type with an array parameter -  Page 173

import java.lang.annotation.*;

/**
 * Indicates that the annotated method is a test method that
 * must throw the any of the designated exceptions to succeed.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {
    Class<? extends Exception>[] value();
}
