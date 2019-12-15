package cn.tblack.work.reporter.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysColumn;
import cn.tblack.work.reporter.sys.entity.SysGenTemp;
import cn.tblack.work.reporter.sys.entity.SysTable;

public interface SysColumnService extends AbstractDBService<Integer>{
	List<SysColumn> findAll();

	List<SysColumn> findAll(Sort sort);

	List<SysColumn> findAllById(Iterable<Integer> ids);

	List<SysColumn> saveAll(Iterable<SysColumn> entities);

	void flush();

	SysColumn saveAndFlush(SysColumn entity);

	void deleteInBatch(Iterable<SysColumn> entities);

	void deleteAllInBatch();

	SysColumn getOne(Integer id);

	SysColumn save(SysColumn entity);

	SysColumn findById(Integer id);
	
	Page<SysColumn> findAll(Pageable pageable);
	
	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(SysColumn entity);

	void deleteAll(Iterable<? extends SysColumn> entities);

	void deleteAll();
	
	/**
	 * @_!_!通过数据库名字和表名查询该表的字段
	 * @param schema
	 * @param tableName
	 * @return
	 */
	List<Map<String,Object>> findAllBySchema(@Param("schema") String schema,@Param("tableName") String tableName);

	/**
	 * @!_!根据表名生成代码
	 * @param settings
	 * @param tableList
	 * @return
	 */
	byte[]  generatorCode(Map<String,Object> settings, List<SysTable> tableList,List<SysGenTemp> templateList);
}
