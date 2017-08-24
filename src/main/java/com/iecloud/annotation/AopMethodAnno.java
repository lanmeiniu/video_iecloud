package com.iecloud.annotation;

import java.lang.annotation.*;

/**
 * Created by lanmeiniu on 2017/7/24.
 */
//当前注解仅作用在类、接口、枚举上
@Target({ElementType.METHOD})
//仅在运行期间有效
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AopMethodAnno {

    String testString () default "" ;
}
