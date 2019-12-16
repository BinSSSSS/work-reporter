package cn.tblack.work.reporter.sys.service;


import java.util.List;

import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.enums.VCodeEmailTypes;
import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.VerificationMail;
	

public interface VerificationMailService extends AbstractDBService<Integer>{
	List<VerificationMail> findAll();

	List<VerificationMail> findAll(Sort sort);

	List<VerificationMail> findAllById(Iterable<Integer> ids);

	List<VerificationMail> saveAll(Iterable<VerificationMail> entities);

	void flush();

	VerificationMail saveAndFlush(VerificationMail entity);

	void deleteInBatch(Iterable<VerificationMail> entities);

	void deleteAllInBatch();

	VerificationMail getOne(Integer id);

	VerificationMail save(VerificationMail entity);

	VerificationMail findById(Integer id);

	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(VerificationMail entity);

	void deleteAll(Iterable<? extends VerificationMail> entities);

	void deleteAll();
	
	/**
	 * @!_!查找某个邮箱最后一封发送的邮件
	 * @param email
	 * @return
	 */
	VerificationMail findLastMail(String email,VCodeEmailTypes type);

}
