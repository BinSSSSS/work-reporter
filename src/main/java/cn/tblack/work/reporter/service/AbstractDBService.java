package cn.tblack.work.reporter.service;

/**
 * @_~_~该接口主要是定义一些用于所有数据库服务的接口都必需要实现的方法。
 * @author TD唐登
 * @Date:2019年12月8日
 * @Version: 1.0(测试版)
 */
public interface AbstractDBService<ID> {

	 boolean  existsById(ID id);
}
