package cn.tblack.work.reporter.sys.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.sys.entity.SysGen;

public interface SysGenDao extends JpaRepository<SysGen, String>{

	/**
	 * @~!_!测试使用。实际生产不使用
	 * @return
	 */
	@Query(value = "SELECT table_name tableName,table_comment comment , create_time createTime, engine engine"
			+ " FROM information_schema.tables WHERE table_schema = 'wreporter_sys'",nativeQuery =  true)
	List<Map<String,Object>>  findAllBySchema();
	
	@Modifying
	@Query( value ="UPDATE wreporter_sys.sys_gen g set g.value = :value WHERE `key` = :key",nativeQuery = true)
	void updateKeyValue(@Param("key") String key,@Param("value") String value);
}
