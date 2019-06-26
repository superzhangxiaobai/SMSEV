package com.xiaobai.smsev.sys.dao;

import com.xiaobai.smsev.sys.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserinfoDao extends JpaRepository<UserInfo,Integer> {
    UserInfo findByLoginname(String loginname);
}
