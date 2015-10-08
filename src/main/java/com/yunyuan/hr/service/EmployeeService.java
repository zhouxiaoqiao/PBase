package com.yunyuan.hr.service;

import java.io.File;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.moon.common.excel.ExcelReader;
import org.moon.common.util.ReflectUtil;
import org.snaker.engine.core.AccessService;
import org.snaker.framework.security.entity.Org;
import org.snaker.framework.security.entity.User;
import org.snaker.framework.security.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yunyuan.hr.dao.EmployeeDao;
import com.yunyuan.hr.entity.Employee;
import com.yunyuan.util.KeyUtil;

@Component
public class EmployeeService extends AccessService
{
	//注入 对象
	@Autowired
	private EmployeeDao employeeDao;
	//注入用户管理对象
	@Autowired
	private UserManager userManager;
	
	ReflectUtil reflectUtil = new ReflectUtil();
	ExcelReader excelReader = new ExcelReader();
	public Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param employee
	 * @author 周小桥 |2015-9-29 上午10:21:41
	 * @version 0.1
	 */
	public void saveEmployee(Employee employee)
	{

		employeeDao.save(employee);

	}

	/**
	 * 
	 * @param file
	 * @author 周小桥 |2015-9-30 上午10:38:16
	 * @version 0.1
	 */
	public void importEmployeeFile(File file)
	{

		String[] colName = { "staff_name", "job_name", "age", "status", "dept_id", "full_name", "join_time", "college",
				"graduate_time", "salary_month", "user_account", "work_history", "born_date", "professional",
				"work_year", "address" };
		logger.info("开始导入!");
		List<JSONObject> excel_content = excelReader.readExcelContent(file, colName);
		for (int i = 0; i < excel_content.size(); i++)
		{
			String eid = "" + KeyUtil.getLongID();
			Employee employee = new Employee();
			reflectUtil.setObjectVal(employee, excel_content.get(i));
			employee.setEid(eid);
			logger.info("Dept_id==" + employee.getDept_id());
			employeeDao.save(employee);
		}
		logger.info("导入结束!");
	}
	/**
	 * 
	 * @param user
	 * @author 周小桥 |2015-10-6 下午5:12:30
	 * @version 0.1
	 */
	public void authEmployeeLogin(User user){
	
		userManager.save(user);
	}
	
	public void batchAuthEmployeeLogin(){
		
		
	}
}
