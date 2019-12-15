package cn.tblack.work.reporter.gen.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import cn.hutool.extra.template.VelocityUtil;
import cn.tblack.work.reporter.sys.entity.SysGenTemp;
import cn.tblack.work.reporter.sys.entity.SysTable;

/**
 * @!_!用于生成代码的工具类
 * @author TD唐登
 * @Date:2019年12月2日
 * @Version: 1.0(测试版)
 */
public class GenCodeUtils {
	
	
	@SuppressWarnings("deprecation")
	public static void generatorCodeToZIP(List<SysTable> tableList, ZipOutputStream zip,List<SysGenTemp> templateList) {
		
		Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);
        
        Map<String, Object> map = new HashMap<>();
        for (SysTable table : tableList) {
        	
        	map.put("tableName", table.getTableName());
            map.put("comment", table.getComment());
            map.put("pk", table.getPk());
            map.put("className", table.getClassName());
            map.put("entityName", table.getEntityName());
            map.put("pathName", table.getPathName());
            map.put("columns", table.getColumns());
            map.put("packageName", table.getPackageName());
            map.put("author", table.getAuthor());
            map.put("email", table.getEmail());
            map.put("datetime", new Date());
            
            VelocityContext context = new VelocityContext(map);
            
            for (SysGenTemp template : templateList) {
				
            	 StringWriter sw = new StringWriter();
                 String data = VelocityUtil.merge(template.getGenContext(), context);
                 
                 try {
                     // 添加到zip
                     zip.putNextEntry(new ZipEntry(replaceFilename(template.getGenFilename(), map)));
                     IOUtils.write(data, zip, "UTF-8");

                     zip.closeEntry();
                 } catch (IOException e) {
                	 e.printStackTrace();
                 }finally {
                	 IOUtils.closeQuietly(sw);
                 }
            }
        }
       
	}
	/**
	 * @!_!用来替换生成代码模板中生成文件名和路径名等，使用Map内的对象信息替换掉路径中存在的占位符
	 * @param filename
	 * @param map
	 * @return
	 */
    public static String replaceFilename(String filename,Map<String, Object> map){
        // 把className 替换成大写开头的Java Bean的名称
        filename = filename.replace("className", map.get("className").toString());
        // 把classname 替换成小写开头的Java Bean的名称
        filename = filename.replace("classname", map.get("entityName").toString());
        // 把packagePath 替换成对应的生成路径
        filename = filename.replace("packagePath", map.get("packageName").toString());
        return filename;
    }
}
