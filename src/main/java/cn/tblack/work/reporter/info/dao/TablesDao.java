package cn.tblack.work.reporter.info.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.tblack.work.reporter.info.entity.Tables;

@Deprecated(since = "因为Tables表不存在主键，不推荐使用")
public interface TablesDao extends JpaRepository<Tables, Integer>{

}
