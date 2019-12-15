package cn.tblack.work.reporter.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.sys.entity.SysColumn;


public interface SysColumnDao extends JpaRepository<SysColumn, Integer>{

	/**
	 * @!_!通过数据库名字到系统表信息中进行查询数据_
	 * @param schema
	 * @return JPA查询数据如果没有对应实体表，则返回的类型为AbstractJpaQuery.TupleConverter.TupleBackedMap该对象类型为私有的.
	 * <p>但是可以直接通过Map作为返回类型自定义的进行处理。</p>
	 */
	@Query(value = "SELECT "
			+ " column_name columnName, column_comment comment,table_name tableName, table_schema tableSchema, "
			+ " data_type dataType, column_type columnType, column_key columnKey, extra extra"
			+ " FROM information_schema.columns"
			+ " WHERE table_schema = :schema AND table_name = :tableName",nativeQuery = true)
	List<Map<String,Object>> findAllBySchema(@Param("schema") String schema,@Param("tableName") String tableName);

}
