package cn.tblack.work.reporter.sys.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.service.AbstractDBService;
import cn.tblack.work.reporter.sys.entity.OssConfig;

public interface OssConfigService extends AbstractDBService<Integer>{

	List<OssConfig> findAll();

	List<OssConfig> findAll(Sort sort);

	List<OssConfig> findAllById(Iterable<Integer> ids);

	List<OssConfig> saveAll(Iterable<OssConfig> entities);

	void flush();

	OssConfig saveAndFlush(OssConfig entity);

	void deleteInBatch(Iterable<OssConfig> entities);

	void deleteAllInBatch();

	OssConfig getOne(Integer id);

	OssConfig save(OssConfig entity);

	OssConfig findById(Integer id);
	
	LaYuiPage<OssConfig> findAll(Pageable pageable);
	
	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(OssConfig entity);

	void deleteAll(Iterable<? extends OssConfig> entities);

	void deleteAll();

}
