package com.zyiot.commonservice.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataWarning {
/**
 * 参数列表中的索引 默认为第一个
 * @return
 */
	int dataParamIndex() default 0;
	
}
