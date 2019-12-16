package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysOss;

public interface SysOssService extends AbstractDBService<Long>{
	
	List<SysOss> findAll();

	List<SysOss> findAll(Sort sort);

	List<SysOss> findAllById(Iterable<Long> ids);

	List<SysOss> saveAll(Iterable<SysOss> entities);

	void flush();

	SysOss saveAndFlush(SysOss entity);

	void deleteInBatch(Iterable<SysOss> entities);

	void deleteAllInBatch();

	SysOss getOne(Long id);

	SysOss save(SysOss entity);

	SysOss findById(Long id);
	
	boolean existsById(Long id);

	long count();

	void deleteById(Long id);

	void delete(SysOss entity);

	void deleteAll(Iterable<? extends SysOss> entities);

	void deleteAll();
	
	LaYuiPage<SysOss> findAll(Pageable pageable);
}
