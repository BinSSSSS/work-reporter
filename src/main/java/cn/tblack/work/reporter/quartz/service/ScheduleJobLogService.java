package cn.tblack.work.reporter.quartz.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.quartz.entity.ScheduleJobLog;


public interface ScheduleJobLogService {
	
	List<ScheduleJobLog> findAll();

	List<ScheduleJobLog> findAll(Sort sort);

	List<ScheduleJobLog> findAllById(Iterable<Long> ids);

	List<ScheduleJobLog> saveAll(Iterable<ScheduleJobLog> entities);

	void flush();

	ScheduleJobLog saveAndFlush(ScheduleJobLog entity);

	void deleteInBatch(Iterable<ScheduleJobLog> entities);

	void deleteAllInBatch();

	ScheduleJobLog getOne(Long id);

	ScheduleJobLog save(ScheduleJobLog entity);

	ScheduleJobLog findById(Long id);
	
	boolean existsById(Long id);

	long count();

	void deleteById(Long id);

	void delete(ScheduleJobLog entity);

	void deleteAll(Iterable<? extends ScheduleJobLog> entities);

	void deleteAll();

	Page<ScheduleJobLog> findAll(Pageable pageable);
}
