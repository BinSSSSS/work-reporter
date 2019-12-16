package cn.tblack.work.reporter.redis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.tblack.work.reporter.redis.util.RedisUtils;

/**
 * @~——~用于Redis查询缓存的切面
 * @author TD唐登
 * @Date:2019年12月15日
 * @Version: 1.0(测试版)
 */
@Component
@Aspect
public class RedisFindMethodAspect {
	
	@Autowired
	private RedisUtils redisUtils;
	
	
	@Pointcut("execution(* cn.tblack.work.reporter.*.service.*.*.find*(..)) || "
			+ "execution(* cn.tblack.work.reporter.*.service.*.*.fuzzy*(..))")
	public void pointcut() {
		
	}
	
	@Around("pointcut()")
	public Object aroundPro(ProceedingJoinPoint proceeding) throws Throwable {
		
		String key =  redisUtils.generatorKey(proceeding);
		
		
		//如果redis缓存中存在该对象，无需要调用方法，直接返回缓存
		if(redisUtils.exist(key)) {
			
			return redisUtils.getValue(key);
		}
		
		//否则的话将方法调用结果添加到Redis缓存中
		Object ret = proceeding.proceed();
		
		redisUtils.putKey(key, ret);
		
		return ret;
	}
}
