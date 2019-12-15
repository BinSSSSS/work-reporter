package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysConfig;

public interface SysConfigService  extends AbstractDBService<Long>{

	List<SysConfig> findAll();

	List<SysConfig> findAll(Sort sort);

	List<SysConfig> findAllById(Iterable<Long> ids);

	List<SysConfig> saveAll(Iterable<SysConfig> entities);

	void flush();

	SysConfig saveAndFlush(SysConfig entity);

	void deleteInBatch(Iterable<SysConfig> entities);

	void deleteAllInBatch();

	SysConfig getOne(Long id);

	SysConfig save(SysConfig entity);

	SysConfig findById(Long id);
	
	boolean existsById(Long id);

	long count();

	void deleteById(Long id);

	void delete(SysConfig entity);

	void deleteAll(Iterable<? extends SysConfig> entities);

	void deleteAll();
}
