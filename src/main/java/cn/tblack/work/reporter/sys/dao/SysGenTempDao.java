package cn.tblack.work.reporter.sys.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.sys.entity.SysGenTemp;

public interface SysGenTempDao extends JpaRepository<SysGenTemp, String>{

	/**
	 * @!_!通过生成模板的文件名来查找对象
	 * @param filename
	 * @return
	 */
	@Query("SELECT t FROM #{#entityName} t WHERE t.genFilename = :filename")
	List<SysGenTemp> findAllByGenFilename(String filename);

	@Query("SELECT t FROM #{#entityName} t WHERE t.genFilename LIKE %:filename%")
	List<SysGenTemp> blurredFindAllByFilename(String filename);

	@Query("SELECT t FROM #{#entityName} t WHERE t.styleId = :styleId")
	Page<SysGenTemp> findAllByStyleId(@Param("styleId")String styleId, Pageable pageable);
	
	@Query("SELECT t FROM #{#entityName} t WHERE t.styleId = :styleId")
	List<SysGenTemp> findAllByStyleId(String styleId);
}
