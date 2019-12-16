package cn.tblack.work.reporter.config;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

import cn.tblack.work.reporter.constant.RedisCacheBeanDefinition;

/**
 * @~_~Redis缓存配置类
 * @author TD唐登
 * @Date:2019年12月15日
 * @Version: 1.0(测试版)
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {

	/**
	 * @~_~用于生成缓存对象存储的key。 Redis缓存通过Key来查找缓存值。 @~_~该对象通过对象名+方法名+属性值来进行生成Key
	 * @return
	 */
	@Bean(name = RedisCacheBeanDefinition.REDIS_CACHE_KEY_GENERATOR)
	public KeyGenerator cacheKeyGenerator() {

		return new KeyGenerator() {

			@Override
			public Object generate(Object target, Method method, Object... params) {

				StringBuffer buffer = new StringBuffer(target.getClass().getName());
				buffer.append("_");
				buffer.append(method.getName());

				for (Object object : params) {
					buffer.append("_");
					buffer.append(object.toString());
				}

				return buffer.toString();
			}
		};

	}

	/**
	 * @~_~Redis缓存管理对象
	 * @param factory
	 * @return
	 */
	@Bean(name = RedisCacheBeanDefinition.REDIS_CACHE_MANAGER)
	public CacheManager redisCacheManager(RedisConnectionFactory factory) {
		
		// 初始化一个RedisWriter对象
		RedisCacheWriter redisWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);

		// 初始化一个缓存配置对象
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();

		// 设置超时时间
		defaultCacheConfig.entryTtl(Duration.ofSeconds(10));

		RedisCacheManager redisCacheManager = new RedisCacheManager(redisWriter, defaultCacheConfig);

		return redisCacheManager;
	}
	
	/**
	 * @~——~用于注入和提取对象的RedisTemplate
	 * @param factory
	 * @return
	 */
	@Bean(name = RedisCacheBeanDefinition.REDIS_TEMPLATE)
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String,Object>();
		template.setConnectionFactory(factory);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		//使用宽松实现的LaissezFaireSubTypeValidator.该验证器允许所有的子类型
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, 
				ObjectMapper.DefaultTyping.NON_FINAL);
		
		jackson2JsonRedisSerializer.setObjectMapper(om);
		template.setKeySerializer(new StringRedisSerializer(Charset.forName("utf-8")));
		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

}
