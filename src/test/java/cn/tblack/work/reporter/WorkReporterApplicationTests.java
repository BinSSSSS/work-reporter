package cn.tblack.work.reporter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.tblack.work.reporter.sys.dao.SysTableDao;

@SpringBootTest
class WorkReporterApplicationTests {

	@Autowired
	private SysTableDao tableDao;
	
	@Test
	void contextLoads() {
		
		tableDao.findAllBySchema("wreporter_sys");
	}

}
