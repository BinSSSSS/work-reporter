package cn.tblack.work.reporter.sys.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysGenTemp;

public interface SysGenTempService extends AbstractDBService<String>{

	List<SysGenTemp> findAll();

	List<SysGenTemp> findAll(Sort sort);

	List<SysGenTemp> findAllById(Iterable<String> ids);

	List<SysGenTemp> saveAll(Iterable<SysGenTemp> entities);

	void flush();

	SysGenTemp saveAndFlush(SysGenTemp entity);

	void deleteInBatch(Iterable<SysGenTemp> entities);

	void deleteAllInBatch();

	SysGenTemp getOne(String id);

	SysGenTemp save(SysGenTemp entity);

	SysGenTemp findById(String id);
	
	boolean existsById(String id);

	long count();

	void deleteById(String id);

	void delete(SysGenTemp entity);

	void deleteAll(Iterable<? extends SysGenTemp> entities);

	void deleteAll();

	/***
	 * @!_!_!通过生成文件名来查找数据库内数据
	 * @param filename 需要查找的生成文件名
	 * @param isBlur 是否使用模糊查找的方式
	 * @return
	 */
	List<SysGenTemp> selectAllByGenFilename(String filename, boolean isBlur);

	LaYuiPage<SysGenTemp> findAll(Pageable pageable);

	/**
	 * @_!_!根据StyleId进行分页查找
	 * @param styleId
	 * @param pageable
	 * @return
	 */
	LaYuiPage<SysGenTemp> findAllByStyleId(String styleId, Pageable pageable);

	/**
	 * @_!_!根据StyleId进行查询数据
	 * @param id
	 * @return
	 */
	List<SysGenTemp> findAllByStyleId(String styleId);
}
