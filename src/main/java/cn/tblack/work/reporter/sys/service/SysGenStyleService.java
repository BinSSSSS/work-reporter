package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysGenStyle;

public interface SysGenStyleService extends AbstractDBService<String>{
	
	List<SysGenStyle> findAll();

	List<SysGenStyle> findAll(Sort sort);

	List<SysGenStyle> findAllById(Iterable<String> ids);

	List<SysGenStyle> saveAll(Iterable<SysGenStyle> entities);

	void flush();

	SysGenStyle saveAndFlush(SysGenStyle entity);

	void deleteInBatch(Iterable<SysGenStyle> entities);

	void deleteAllInBatch();

	SysGenStyle getOne(String id);

	SysGenStyle save(SysGenStyle entity);

	SysGenStyle findById(String id);
	
	boolean existsById(String id);

	long count();

	void deleteById(String id);

	void delete(SysGenStyle entity);

	void deleteAll(Iterable<? extends SysGenStyle> entities);

	void deleteAll();

	List<SysGenStyle> selectAllByName(String name, boolean isBlur);

	Page<SysGenStyle> findAll(Pageable pageable);
}
