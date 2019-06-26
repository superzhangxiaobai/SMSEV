package com.xiaobai.smsev.sys.dao;

import com.xiaobai.smsev.sys.entity.RoleInfo;
//import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
//    @Select("select * from t_role where userid=#{userid}")
    List<RoleInfo> getRolesByUserid(Long userid);
}
