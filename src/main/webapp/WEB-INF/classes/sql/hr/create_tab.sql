 
CREATE TABLE `tab_report` (
  `report_id` varchar(20) NOT NULL,
  `report_name` varchar(100) DEFAULT NULL,
  `templet_path` varchar(100) DEFAULT NULL,
  `excute_sql` varchar(1000) DEFAULT NULL,
  `where_sql` varchar(100) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `report_type` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tab_user_menu` (
  `user_id` varchar(20) NOT NULL,
  `menu_id` int(20) NOT NULL,
  PRIMARY KEY (`user_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tab_select` (
  `sel_name` varchar(20) NOT NULL,
  `opt_name` varchar(20) NOT NULL,
  `opt_val` varchar(20) DEFAULT NULL,
  `indx` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tab_employee` (
  `eid` bigint(20) DEFAULT NULL,
  `staff_name` varchar(50) DEFAULT NULL,
  `job_name` varchar(50) DEFAULT NULL,
  `age` varchar(50) DEFAULT NULL,
  `status` varchar(5) DEFAULT NULL,
  `join_time` date DEFAULT NULL,
  `college` varchar(20) DEFAULT NULL,
  `graduate_time` varchar(50) DEFAULT NULL,
  `salary_month` int(10) DEFAULT NULL,
  `dept_id` bigint(20) DEFAULT NULL,
  `full_name` varchar(50) DEFAULT NULL,
  `user_acoount` varchar(50) DEFAULT NULL,
  `work_history` varchar(255) DEFAULT NULL,
  `born_date` date DEFAULT NULL,
  `professional` varchar(50) DEFAULT NULL,
  `work_year` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 
CREATE TABLE `tab_advertise` (
  `eid` bigint(20) NOT NULL,
  `china_name` varchar(50) NOT NULL,
  `age` bigint(3) DEFAULT NULL,
  `college` varchar(50) DEFAULT NULL,
  `work_history` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `interest` varchar(50) DEFAULT NULL,
  `graduate_time` date DEFAULT NULL,
  `professional` varchar(50) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `born_date` date DEFAULT NULL,
  `adver_way` varchar(50) DEFAULT NULL,
  `interview_person` varchar(50) DEFAULT NULL,
  `interview_date` date DEFAULT NULL,
  `hope_salary` bigint(4) DEFAULT NULL,
  `fact_salary` bigint(4) DEFAULT NULL,
  `employ_status` varchar(50) DEFAULT NULL,
  `qual_cert` varchar(255) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `come_date` date DEFAULT NULL,
  `interview_evaluate` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`eid`,`china_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 /**
  * 职位表
  * 
  */
CREATE TABLE `tab_jon` (
  `jid` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `join_time` date DEFAULT NULL,
  `college` varchar(255) DEFAULT NULL,
  `graduate_time` varchar(255) DEFAULT NULL,
  `salary_month` int(10) DEFAULT NULL,
  `dept_id` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 /*
  * 函数
  * 查找所有子部门ID 
  */
CREATE FUNCTION `getChildLst`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN
    DECLARE sTemp VARCHAR(1000);
    DECLARE sTempChd VARCHAR(1000);  
     SET sTemp = '$';
     SET sTempChd =cast(rootId as CHAR);
  
     WHILE sTempChd is not null DO
       SET sTemp = concat(sTemp,',',sTempChd);
      SELECT group_concat(id) INTO sTempChd FROM sec_org where FIND_IN_SET(parent_org,sTempChd)>0;
      END WHILE;
      RETURN sTemp;
   END