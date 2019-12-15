package cn.tblack.work.reporter.sys.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.tblack.work.reporter.enums.VCodeEmailTypes;
import cn.tblack.work.reporter.sys.entity.VerificationMail;

/**
 * @邮件验证相关数据库操作
 * @author TD唐登
 * @Date:2019年10月29日
 * @Version: 1.0(测试版)
 */
public interface VerificationMailDao extends JpaRepository<VerificationMail, Integer>{

	@Query("SELECT vm FROM #{#entityName}  vm WHERE vm.recipientMail=:email " + 
			"AND vm.weights = "
			+ "(SELECT max(t.weights) FROM #{#entityName}  t "
			+ "WHERE  t.recipientMail=:email AND t.type = :type)")
	/**
	 * @-==-根据邮件类型查询出某个邮箱地址发送的最后一封邮件
	 * @param email
	 * @return
	 */
	VerificationMail findLastMail(@Param("email") String email,@Param("type")VCodeEmailTypes type);

}
