package cn.tblack.work.reporter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.tblack.work.reporter.social.enums.SocialPlatformType;
import cn.tblack.work.reporter.sys.entity.TempUser;
import cn.tblack.work.reporter.sys.service.SysUserService;
import cn.tblack.work.reporter.sys.service.TempUserService;

@SpringBootTest
class WorkReporterApplicationTests {

	@Autowired
	private TempUserService tempUserService ;
	
	
	@Autowired
	private SysUserService userService;
	
	@Test
	void contextLoads() {
		
		TempUser tuser = new TempUser();
		
		tuser.setAvatarUrl("1");
		tuser.setNickname("@");
		tuser.setOpenId("123");
		tuser.setSocialPlatform(SocialPlatformType.QQ);
		
//		tempUserService.save(tuser);
		
//		System.err.println(tempUserService.findByOpenIdAndSocialPlatform("123", SocialPlatformType.QQ));
		
//		tempUserService.delete(tuser);
	}

	@Test
	void testUserService() {
		
		System.err.println(userService.findByUsername("我笑起来真丑😀"));
	}
}
