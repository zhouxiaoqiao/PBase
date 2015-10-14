package com.yunyuan.hr.service;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.moon.common.excel.ExcelReader;
import org.moon.common.util.ReflectUtil;
import org.moon.service.inf.ITreeService;
import org.snaker.framework.security.entity.Org;
import org.snaker.framework.security.entity.Role;
import org.snaker.framework.security.entity.User;
import org.snaker.framework.security.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.yunyuan.hr.dao.EmployeeDao;
import com.yunyuan.hr.entity.Employee;
import com.yunyuan.util.KeyUtil;

@Component
public class EmployeeService 
{
	//注入 对象
	@Autowired
	private EmployeeDao employeeDao;
	//注入用户管理对象
	@Autowired
	private UserManager userManager;
	@Resource
	private ITreeService treeService;
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
	 * @param json_user
	 * @author 周小桥 |2015-10-9 上午10:48:14
	 * @version 0.1
	 */
	public String batchAuthEmployeeLogin(String json_user)
	{
		String ret_auth = "";
		if (json_user != null)
		{
			ReflectUtil rf = new ReflectUtil();

			JSONArray emps = JSONArray.fromObject(json_user);
			for (int i = 0; i < emps.size(); i++)
			{
				JSONObject tmp = emps.getJSONObject(i);
				//检查用户是否已经存在
				User find_u = userManager.findUserByName(tmp.getString("username"));
				if (find_u != null)
				{
					ret_auth = tmp.getString("username") + "已经存在,赋权fail." + ret_auth;
					continue ;
				}

				//插入赋权可以登录系统
				User user = new User();
				Org org = new Org(Long.parseLong(tmp.getString("org")));
				tmp.remove("org");
				long roleID = Long.parseLong(tmp.getString("roleID"));
				tmp.remove("roleID");
				rf.setObjectVal(user, tmp);
				//设置部门
				user.setOrg(org);
				//设置普通角色
				Role usual = new Role();
				usual.setId(roleID);//
				user.getRoles().add(usual);

				userManager.save(user);
				//赋予基本的菜单权限
				String checkedBoxIDs = "10,100,1001,15,156";
				if (treeService.updateMenuRight(user.getUsername(), checkedBoxIDs, null))
					logger.info(user.getUsername() + " 菜单树赋权成功! ");
				else
					logger.info(user.getUsername() + " 菜单树赋权失败! ");

			}
		}
		return ret_auth;
	}
}
