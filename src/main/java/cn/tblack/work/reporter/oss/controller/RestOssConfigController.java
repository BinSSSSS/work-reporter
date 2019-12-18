package cn.tblack.work.reporter.oss.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.annotation.NeedAnyRole;
import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.OssConfig;
import cn.tblack.work.reporter.sys.service.OssConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "存储桶配置相关控制器")
@RestController
@RequestMapping("/oss/config")
@NeedAnyRole
public class RestOssConfigController {

	private static Logger log = LoggerFactory.getLogger(RestOssConfigController.class);
	
	@Autowired
	private OssConfigService ossConfigService;
	
	@ApiOperation(value = "存储桶配置列表")
	@RequestMapping(value = "/select-list",method = {RequestMethod.POST,RequestMethod.GET})
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
	
	@ApiOperation(value = "存储桶配置分页列表")
	@RequestMapping(value = "/page-list",method = {RequestMethod.POST,RequestMethod.GET})
	public LaYuiPage<OssConfig> getPageList(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = "10") Integer limit,
			@RequestParam(name = "searchText", defaultValue = "") String searchText) {

		LaYuiPage<OssConfig> configPage = null;

		try {

			Pageable pageable = PageRequest.of(page - 1, limit);

			configPage = ossConfigService.findAll(pageable);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询OssConfig分页列表出错，出错原因为: " + e.getMessage());
		}

		return configPage;

	}
	
	@ApiOperation(value = "创建一个存储桶配置",consumes = "application/json")
	@ApiImplicitParam(name = "ossConfig", value = "存储桶配置信息", dataTypeClass = OssConfig.class, required = true)
	@PostMapping(value = "/create")
	public WebResult createDatabase(@RequestBody OssConfig ossConfig) {

		WebResult result = new WebResult();

//		log.info("传递的OssConfig信息为: " + ossConfig);

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

	@ApiOperation(value = "更新一个存储桶配置",consumes = "application/json")
	@ApiImplicitParam(name = "ossConfig", value = "存储桶配置信息", dataTypeClass = OssConfig.class, required = true)
	@PostMapping(value = "/update")
	public WebResult updateTemplate(@RequestBody OssConfig ossConfig) {

		WebResult result = new WebResult();

//		log.info("传递的ossConfig信息为: " + ossConfig);
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

	@ApiOperation(value = "删除多个存储桶配置")
	@ApiImplicitParam(name ="ids", value = "多个存储桶配置id", dataTypeClass = String.class, required = true)
	@PostMapping(value = "/delete")
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

	@ApiOperation(value = "拿到存储桶配置信息")
	@ApiImplicitParam(name ="id", value = "存储桶配置id", dataTypeClass = String.class, required = true)
	@RequestMapping(value = "/get",method = {RequestMethod.POST,RequestMethod.GET})
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
