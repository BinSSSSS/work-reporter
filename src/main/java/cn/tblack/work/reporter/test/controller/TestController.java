package cn.tblack.work.reporter.test.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.tblack.work.reporter.enums.VCodeEmailTypes;
import cn.tblack.work.reporter.quartz.dao.ScheduleJobDao;
import cn.tblack.work.reporter.quartz.entity.ScheduleJob;
import cn.tblack.work.reporter.quartz.service.MailSenderService;
import cn.tblack.work.reporter.quartz.service.ReminderService;
import cn.tblack.work.reporter.sys.dao.SysConfigDao;
import cn.tblack.work.reporter.sys.dao.SysGenDao;
import cn.tblack.work.reporter.sys.entity.SysConfig;
import cn.tblack.work.reporter.sys.entity.SysTable;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.entity.VerificationMail;
import cn.tblack.work.reporter.sys.service.VerificationMailService;

@ConditionalOnProperty(prefix = "web.test-controller", name = "enable")
@RestController
public class TestController {

	private static Logger log = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private SysConfigDao sysConfigDao;

	@Autowired
	private ScheduleJobDao scheduleJobDao;

	@Autowired
	private ReminderService reminderService;

	@Autowired
	private MailSenderService mailSenderService;

	@Autowired
	private SysGenDao genDao;

	@Autowired
	private VerificationMailService vMailService;

	
	
//	@Autowired
//	private SysUserService userService;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/sys")
	public List<SysConfig> sysList() {

		return sysConfigDao.findAll();
	}

	@RequestMapping("/quartz")
	public List<ScheduleJob> quartzList() {
		return scheduleJobDao.findAll();
	}

	@RequestMapping("/hi")
	public String hi() {

		
		SysUser user = new SysUser();
		user.setId("123");
		user.setEmail("12367612@qq.com");
//		userService.update(user);
		
		return "hi";
	}

	@RequestMapping("/hello")
	public String hello() throws IOException {
		SysUser user = new SysUser();
		user.setUsername(null);
		user.setId("123");
//		user.setEmail("12367612@qq.com");
//		userService.save(user);
		
		if(user.getId().equals("123")) {
			throw new IOException();
		}
		
		// log.info("查询用户信息: " + userService.findById("asdasdasdasdasdasd"));
		// log.info("查询的数据信息为:" + vMailService.findById(1));
		return "hello";
	}

	@RequestMapping("/insert")
	public String insert() {

		VerificationMail vMail = new VerificationMail();

		vMail.setCode("223431");
		vMail.setType(VCodeEmailTypes.PASSWORD_RESET_CODE);
		vMail.setDeadline(new Date());
		vMail.setCreateTime(new Date());
		vMail.setRecipientMail("1395926989@qq.com");
		vMail.setWeights(10923712312L);

		vMailService.save(vMail);

		return "insert";
	}

	@RequestMapping("/find")
	public String find() {
		vMailService.findLastMail("1395926989@qq.com", VCodeEmailTypes.PASSWORD_FORGET_CODE);
		return "find";
	}

//	@RequestMapping("/table")
//	public String table() {
//
//		List<Object> objList = tableDao.findAllBySchema();
//
//		for (Object object : objList) {
//			log.info("当前的对象信息为: " + object);
//			log.info("当前的对象类为: " + object.getClass());
//		}
//		log.info(objList.toString());
//
//		return tableDao.findAllBySchema().toString();
//	}

	@RequestMapping("/tb")
	public String tb() throws IllegalAccessException, InvocationTargetException {

		List<Map<String, Object>> objList = genDao.findAllBySchema();

		for (Map<String, ? extends Object> map : objList) {
			SysTable table = new SysTable();
			BeanUtils.populate(table, map);
			log.info("包装成一个Table对象为: " + table);
		}
		log.info(objList.toString());

		return genDao.findAllBySchema().toString();
	}

	@RequestMapping("/pause")
	public String pause() {

		reminderService.updateDeprecated(2, (short) 1);

		return "pause";
	}

	@RequestMapping("/state")
	public String state() {

		mailSenderService.updateSendState(5, (short) 1);

		return "state";
	}

	@RequestMapping("/map")
	@SuppressWarnings("unchecked")
	public Map<String, String> getMap() {

		Map<String, String> map = restTemplate.getForObject("http://localhost:7777/getMap", Map.class);
		
		Map<String, String> map2 = restTemplate.getForObject("http://localhost:7777/getmap", Map.class);
		
		log.info("返回的map为: " + map2);
		
		return map;
	}

	@RequestMapping("/getMap")
	public String stringMap() {

		return "access=1&token=2&kill=3";
	}
	
	@RequestMapping("/getmap")
	public Map<String,String> smap(){
		
		Map<String,String> map =  new HashMap<String,String>();
		map.put("accessToken", "1");
		map.put("openId", "3");
		
		return map;
	}

	@RequestMapping("/smap")
	public String getSMap() {
		return restTemplate.getForObject("http://localhost:7777/getMap", String.class);
	}
	
	@RequestMapping("/smap2")
	public String getSMap2() {
		return restTemplate.getForObject("http://localhost:7777/getmap", String.class);
	}
	
}
