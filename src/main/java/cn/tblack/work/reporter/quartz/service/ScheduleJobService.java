package cn.tblack.work.reporter.quartz.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import cn.tblack.work.reporter.quartz.entity.ScheduleJob;


public interface ScheduleJobService {

	List<ScheduleJob> findAll();

	List<ScheduleJob> findAll(Sort sort);

	List<ScheduleJob> findAllById(Iterable<Integer> ids);

	List<ScheduleJob> saveAll(Iterable<ScheduleJob> entities);

	void flush();

	ScheduleJob saveAndFlush(ScheduleJob entity);

	void deleteInBatch(Iterable<ScheduleJob> entities);

	void deleteAllInBatch();

	ScheduleJob getOne(Integer id);

	ScheduleJob save(ScheduleJob entity);

	ScheduleJob findById(Integer id);
	
	boolean existsById(Integer id);

	long count();

	void deleteById(Integer id);

	void delete(ScheduleJob entity);

	void deleteAll(Iterable<? extends ScheduleJob> entities);

	void deleteAll();

	Page<ScheduleJob> findAll(Pageable pageable);
}
