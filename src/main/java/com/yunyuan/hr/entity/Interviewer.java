package com.yunyuan.hr.entity;

import java.io.Serializable;
import java.sql.Date;
 
public class Interviewer implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3586012785845509705L;
	private String eid;
	private String china_name;
	private String age;
	private String college;
	private String work_history;
	private String address;
	private String interest;
	private Date graduate_time;
	private String professional;
	private String tel;
	private Date born_date;
	private String adver_way;
	private String interview_person;
	private Date interview_date;
	private int hope_salary;
	private int fact_salary;
	private String employ_status;
	private String qual_cert;
	private String sex;
	private Date come_date;
	private String interview_evaluate;

	public String getEid()
	{
		return eid;
	}

	public void setEid(String eid)
	{
		this.eid = eid;
	}

	public String getChina_name()
	{
		return china_name;
	}

	public void setChina_name(String china_name)
	{
		this.china_name = china_name;
	}

	public String getAge()
	{
		return age;
	}

	public void setAge(String age)
	{
		this.age = age;
	}

	public String getCollege()
	{
		return college;
	}

	public void setCollege(String college)
	{
		this.college = college;
	}

	public String getWork_history()
	{
		return work_history;
	}

	public void setWork_history(String work_history)
	{
		this.work_history = work_history;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getInterest()
	{
		return interest;
	}

	public void setInterest(String interest)
	{
		this.interest = interest;
	}

	public Date getGraduate_time()
	{
		return graduate_time;
	}

	public void setGraduate_time(Date graduate_time)
	{
		this.graduate_time = graduate_time;
	}

	public String getProfessional()
	{
		return professional;
	}

	public void setProfessional(String professional)
	{
		this.professional = professional;
	}

	public String getTel()
	{
		return tel;
	}

	public void setTel(String tel)
	{
		this.tel = tel;
	}

	public Date getBorn_date()
	{
		return born_date;
	}

	public void setBorn_date(Date born_date)
	{
		this.born_date = born_date;
	}

	public String getAdver_way()
	{
		return adver_way;
	}

	public void setAdver_way(String adver_way)
	{
		this.adver_way = adver_way;
	}

	public String getInterview_person()
	{
		return interview_person;
	}

	public void setInterview_person(String interview_person)
	{
		this.interview_person = interview_person;
	}

	public Date getInterview_date()
	{
		return interview_date;
	}

	public void setInterview_date(Date interview_date)
	{
		this.interview_date = interview_date;
	}

	public int getHope_salary()
	{
		return hope_salary;
	}

	public void setHope_salary(int hope_salary)
	{
		this.hope_salary = hope_salary;
	}

	public int getFact_salary()
	{
		return fact_salary;
	}

	public void setFact_salary(int fact_salary)
	{
		this.fact_salary = fact_salary;
	}

	public String getEmploy_status()
	{
		return employ_status;
	}

	public void setEmploy_status(String employ_status)
	{
		this.employ_status = employ_status;
	}

	public String getQual_cert()
	{
		return qual_cert;
	}

	public void setQual_cert(String qual_cert)
	{
		this.qual_cert = qual_cert;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public Date getCome_date()
	{
		return come_date;
	}

	public void setCome_date(Date come_date)
	{
		this.come_date = come_date;
	}

	public String getInterview_evaluate()
	{
		return interview_evaluate;
	}

	public void setInterview_evaluate(String interview_evaluate)
	{
		this.interview_evaluate = interview_evaluate;
	}

}
