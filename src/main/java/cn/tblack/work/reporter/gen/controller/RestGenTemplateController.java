package cn.tblack.work.reporter.gen.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.annotation.HasAnyRole;
import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.SysGenTemp;
import cn.tblack.work.reporter.sys.service.SysGenTempService;
import cn.tblack.work.reporter.util.DatabaseTableIdGenerator;

@HasAnyRole
@RestController
@RequestMapping("/gen-template")
public class RestGenTemplateController {

	private static Logger log = LoggerFactory.getLogger(RestGenTemplateController.class);

	@Autowired
	private SysGenTempService templateService;

	@PostMapping(value = "/select-list")
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

	@RequestMapping(value = "/page-list")
	public LaYuiPage<SysGenTemp> getTemplatePageList(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = "10") Integer limit,
			@RequestParam(name = "styleId", defaultValue = "") String styleId) {

		LaYuiPage<SysGenTemp> tempPage = null;

		try {

			Pageable pageable = PageRequest.of(page - 1, limit);
			Page<SysGenTemp> pageList = templateService.findAllByStyleId(styleId,pageable);

			tempPage = new LaYuiPage<>(pageList);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("进行模板的分页查询的时候出错，出错信息为:" + e.getMessage());
		}

		return tempPage;
	}

	@RequestMapping(value = "/get")
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

	@RequestMapping(value = "/create")
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

	@RequestMapping(value = "/update")
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

	@RequestMapping(value = "/delete")
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
