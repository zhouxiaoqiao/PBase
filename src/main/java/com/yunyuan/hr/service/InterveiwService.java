package com.yunyuan.hr.service;

import java.io.File;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.moon.common.excel.ExcelReader;
import org.moon.common.util.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yunyuan.hr.dao.InterviewerDao;
import com.yunyuan.hr.entity.Interviewer;
import com.yunyuan.util.KeyUtil;

@Component
public class InterveiwService  
{
	//注入 对象
	@Autowired
	private InterviewerDao interviewerDao;
	ReflectUtil reflectUtil = new ReflectUtil();
	ExcelReader excelReader = new ExcelReader();
	public Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param employee
	 * @author 周小桥 |2015-9-29 上午10:21:41
	 * @version 0.1
	 */

	/**
	 * 
	 * @param file
	 * @author 周小桥 |2015-9-30 上午10:38:16
	 * @version 0.1
	 */
	public void importEmployeeFile(File file)
	{

		String[] colName = { "china_name", "age", "college", "work_history", "address", "interest", "graduate_time",
				"professional", "tel", "born_date", "adver_way", "interview_person", "interview_date", "hope_salary",
				"fact_salary", "employ_status", "qual_cert", "sex", "come_date", "interview_evaluate"

		};
		logger.info("开始导入!");
		List<JSONObject> excel_content = excelReader.readExcelContent(file, colName);
		for (int i = 0; i < excel_content.size(); i++)
		{
			String eid = "" + KeyUtil.getLongID();
			Interviewer interviewer = new Interviewer();
			reflectUtil.setObjectVal(interviewer, excel_content.get(i));
			interviewer.setEid(eid);
			//logger.info("Dept_id==" + employee.getDept_id());
			interviewerDao.save(interviewer);
		}
		logger.info("导入结束!");
	}
}
