package com.xiaobai.smsev.sys.dao;

import com.xiaobai.smsev.sys.entity.UserInfo;
//import org.apache.ibatis.annotations.Mapper;

/*
 * Mybatis数据映射，数据库sql见: resource/mybatis/db.sql
 */
//@Mapper
public interface DataMapper {
	UserInfo getUserLoginInfo(String username);
}
