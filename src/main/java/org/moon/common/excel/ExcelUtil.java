package org.moon.common.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;



public class ExcelUtil {
	
	private final static String EXCEL_CONFIG = "excel-config";
	
	 private POIFSFileSystem fs;   
	 private HSSFWorkbook wb;   
	 private HSSFSheet sheet;   
	 private HSSFRow row;
	private ExcelUtil() {
		//工具类不允许实例化
	}
	

	
	/**
		 * 导出Excel文件,根据指定路径下的模板生成输出的Excel文件
		 * 
		 * @param exportObjects	待导出的对象数组
		 * @param exportInfo	模板文件的其他附加信息(非结果集内容)
		 * @param templateFilename	模板文件名(不带扩展名),在excel-config文件夹下的模板文件
		 * @param autoPagination	是否分页
		 * @param autoSheet	是否分工作表
		 * @return	返回生成的Excel文件下载路径
		 * @throws Exception
		 */
		private static String exportExcel(List<Map> exportObjects,Map exportInfo,String templateFilename,boolean autoPagination,boolean autoSheet) throws Exception{
			String filename = templateFilename;
			String separator = System.getProperty("file.separator");
			if (filename.indexOf(".xls") == -1) {
				filename += ".xls";
			}
	
			//临时路径是服务器当前war下面的excel-config目录
	//		String templateDir = ServletActionContext.getServletContext().getRealPath(ExcelUtil.EXCEL_CONFIG) + System.getProperty("file.separator");
			String templateDir = ExcelUtil.class.getResourceAsStream(separator + ExcelUtil.EXCEL_CONFIG) + separator;
			
			String excelExportMaxnum = "500";
			
			String tempDir = templateDir + "temp" + separator;
			File file=new File(tempDir);
			
			if (!file.exists()) {
				//创建临时目录
				file.mkdirs();
			}
			
			String templateFile=templateDir+filename;
			String outputFile=tempDir+generateOutputExcelFile(filename);
			ExcelTemplate template=new ExcelTemplate(templateFile,outputFile);
			
			template.setAutoPagination(autoPagination);
			template.setAutoSheet(autoSheet);
			template.setIncludeFormula(true);
			
			int excelExportMaxnumInt = 0;
			try{
				excelExportMaxnumInt = Integer.parseInt(excelExportMaxnum);
			}catch (Exception e){
				e.printStackTrace();
			}
			template.setMaxRow(excelExportMaxnumInt);
			template.generate(exportObjects,exportInfo);
	
			return outputFile;
		}



	/**
	 * 生成EXCEL输出文件，默认带时间戳
	 * @param templateFilename 文件名
	 * @return
	 */
	private static String generateOutputExcelFile(String templateFilename){
		String filename=templateFilename;
		if(templateFilename.endsWith(".xls")){
			filename=templateFilename.substring(0,templateFilename.length()-4);
		}
	
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String datetimeString=format.format(new Date());
	
		filename=filename+"_"+datetimeString+".xls";
		return filename;
	}



	/**
	 * 导出Excel文件,根据指定路径下的模板生成输出的Excel文件
	 * 
	 * @param exportObjects	待导出的对象数组
	 * @param exportInfo	模板文件的其他附加信息(非结果集内容)
	 * @param templateFilePath	模板文件路径(不带扩展名),在excel-config文件夹下的模板文件
	 * @param outFilePath 文件输出路径
	 * @param autoPagination	是否分页
	 * @param autoSheet	是否分工作表
	 * @return	返回生成的Excel文件下载路径
	 * @throws Exception
	 */
	public static String exportExcel(List<Map> exportObjects,Map exportInfo,String templateFilePath,String outFilePath,String sheetName,boolean autoPagination,boolean autoSheet) throws Exception{
		String filename = templateFilePath;
		String separator = System.getProperty("file.separator");
		if (filename.indexOf(".xls") == -1) {
			filename += ".xls";
		}
		
		File file=new File(outFilePath);
		
		if (!file.getParentFile().exists()) {
			//创建临时目录
			file.getParentFile().mkdirs();
		}
		
		String templateFile = templateFilePath;
		String outputFile = outFilePath;
		ExcelTemplate template=new ExcelTemplate(templateFile,outputFile);
		
		template.setAutoPagination(autoPagination);
		template.setAutoSheet(autoSheet);
		template.setIncludeFormula(true);
		
		template.setMaxRow(65536);
		template.generate(exportObjects,exportInfo,sheetName);

		return outputFile;
	}
	
	
	/**
	 * 将指定的对象数组exportObjects导出到指定模板的Excel文件
	 *
	 * @param exportObjects 待导出的对象数组
	 * @param exportInfo  模板文件的其他附加信息(非结果集内容)
	 * @param templateFilename 模板文件名(不带扩展名),对应到在user-config.xml配置路径下的模板文件
	 * @return 返回生成的Excel文件下载路径
	 * @throws Exception
	 */
	public static String exportExcel(List<Map> exportObjects,Map exportInfo,String templateFilename) throws Exception{
		return exportExcel(exportObjects,exportInfo,templateFilename,false,false);
	}
	 
}
