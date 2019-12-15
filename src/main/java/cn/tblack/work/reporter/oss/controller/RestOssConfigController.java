package cn.tblack.work.reporter.oss.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.OssConfig;
import cn.tblack.work.reporter.sys.service.OssConfigService;

@RestController
@RequestMapping("/oss/config")
public class RestOssConfigController {

	private static Logger log = LoggerFactory.getLogger(RestOssConfigController.class);
	
	@Autowired
	private OssConfigService ossConfigService;
	
	@RequestMapping(value = "/select-list")
	public List<OssConfig> ossConfigList(){
		
		List<OssConfig> configList = null;
		
		try {
			configList =  ossConfigService.findAll();
		}catch(Exception e) {
			e.printStackTrace();
			log.error("查询OssConfig数据库链表出错，出错信息为: " + e.getMessage());
		}
		
		return configList;
	}

	@RequestMapping(value = "/page-list")
	public LaYuiPage<OssConfig> getPageList(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = "10") Integer limit,
			@RequestParam(name = "searchText", defaultValue = "") String searchText) {

		LaYuiPage<OssConfig> configPage = null;

		log.info("进行分页查询");
		try {

			Pageable pageable = PageRequest.of(page - 1, limit);

			Page<OssConfig> pageImpl = ossConfigService.findAll(pageable);
			
			if(pageImpl != null)
				configPage = new LaYuiPage<>(pageImpl);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询OssConfig分页列表出错，出错原因为: " + e.getMessage());
		}

		return configPage;

	}

	@RequestMapping(value = "/create")
	public WebResult createDatabase(@RequestBody OssConfig ossConfig) {

		WebResult result = new WebResult();

		log.info("传递的OssConfig信息为: " + ossConfig);

		try {

			ossConfigService.save(ossConfig);
			result.setMsg("添加成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建一个新的OssConfig对象失败，失败原因为: " + e.getMessage());
			result.setMsg("创建新的对象失败!服务器正忙");
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "/update")
	public WebResult updateTemplate(@RequestBody OssConfig ossConfig) {

		WebResult result = new WebResult();

		log.info("传递的ossConfig信息为: " + ossConfig);
		try {

			OssConfig orgGenDb = ossConfigService.findById(ossConfig.getId());

			// 如果需要更新的对象已经不存在了，那么则返回出错信息。
			if (orgGenDb == null) {
				result.setMsg("要更新的对象不存在");
				result.setSuccess(false);
				return result;
			}

			// 动态的更新， 为空的数据不进行更新。
			ossConfigService.save(ossConfig);
			result.setMsg("更新成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新Id[" + ossConfig.getId() + "]OssConfig信息出错，出错原因为:" + e.getMessage());
			result.setMsg("更新失败！服务器正忙~");
			result.setSuccess(false);
		}

		return result;

	}

	@RequestMapping(value = "/delete")
	public WebResult deleteTemplate(String ids) {

		WebResult result = new WebResult();

		String[] idArr = ids.split(",");

		try {

			for (String idStr : idArr) {
				
				ossConfigService.deleteById(Integer.valueOf(idStr));
			}

			result.setMsg("删除成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除id为["+ ids +"]的OssConfig的对象失败！失败原因为: " + e.getMessage());
			result.setMsg("删除对象失败，服务器正忙~");
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "/get")
	public OssConfig getTemplate(Integer id) {

		OssConfig ossConfig = null;

		try {

			ossConfig = ossConfigService.findById(id);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询Id为:[" + id + "]的OssConfig出错，出错信息为: " + e.getMessage());
		}
		return ossConfig;
	}

}
