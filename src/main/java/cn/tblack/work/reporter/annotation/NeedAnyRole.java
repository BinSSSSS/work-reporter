package cn.tblack.work.reporter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @=-=该注解用于切面扫描。 标记了该注解表示访问页面时至少需要一个角色。如果当前用户的Authentication中的角色为空，不允许访问。
 * @author TD唐登
 * @Date:2019年11月22日
 * @Version: 1.0(测试版)
 */
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface NeedAnyRole {

	/**
	 * @~_~表示的是访问的资源需要的角色信息， 默认为空表示任何具备角色的用户都可以访问.
	 * @return
	 */
	String[] value() default "";
}
