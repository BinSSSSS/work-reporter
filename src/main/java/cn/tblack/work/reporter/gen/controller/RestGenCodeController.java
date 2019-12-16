package cn.tblack.work.reporter.gen.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.tblack.work.reporter.constant.DataBaseBeanNames;
import cn.tblack.work.reporter.page.LaYuiPage;
import cn.tblack.work.reporter.result.WebResult;
import cn.tblack.work.reporter.sys.entity.SysGen;
import cn.tblack.work.reporter.sys.entity.SysGenTemp;
import cn.tblack.work.reporter.sys.entity.SysTable;
import cn.tblack.work.reporter.sys.service.SysColumnService;
import cn.tblack.work.reporter.sys.service.SysGenService;
import cn.tblack.work.reporter.sys.service.SysGenTempService;
import cn.tblack.work.reporter.sys.service.SysTableService;

@RestController
@RequestMapping("/gen-code")
public class RestGenCodeController {

	private static Logger log = LoggerFactory.getLogger(RestGenCodeController.class);

	@Autowired
	private SysTableService tableService;

	@Autowired
	private SysGenService genService;

	@Autowired
	private SysGenTempService templateService;

	@Autowired
	private SysColumnService columnService;

	/**
	 * @!_!返回数据库内指定数据库的全部表名
	 * @param pageSize
	 * @param curPage
	 * @param schemaName
	 * @return
	 */
	@RequestMapping(value = "/table-list")
	public LaYuiPage<SysTable> getGenCodeList(@RequestParam(name = "limit", defaultValue = "10") Integer pageSize,
			@RequestParam(name = "page", defaultValue = "1") Integer curPage,
			@RequestParam(name = "schemaName", defaultValue = "") String schemaName,
			@RequestParam(name = "tableName", defaultValue = "") String tableName, HttpServletRequest request) {

		LaYuiPage<SysTable> genCodePage = new LaYuiPage<>();
		
		log.info("传递的数据库名称为:" +  schemaName);
		try {

			List<SysTable> tableList = null;

			if (schemaName.isEmpty()) {
				schemaName = DataBaseBeanNames.DB_SYSTEM;
			}
			Pageable pageable = PageRequest.of(curPage - 1, pageSize);

			// 将查询结果转换为一个实体类对象列表
			tableList = 
					// 分页查询数据，这里使用的自己编写的SQL语句， 使用的MYSQL分页语句。 LIMIT，数据量不多的情况下不影响性能
					tableService.findAllBySchemaAndTableName(schemaName, tableName, curPage - 1, pageSize);

			// 通过tableList构建一个PageImpl对象
			PageImpl<SysTable> pageImpl = new PageImpl<>(tableList, pageable, tableList.size());

			genCodePage = new LaYuiPage<>(pageImpl);
			genCodePage.setCount(tableService.findAllBySchemaAndTableName(schemaName, tableName).size());
		} catch (Exception e) {
			log.error("查询条件:Schema:[ " + schemaName + "], Table:[" + tableName + "]的数据库表失败，失败信息为" + e.getMessage());
			e.printStackTrace();
		}
		return genCodePage;
	}

	@PostMapping(value = "/settings")
	public Map<String, Object> getSettings() {

		log.info("配置的读取");
		Map<String, Object> map = new HashMap<>();
		try {
			List<SysGen> genList = genService.findAll();

			for (SysGen sysGen : genList) {
				map.put(sysGen.getKey(), sysGen.getValue());
			}
		} catch (Exception e) {
			log.error("查询 SysGen数据库出错，出错信息为: " + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}
	
	@PostMapping(value = "/update-settings")
	public WebResult updateSettings(HttpServletRequest request) {
		
		WebResult result = new WebResult();

		Map<String,String[]> map =  
				request.getParameterMap();;
		try {
			for (Entry<String,String[]> entry : map.entrySet()) {
				genService.updateKeyValue(
						entry.getKey(),entry.getValue()[0]);
				log.info("Key: " +  entry.getKey() + ",Value:" +  entry.getValue()[0]);
			}
			result.setMsg("更新成功");
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("更新SysGen数据出错，出错原因为:" + e.getMessage());
			result.setMsg("更新出错~服务器正忙~");
			result.setSuccess(false);
		}
		
		
		return result;
	}
	
	/**
	 * @!_!代码生成
	 * @param tableName
	 * @param schemaName
	 * @param response
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/code")
	public void generatorCode(@RequestParam("tableName") String tableName,
			@RequestParam("schemaName") String schemaName, HttpServletResponse response) throws IOException, IllegalAccessException, InvocationTargetException {

		List<SysTable> tableList = new ArrayList<>();

		log.info("传递的tableName为: " + tableName);
		
		try {
			String[] tableNameArr =  tableName.split(",");
			
			
			//对应传递的多个表名进行拆分成单个表名分别进行查询
			for (String tName : tableNameArr) {
				SysTable table = tableService.findBySchemaAndTableName(schemaName, tName);
				tableList.add(table);
			}
			
			/*tableList = EntityInjectionUtil.beanListInjection(new SysTable(),
					tableService.findAllBySchemaAndTableName(schemaName, tableName));*/
			
			Map<String, Object> settings = getSettings();

			List<SysGenTemp> templateList = templateService.findAll();

			byte[] data = columnService.generatorCode(settings, tableList, templateList);
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=\"gencode.zip\"");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream; charset=UTF-8");
			IOUtils.write(data, response.getOutputStream());
		}catch(Exception e) {
			log.error("生成代码失败~");
			e.printStackTrace();
			
		}
		
	}
	
	
}
