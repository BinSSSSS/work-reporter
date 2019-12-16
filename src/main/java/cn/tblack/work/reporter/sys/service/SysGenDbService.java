package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysGenDb;

public interface SysGenDbService extends AbstractDBService<String>{
	
	List<SysGenDb> findAll();

	List<SysGenDb> findAll(Sort sort);

	List<SysGenDb> findAllById(Iterable<String> ids);

	List<SysGenDb> saveAll(Iterable<SysGenDb> entities);

	void flush();

	SysGenDb saveAndFlush(SysGenDb entity);

	void deleteInBatch(Iterable<SysGenDb> entities);

	void deleteAllInBatch();

	SysGenDb getOne(String id);

	SysGenDb save(SysGenDb entity);

	SysGenDb findById(String id);
	
	boolean existsById(String id);

	long count();

	void deleteById(String id);

	void delete(SysGenDb entity);

	void deleteAll(Iterable<? extends SysGenDb> entities);

	void deleteAll();

	/**
	 * @!_!_!通过DB_NAME来进行数据的查询
	 * @param dbName 需要查询的表名
	 * @param isBlur 是否使用模糊查询
	 * @return
	 */
	List<SysGenDb> selectByDbName(String dbName, boolean isBlur);

	LaYuiPage<SysGenDb> findAll(Pageable pageable);
}
