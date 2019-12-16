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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.annotation.HasAnyRole;
import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.SysGenDb;
import cn.tblack.work.reporter.sys.service.SysGenDbService;
import cn.tblack.work.reporter.util.DatabaseTableIdGenerator;

@HasAnyRole
@RestController
@RequestMapping("/gen-db")
public class RestGenDbController {

	private static Logger log = LoggerFactory.getLogger(RestGenDbController.class);

	@Autowired
	private SysGenDbService genDbService;

	@PostMapping(value = "/select-list")
	public List<SysGenDb> selectAllGenStyle(@RequestParam(name = "searchText", defaultValue = "") String searchText) {

		List<SysGenDb> genDbList = null;
		try {
			genDbList = genDbService.selectByDbName(searchText, true);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过查询条件dbName[ " + searchText + "]查询SysGenDb数据失败！失败原因为: " + e.getMessage());
		}

		return genDbList;
	}

	@RequestMapping(value = "/page-list")
	public LaYuiPage<SysGenDb> getPageList(@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "limit", defaultValue = "10") Integer limit,
			@RequestParam(name = "dbName", defaultValue = "") String dbName) {

		LaYuiPage<SysGenDb> dbPage = null;

		log.info("进行分页查询");
		try {

			Pageable pageable = PageRequest.of(page - 1, limit);

			dbPage = genDbService.findAll(pageable);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询SysGenDb分页列表出错，出错原因为: " + e.getMessage());
		}

		return dbPage;

	}

	@RequestMapping(value = "/create")
	public WebResult createDatabase(@RequestBody SysGenDb genDb) {

		WebResult result = new WebResult();

		log.info("传递的SysGenDb信息为: " + genDb);

		try {
			// 需要生成一个随机性的id
			genDb.setId(DatabaseTableIdGenerator.generatorTableId(genDbService));

			genDbService.save(genDb);
			result.setMsg("添加成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建一个新的SysGenDb对象失败，失败原因为: " + e.getMessage());
			result.setMsg("创建新的对象失败!服务器正忙");
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "/update")
	public WebResult updateTemplate(@RequestBody SysGenDb genDb) {

		WebResult result = new WebResult();

		log.info("传递的genDb信息为: " + genDb);
		try {

			SysGenDb orgGenDb = genDbService.findById(genDb.getId());

			// 如果需要更新的对象已经不存在了，那么则返回出错信息。
			if (orgGenDb == null) {
				result.setMsg("要更新的对象不存在");
				result.setSuccess(false);
				return result;
			}

			// 动态的更新， 为空的数据不进行更新。
			genDbService.save(genDb);
			result.setMsg("更新成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新Id[" + genDb.getId() + "]SysGenDb信息出错，出错原因为:" + e.getMessage());
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
				genDbService.deleteById(id);
			}

			result.setMsg("删除成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除id为["+ ids +"]的SysGenDb的对象失败！失败原因为: " + e.getMessage());
			result.setMsg("删除对象失败，服务器正忙~");
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "/get")
	public SysGenDb getTemplate(String id) {

		SysGenDb genDatabase = null;

		try {

			genDatabase = genDbService.findById(id);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询Id为:[" + id + "]的SysGenDb出错，出错信息为: " + e.getMessage());
		}
		return genDatabase;
	}

}
