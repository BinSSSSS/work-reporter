package cn.tblack.work.reporter.sys.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.SysTable;

public interface SysTableService extends AbstractDBService<Integer>{
	
	List<SysTable> findAll();

	List<SysTable> findAll(Sort sort);

	List<SysTable> findAllById(Iterable<Integer> ids);

	List<SysTable> saveAll(Iterable<SysTable> entities);

	void flush();

	SysTable saveAndFlush(SysTable entity);

	void deleteInBatch(Iterable<SysTable> entities);

	void deleteAllInBatch();

	SysTable getOne(Integer id);

	SysTable save(SysTable entity);

	SysTable findById(Integer id);
	
	Page<SysTable> findAll(Pageable pageable);
	
	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(SysTable entity);

	void deleteAll(Iterable<? extends SysTable> entities);

	void deleteAll();

	/**
	 * @!_!通过指定的数据库名在系统表信息中查找到指定数据库的所有的表信息
	 * @param schema
	 */
	List<SysTable> findAllBySchema(String schema);

	/**
	 * @!_!通过指定的数据库名和在系统表信息中查找指定数据库内符合模糊查找表名的表数据信息
	 * @param schemaName
	 * @return
	 */
	List<SysTable> findAllBySchemaAndTableName(String schemaName,String tableName);
	
	/**
	 * @!_!通过指定的数据库名和在系统表信息中查找指定数据库内符合模糊查找表名的表数据信息,并进行分页
	 * @param schemaName
	 * @param tableName
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<SysTable> findAllBySchemaAndTableName(String schemaName,String tableName,Integer pageNo,Integer pageSize);
	
	
	/**
	 * @_~_~通过数据库名和数据表名精确的进行查找数据
	 * @param schemaName
	 * @param tableName
	 * @return
	 */
	SysTable findBySchemaAndTableName( String schemaName,
			 String tableName);
	
	/**
	 * @_!_!通过数据库名来进行精确的查找符合数据表名的数据并进行分页
	 * @param schemaName
	 * @param tableName
	 * @return
	 */
	SysTable findBySchemaAndTableName(String schemaName,
			 String tableName,
			 Integer pageNo,
			 Integer pageSize);
}
