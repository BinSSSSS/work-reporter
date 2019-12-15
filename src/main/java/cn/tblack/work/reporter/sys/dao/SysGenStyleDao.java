package cn.tblack.work.reporter.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.tblack.work.reporter.sys.entity.SysGenStyle;

public interface SysGenStyleDao extends JpaRepository<SysGenStyle, String>{

	@Query("SELECT s FROM #{#entityName} s WHERE s.name = :name")
	List<SysGenStyle> findAllByName(String name);
	
	
	@Query("SELECT s FROM #{#entityName} s WHERE s.name LIKE %:name%")
	List<SysGenStyle> blurredFindAllByName(String name);
	
}
