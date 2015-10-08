package com.yunyuan.hr.entity;

import java.io.Serializable;
import java.sql.Date;

 

public class Employee implements Serializable
{
	/**
	 * 
	 */

	private static final long serialVersionUID = -44446578074524633L;
	private String eid;
	private String staff_name;
	private String job_name;
	private String age;
	private String status;
	private Date join_time;
	private String college;
	private String graduate_time;
	private String salary_month;
	private long dept_id;
	private String full_name;
	private String user_account;
	private String work_history;
	private String born_date;
	private String professional;
	private String work_year;
	private String address;

	public String getEid()
	{
		return eid;
	}

	public void setEid(String eid)
	{
		this.eid = eid;
	}

	public String getStaff_name()
	{
		return staff_name;
	}

	public void setStaff_name(String staff_name)
	{
		this.staff_name = staff_name;
	}

	public String getJob_name()
	{
		return job_name;
	}

	public void setJob_name(String job_name)
	{
		this.job_name = job_name;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public Date getJoin_time()
	{
		return join_time;
	}

	public void setJoin_time(Date join_time)
	{
		this.join_time = join_time;
	}

	public String getCollege()
	{
		return college;
	}

	public void setCollege(String college)
	{
		this.college = college;
	}

	public String getGraduate_time()
	{
		return graduate_time;
	}

	public void setGraduate_time(String graduate_time)
	{
		this.graduate_time = graduate_time;
	}

	public String getSalary_month()
	{
		return salary_month;
	}

	public void setSalary_month(String salary_month)
	{
		this.salary_month = salary_month;
	}

	public long getDept_id()
	{
		return dept_id;
	}

	public void setDept_id(long dept_id)
	{
		this.dept_id = dept_id;
	}

	public String getFull_name()
	{
		return full_name;
	}

	public void setFull_name(String full_name)
	{
		this.full_name = full_name;
	}

	public String getUser_account()
	{
		return user_account;
	}

	public void setUser_account(String user_account)
	{
		this.user_account = user_account;
	}

	public String getWork_history()
	{
		return work_history;
	}

	public void setWork_history(String work_history)
	{
		this.work_history = work_history;
	}

	public String getBorn_date()
	{
		return born_date;
	}

	public void setBorn_date(String born_date)
	{
		this.born_date = born_date;
	}

	public String getProfessional()
	{
		return professional;
	}

	public void setProfessional(String professional)
	{
		this.professional = professional;
	}

	public String getWork_year()
	{
		return work_year;
	}

	public void setWork_year(String work_year)
	{
		this.work_year = work_year;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

}
