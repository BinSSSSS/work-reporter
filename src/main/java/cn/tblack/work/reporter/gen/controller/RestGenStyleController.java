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
import cn.tblack.work.reporter.sys.entity.SysGenStyle;
import cn.tblack.work.reporter.sys.entity.SysGenTemp;
import cn.tblack.work.reporter.sys.service.SysGenStyleService;
import cn.tblack.work.reporter.sys.service.SysGenTempService;
import cn.tblack.work.reporter.util.DatabaseTableIdGenerator;

@HasAnyRole
@RestController
@RequestMapping("/gen-style")
public class RestGenStyleController {

	private static Logger log = LoggerFactory.getLogger(RestGenStyleController.class);

	@Autowired
	private SysGenStyleService genStyleService;

	@Autowired
	private SysGenTempService templateService;
	
	@PostMapping(value = "/select-list")
	public List<SysGenStyle> selectAllGenStyle(
			@RequestParam(name = "searchText", defaultValue = "") String searchText) {

		List<SysGenStyle> genStyleList = null;
		try {
			genStyleList = genStyleService.selectAllByName(searchText, true);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("通过查询条件name[ " + searchText + "]查询生成模板数据失败！失败原因为: " + e.getMessage());
		}

		return genStyleList;
	}
	
	@RequestMapping(value = "/page-list")
	public LaYuiPage<SysGenStyle> getPageList(@RequestParam(name = "page",defaultValue = "1") Integer page,
			@RequestParam(name = "limit",defaultValue = "10")Integer limit,
			@RequestParam(name = "dbName",defaultValue = "") String dbName){
		
		LaYuiPage<SysGenStyle> stylePage = null;
		
		try {
			
			Pageable pageable = PageRequest.of(page - 1, limit);
			
			stylePage =  genStyleService.findAll(pageable);
			
		}catch(Exception e) {
			e.printStackTrace();
			log.error("查询SysGenStyle出错，出错原因为: " + e.getMessage());
		}
		
		return stylePage;
		
	}
	@RequestMapping(value = "/create")
	public WebResult createDatabase(@RequestBody SysGenStyle style) {

		WebResult result = new WebResult();

		log.info("传递的SysGenStyle信息为: " + style);

		try {
			// 需要生成一个随机性的id
			style.setId(DatabaseTableIdGenerator.generatorTableId(genStyleService));

			genStyleService.save(style);
			result.setMsg("添加成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建一个新的SysGenStyle对象失败，失败原因为: " + e.getMessage());
			result.setMsg("创建新的对象失败!服务器正忙");
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "/update")
	public WebResult updateTemplate(@RequestBody SysGenStyle style) {

		WebResult result = new WebResult();

		log.info("传递的style信息为: " + style);
		try {

			SysGenStyle orgGenDb = genStyleService.findById(style.getId());

			// 如果需要更新的对象已经不存在了，那么则返回出错信息。
			if (orgGenDb == null) {
				result.setMsg("要更新的对象不存在");
				result.setSuccess(false);
				return result;
			}

			// 动态的更新， 为空的数据不进行更新。
			genStyleService.save(style);
			result.setMsg("更新成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("更新Id[" + style.getId() + "]SysGenStyle信息出错，出错原因为:" + e.getMessage());
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
				genStyleService.deleteById(id);
			}

			result.setMsg("删除成功!");
			result.setSuccess(true);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除ids为[" + ids + "]的SysGenStyle的对象失败！失败原因为: " + e.getMessage());
			result.setMsg("删除对象失败，服务器正忙~");
			result.setSuccess(false);
		}

		return result;
	}

	@RequestMapping(value = "/get")
	public SysGenStyle getTemplate(String id) {

		SysGenStyle genStyle = null;

		try {

			genStyle = genStyleService.findById(id);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询Id为:[" + id + "]的SysGenStyle出错，出错信息为: " + e.getMessage());
		}
		return genStyle;
	}
	
	/**
	 * @!_!拷贝框架
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/copy")
	public WebResult copyStyele(String id) {
		
		WebResult result =  new WebResult();
		
		try {
			
			//首先查询数据库中是否存在该框架类型数据
			SysGenStyle style = genStyleService.findById(id);
			
			if(style == null) {
				result.setMsg("需要复制的框架类型不存在");
				result.setSuccess(false);
				return result;
			}
			
			//进行框架类型的复制
			style.setId(DatabaseTableIdGenerator.generatorTableId(genStyleService));
			genStyleService.save(style);
			
			//查找所有的该框架类型的模板数据。
			List<SysGenTemp> templateList = templateService.findAllByStyleId(id);
			
			
			//进行模板数据的复制
			for (SysGenTemp template : templateList) {
				template.setId(DatabaseTableIdGenerator.generatorTableId(templateService));
				template.setStyleId(style.getId());
				templateService.save(template);
			}
			
			result.setMsg("复制完成~");
			result.setSuccess(true);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("拷贝id为[" + id + "]的框架出错，出错信息为: " + e.getMessage());
			result.setMsg("拷贝失败！服务器正忙");
			result.setSuccess(false);
		}
		
		
		return result;
	}
	
	
	@RequestMapping(value = "/template")
	public String getTemplate() {
		return 
				"package ${packageName}.entity;<br/>" + 
				"<br/>" + 
				"import java.io.Serializable;<br/>" + 
				"<br/>" + 
				"import javax.persistence.Entity;<br/>" + 
				"import javax.persistence.GeneratedValue;<br/>" + 
				"import javax.persistence.GenerationType;<br/>" + 
				"import javax.persistence.Id;<br/>" + 
				"import javax.persistence.Table;<br/>" + 
				"<br/>" + 
				"<br/>" + 
				"/**<br/>" + 
				"* ${comment}<br/>" + 
				"* <br/>" + 
				"* @author ${author}<br/>" + 
				"* @email ${email}<br/>" + 
				"* @date ${datetime}<br/>" + 
				"*/<br/>" + 
				"@Entity <br/>" + 
				"public class ${className} implements Serializable {<br/>" + 
				"	private static final long serialVersionUID = 1L;<br/>" + 
				"<br/>" + 
				"	@Id<br/>" + 
				"	/**${pk.comment}*/<br/>" + 
				"	private ${pk.attrType} ${pk.attrName};<br/>" + 
				"<br/>" + 
				"	#foreach ($column in $columns)<br/>" + 
				"	/**$column.comment**/<br/>" + 
				"	private $column.attrType $column.attrName;<br/>" + 
				"	#end<br/>" + 
				"<br/>" + 
				"	#foreach ($column in $columns)<br/>" + 
				"<br/>" + 
				"	public void set${column.attrName}($column.attrType $column.attrName) {<br/>" + 
				"		this.$column.attrName = $column.attrName;<br/>" + 
				"	}<br/>" + 
				"<br/>" + 
				"	public $column.attrType get${column.attrName}() {<br/>" + 
				"		return $column.attrName;<br/>" + 
				"	}<br/>" + 
				"	#end<br/>" + 
				"}<br/>";

	}
}
