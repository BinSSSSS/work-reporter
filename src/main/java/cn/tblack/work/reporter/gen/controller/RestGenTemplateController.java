package cn.tblack.work.reporter.gen.controller;

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
import cn.tblack.work.reporter.sys.entity.SysGenTemp;
import cn.tblack.work.reporter.sys.service.SysGenTempService;
import cn.tblack.work.reporter.util.DatabaseTableIdGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "生成代码模板管理控制器")
@RestController
@RequestMapping("/gen-template")
@NeedAnyRole
public class RestGenTemplateController {

	private static Logger log = LoggerFactory.getLogger(RestGenTemplateController.class);

	@Autowired
	private SysGenTempService templateService;
	
	@ApiOperation("拿到所有生成模板列表")
	@RequestMapping(value = "/select-list",method = {RequestMethod.POST,RequestMethod.GET})
	public List<SysGenTemp> selectAllGenStyle(@RequestParam(name = "searchText", defaultValue = "") String searchText) {

		List<SysGenTemp> genStyleList = null;
		try {
			genStyleList = templateService.selectAllByGenFilename(searchText, true);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过查询条件name[ " + searchText + "]查询生成模板数据失败！失败原因为: " + e.getMessage());
		}

		return genStyleList;
	}
	
	@ApiOperation("拿到所有生成模板分页列表")
	@RequestMapping(value = "/page-list",method = {RequestMethod.POST,RequestMethod.GET})
	public LaYuiPage<SysGenTemp> getTemplatePageList(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = "10") Integer limit,
			@RequestParam(name = "styleId", defaultValue = "") String styleId) {

		LaYuiPage<SysGenTemp> tempPage = null;

		try {

			Pageable pageable = PageRequest.of(page - 1, limit);
			tempPage = templateService.findAllByStyleId(styleId,pageable);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("进行模板的分页查询的时候出错，出错信息为:" + e.getMessage());
		}

		return tempPage;
	}

	@ApiOperation(value = "拿到生成代码模板信息")
	@ApiImplicitParam(name ="id", value = "生成代码模板id", dataTypeClass = String.class, required = true)
	@RequestMapping(value = "/get",method = {RequestMethod.POST,RequestMethod.GET})
	public SysGenTemp getTemplate(String id) {

		SysGenTemp template = null;

		try {

			template = templateService.findById(id);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询Id为:[" + id + "]的SysGenTemp出错，出错信息为: " + e.getMessage());
		}
		return template;
	}
	
	@ApiOperation(value = "创建一个生成代码模板",consumes = "application/json")
	@ApiImplicitParam(name = "template", value = "生成代码模板信息", dataTypeClass = SysGenTemp.class, required = true)
	@PostMapping(value = "/create")
	public WebResult createTemplate(@RequestBody SysGenTemp template) {

		WebResult result = new WebResult();

//		log.info("传递的SysGenTemp信息为: " + template);

		try {
			// 需要生成一个随机性的id
			template.setId(DatabaseTableIdGenerator.generatorTableId(templateService));

			templateService.save(template);
			result.setMsg("添加成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建一个新的SysGenTemp对象失败，失败原因为: " + e.getMessage());
			result.setMsg("创建新的对象失败!服务器正忙");
			result.setSuccess(false);
		}

		return result;
	}
	
	@ApiOperation(value = "更新一个生成代码模板",consumes = "application/json")
	@ApiImplicitParam(name = "template", value = "生成代码模板信息", dataTypeClass = SysGenTemp.class, required = true)
	@PostMapping(value = "/update")
	public WebResult updateTemplate(@RequestBody SysGenTemp template) {

		WebResult result = new WebResult();

//		log.info("传递的template信息为: " + template);
		try {

			SysGenTemp orgTemplate = templateService.findById(template.getId());

			// 如果需要更新的对象已经不存在了，那么则返回出错信息。
			if (orgTemplate == null) {
				result.setMsg("要更新的对象不存在");
				result.setSuccess(false);
				return result;
			}

			// 动态的更新， 为空的数据不进行更新。
			templateService.save(template);
			result.setMsg("更新成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新Id[" + template.getId() + "]SysGenTemp信息出错，出错原因为:" + e.getMessage());
			result.setMsg("更新失败！服务器正忙~");
			result.setSuccess(false);
		}

		return result;

	}
	@ApiOperation(value = "删除多个生成代码模板")
	@ApiImplicitParam(name ="ids", value = "多个生成代码模板id", dataTypeClass = String.class, required = true)
	@PostMapping(value = "/delete")
	public WebResult deleteTemplate(String ids) {

		WebResult result = new WebResult();

		String[] idArr = ids.split(",");

		try {
			
			for (String id : idArr) {
				templateService.deleteById(id);
			}

			result.setMsg("删除成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除id为[" +ids +"]的SysGenTemp的对象失败！失败原因为: " + e.getMessage());
			result.setMsg("删除对象失败，服务器正忙~");
			result.setSuccess(false);
		}

		return result;
	}
}
