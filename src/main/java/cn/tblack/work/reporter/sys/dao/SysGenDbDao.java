package cn.tblack.work.reporter.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.tblack.work.reporter.sys.entity.SysGenDb;

public interface  SysGenDbDao extends JpaRepository<SysGenDb, String>{

	/**
	 * @!_!模糊查找
	 * @param dbName
	 * @return
	 */
	@Query("SELECT d FROM #{#entityName} d WHERE d.dbName LIKE %:dbName%")
	List<SysGenDb> blurredFindAllByDbName(String dbName);

	@Query("SELECT d FROM #{#entityName} d WHERE d.dbName = :dbName")
	List<SysGenDb> findAllByDbName(String dbName);

}
