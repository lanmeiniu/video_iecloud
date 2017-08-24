package com.iecloud.annotation;

import java.lang.annotation.*;

/**
 * Created by lanmeiniu on 2017/7/24.
 */
//当前注解仅作用在方法上
@Target({ElementType.TYPE})
//只是编译器如何对待你的自定义Annotation
//此在运行期间有效
@Retention(RetentionPolicy.RUNTIME)
@Inherited

public @interface AopClassAnno {
    String testString() default "";
}
