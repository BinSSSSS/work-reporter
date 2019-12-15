package cn.tblack.work.reporter.sys.service.impl;

import static cn.tblack.work.reporter.gen.util.DBNameConverterUtils.toJavaClassName;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tblack.work.reporter.gen.util.EntityInjectionUtil;
import cn.tblack.work.reporter.gen.util.GenCodeUtils;
import cn.tblack.work.reporter.sys.dao.SysColumnDao;
import cn.tblack.work.reporter.sys.entity.SysColumn;
import cn.tblack.work.reporter.sys.entity.SysGenTemp;
import cn.tblack.work.reporter.sys.entity.SysTable;
import cn.tblack.work.reporter.sys.service.SysColumnService;
import static cn.tblack.work.reporter.gen.util.DBNameConverterUtils.*;

@Service
@Transactional
public class SysColumnServiceImpl implements SysColumnService {

	@Autowired
	private SysColumnDao columnDao;
	
	@Override
	public List<SysColumn> findAll() {
		return columnDao.findAll();
	}

	@Override
	public List<SysColumn> findAll(Sort sort) {
		return columnDao.findAll(sort);
	}

	@Override
	public List<SysColumn> findAllById(Iterable<Integer> ids) {
		return columnDao.findAllById(ids);
	}

	@Override
	public List<SysColumn> saveAll(Iterable<SysColumn> entities) {
		return columnDao.saveAll(entities);
	}

	@Override
	public void flush() {
		columnDao.flush();
	}

	@Override
	public SysColumn saveAndFlush(SysColumn entity) {
		return columnDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysColumn> entities) {
		columnDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		columnDao.deleteAllInBatch();
	}

	@Override
	public SysColumn getOne(Integer id) {
		return columnDao.getOne(id);
	}

	@Override
	public SysColumn save(SysColumn entity) {
		return columnDao.save(entity);
	}

	@Override
	public SysColumn findById(Integer id) {
		return columnDao.findById(id).get();
	}


	@Override
	public boolean existsById(Integer id) {
		return columnDao.existsById(id);
	}

	@Override
	public long count() {
		return columnDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		columnDao.deleteById(id);
	}

	@Override
	public void delete(SysColumn entity) {
		columnDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysColumn> entities) {
		columnDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		columnDao.deleteAll();
	}

	@Override
	public Page<SysColumn> findAll(Pageable pageable) {
		return columnDao.findAll(pageable);
	}

	@Override
	public List<Map<String, Object>> findAllBySchema(String schema, String tableName) {
		return columnDao.findAllBySchema(schema, tableName);
	}

	@Override
	public byte[] generatorCode(Map<String, Object> settings, List<SysTable> tableList,List<SysGenTemp> templateList) {
		
		
		for (SysTable table : tableList) {
				
			//设置生成表的一些属性信息
			String tableName =  table.getTableName();
			String schemaName = table.getTableSchema();
			table.setClassName(toJavaClassName(tableName));  //将表名转化为Java类名
			table.setEntityName(toCamelCase(tableName));	//将表名转化为使用的字段名。类名首字母小写
			table.setAuthor(settings.get("author") + "");
			table.setEmail(settings.get("email") + "");
			table.setPathName(settings.get("pathName") + "");
			table.setPackageName(settings.get("package") + "");
			
			
			List<SysColumn> columnList = null;
			
			//查找该表的所有字段
			columnList = EntityInjectionUtil.beanListInjection(new SysColumn(),
					findAllBySchema(schemaName, tableName));
			
			//主键
			SysColumn pk = null;
			
			//遍历字段设置一些生成代码字段的属性
			for (SysColumn column : columnList) {
				String attrName = toCamelCase(column.getColumnName());
				column.setAttrName(attrName);	//设置该表在Java中使用的属性名
				column.setAttrType(settings.get(column.getDataType()) + ""); //设置为Java的字段属性
				column.setUpperAttrName(toJavaClassName(attrName));  //设置为首字母大写的形式
				if (column.getColumnKey().equalsIgnoreCase("PRI")) {
					pk = column;
				}
			}
			table.setPk(pk);
			columnList.remove(pk);  //从字段中移除掉主键
			table.setColumns(columnList);
		}
		
		//生成代码信息， 使用的是 hutool工具
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		GenCodeUtils.generatorCodeToZIP(tableList, zip, templateList);
		
		try {
			zip.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputStream.toByteArray();
	}

}
