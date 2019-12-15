package cn.tblack.work.reporter.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.sys.entity.SysTable;

public interface SysTableDao extends JpaRepository<SysTable, Integer>{

	/**
	 * @!_!通过数据库名字到系统表信息中进行查询数据_
	 * @param schema
	 * @return JPA查询数据如果没有对应实体表，则返回的类型为AbstractJpaQuery.TupleConverter.TupleBackedMap该对象类型为私有的.
	 * <p>但是可以直接通过Map作为返回类型自定义的进行处理。</p>
	 */
	@Query(value = "SELECT "
			+ " table_name tableName, table_comment comment, "
			+ " create_time createTime, engine engine, table_schema tableSchema"
			+ " FROM information_schema.tables WHERE table_schema = :schema",nativeQuery = true)
	List<Map<String,Object>> findAllBySchema(@Param("schema")String schema);
	
	
	/**
	 * @_!_!通过数据库名来进行模糊的查找符合数据表名的数据
	 * @param schemaName
	 * @param tableName
	 * @return
	 */
	@Query(value = "SELECT "
			+ " table_name tableName, table_comment comment, "
			+ " create_time createTime, engine engine, table_schema tableSchema"
			+ " FROM information_schema.tables "
			+ " WHERE table_schema = :schemaName AND table_name LIKE %:tableName%",nativeQuery = true)
	List<Map<String, Object>> fuzzyFindAllBySchemaAndTableName(@Param("schemaName") String schemaName,
			@Param("tableName") String tableName);

	/**
	 * @_~_~通过数据库名和数据表名精确的进行查找数据
	 * @param schemaName
	 * @param tableName
	 * @return
	 */
	@Query(value = "SELECT "
			+ " table_name tableName, table_comment comment, "
			+ " create_time createTime, engine engine, table_schema tableSchema"
			+ " FROM information_schema.tables "
			+ " WHERE table_schema = :schemaName AND table_name = :tableName",nativeQuery = true)
	Map<String, Object> findBySchemaAndTableName(@Param("schemaName") String schemaName,
			@Param("tableName") String tableName);
	
	/**
	 * @_!_!通过数据库名来进行模糊的查找符合数据表名的数据并进行分页
	 * @param schemaName
	 * @param tableName
	 * @return
	 */
	@Query(value = "SELECT "
			+ " table_name tableName, table_comment comment, "
			+ " create_time createTime, engine engine, table_schema tableSchema"
			+ " FROM information_schema.tables "
			+ " WHERE table_schema = :schemaName AND table_name LIKE %:tableName%"
			+ " LIMIT :pageNo,:pageSize",nativeQuery = true)
	List<Map<String, Object>> fuzzyFindAllBySchemaAndTableName(@Param("schemaName")String schemaName,
			@Param("tableName")  String tableName,
			@Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	
	/**
	 * @_!_!通过数据库名来进行精确的查找符合数据表名的数据并进行分页
	 * @param schemaName
	 * @param tableName
	 * @return
	 */
	@Query(value = "SELECT "
			+ " table_name tableName, table_comment comment, "
			+ " create_time createTime, engine engine, table_schema tableSchema"
			+ " FROM information_schema.tables "
			+ " WHERE table_schema = :schemaName AND table_name = :tableName"
			+ " LIMIT :pageNo,:pageSize",nativeQuery = true)
	Map<String, Object> findBySchemaAndTableName(@Param("schemaName")String schemaName,
			@Param("tableName")  String tableName,
			@Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);
}
