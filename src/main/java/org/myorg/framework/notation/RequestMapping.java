package org.myorg.framework.notation;

import org.myorg.framework.enums.ContentType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String path();
    ContentType contentType() default ContentType.PLAIN ;
    boolean files() default false;
}
