package cn.tblack.work.reporter.quartz.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.tblack.work.reporter.quartz.entity.ScheduleJob;

public interface ScheduleJobDao extends JpaRepository<ScheduleJob, Integer>{

}
