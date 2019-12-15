package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysGen;

public interface SysGenService extends AbstractDBService<String>{
	
	List<SysGen> findAll();

	List<SysGen> findAll(Sort sort);

	List<SysGen> findAllById(Iterable<String> ids);

	List<SysGen> saveAll(Iterable<SysGen> entities);

	void flush();

	SysGen saveAndFlush(SysGen entity);

	void deleteInBatch(Iterable<SysGen> entities);

	void deleteAllInBatch();

	SysGen getOne(String id);

	SysGen save(SysGen entity);

	SysGen findById(String id);
	
	Page<SysGen> findAll(Pageable pageable);
	
	boolean existsById(String id);

	long count();

	void deleteById(String id);

	void delete(SysGen entity);

	void deleteAll(Iterable<? extends SysGen> entities);

	void deleteAll();

	/**
	 * @_!_!更新Sys的Key和Value值
	 * @param key
	 * @param value
	 */
	void updateKeyValue(String key, String value);
}
