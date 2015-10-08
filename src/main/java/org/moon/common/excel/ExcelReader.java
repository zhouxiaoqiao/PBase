package org.moon.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelReader
{

	/**
	 * @param args
	 * @author 周小桥 |2015-9-28 上午9:17:59
	 * @version 0.1
	 */
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;

	/**
	 * 读取Excel表格表头的内容
	 * @param InputStream
	 * @return String 表头内容的数组
	 */
	@SuppressWarnings("deprecation")
	public String[] readExcelTitle(InputStream is)
	{
		try
		{
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("colNum:" + colNum);
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++)
		{
			//title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = getCellFormatValue(row.getCell((short) i));
		}
		return title;
	}

	/**
	   * 读取Excel数据内容
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 * @param is
	 * @param colName
	 * @return
	 * @author 周小桥 |2015-9-29 下午5:20:36
	 * @version 0.1
	 */
	@SuppressWarnings("deprecation")
	public List<JSONObject> readExcelContent(File file_, String colName[])
	{
		List<JSONObject> content = new ArrayList<JSONObject>();

		try
		{
			InputStream in = new FileInputStream(file_);
			fs = new POIFSFileSystem(in);
			wb = new HSSFWorkbook(fs);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++)
		{
			row = sheet.getRow(i);
			int j = 0;
			JSONObject row_json = new JSONObject();
			while (j < colName.length&&j<colNum)
			{
				// 每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据
				// 也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
				// str += getStringCellValue(row.getCell((short) j)).trim() +
				// "-";

				row_json.put(colName[j], getCellFormatValue(row.getCell((short) j)).trim());

				j++;
			}
			content.add(row_json);

		}
		return content;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell Excel单元格
	 * @return String 单元格数据内容
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private String getStringCellValue(HSSFCell cell)
	{
		String strCell = "";
		switch (cell.getCellType())
		{
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null)
		{
			return "";
		}
		if (cell == null)
		{
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	@SuppressWarnings({ "unused", "deprecation" })
	private String getDateCellValue(HSSFCell cell)
	{
		String result = "";
		try
		{
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC)
			{
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
			}
			else if (cellType == HSSFCell.CELL_TYPE_STRING)
			{
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			}
			else if (cellType == HSSFCell.CELL_TYPE_BLANK)
			{
				result = "";
			}
		}
		catch (Exception e)
		{
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	//	    public List<Student> readXlsx(String path) throws IOException {
	//	        System.out.println(Common.PROCESSING + path);
	//	        InputStream is = new FileInputStream(path);
	//	        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
	//	        Student student = null;
	//	        List<Student> list = new ArrayList<Student>();
	//	        // Read the Sheet
	//	        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
	//	            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
	//	            if (xssfSheet == null) {
	//	                continue;
	//	            }
	//	            // Read the Row
	//	            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
	//	                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
	//	                if (xssfRow != null) {
	//	                    student = new Student();
	//	                    XSSFCell no = xssfRow.getCell(0);
	//	                    XSSFCell name = xssfRow.getCell(1);
	//	                    XSSFCell age = xssfRow.getCell(2);
	//	                    XSSFCell score = xssfRow.getCell(3);
	//	                    student.setNo(getValue(no));
	//	                    student.setName(getValue(name));
	//	                    student.setAge(getValue(age));
	//	                    student.setScore(Float.valueOf(getValue(score)));
	//	                    list.add(student);
	//	                }
	//	            }
	//	        }
	//	        return list;
	//	    }
	/**
	 * 根据HSSFCell类型设置数据
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell)
	{
		String cellvalue = "";
		if (cell != null)
		{
			// 判断当前Cell的Type
			switch (cell.getCellType())
			{
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA:
			{
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell))
				{
					// 如果是Date类型则，转化为Data格式

					//方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					//cellvalue = cell.getDateCellValue().toLocaleString();

					//方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else
				{
					// 取得当前Cell的数值
					 DecimalFormat df = new DecimalFormat("0");  
					 cellvalue = df.format(cell.getNumericCellValue());
					  //cellvalue = String.valueOf(cell.getNumericCellValue());
					//仍然按字符读取
					//cellvalue = cell.getRichStringCellValue().getString();
				}
				break;
			}
			// 如果当前Cell的Type为STRING
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		}
		else
		{
			cellvalue = "";
		}
		return cellvalue;

	}

	public static void main(String[] args)
	{
		try
		{
			ExcelReader excelReader = new ExcelReader();
			// 对读取Excel表格标题测试
			//	            InputStream is = new FileInputStream("E:/logs/test.xls");

			//	            String[] title = excelReader.readExcelTitle(is);
			//	            System.out.println("获得Excel表格的标题:");
			//	            for (String s : title) {
			//	                System.out.print(s + " ");
			//	            }
			String colName[] = { "staff_name", "job_name","age" ,"status","dept_id","full_name"};
			// 对读取Excel表格内容测试
 
			File f=new File("E:/logs/employee_file.xls");
			List<JSONObject> content = excelReader.readExcelContent(f, colName);
			System.out.println("获得Excel表格的内容:");
			for (int i = 0; i < content.size(); i++)
			{
				System.out.println(content.get(i));
			}

		}
		catch (Exception e)
		{
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}
	}
}
