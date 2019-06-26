package com.xiaobai.smsev.sys.service;

import com.xiaobai.smsev.sys.dao.UserinfoDao;
import com.xiaobai.smsev.sys.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserinfoService {
    @Autowired
    private UserinfoDao userinfoDao;

    public UserInfo loadUserByUsername(String username){
        System.out.println("账号 "+username+" 登陆中");
        userinfoDao.findAll();
        UserInfo user = userinfoDao.findByLoginname(username);
        return user;
    }

    public List<String> getPerms(Integer userid) {
//        return  userinfoMapper.getPerms(userid);
        List<String> list=new ArrayList<>();
        list.add("1");
        return list;
    }

    public UserInfo findUser(UserInfo realUser) {
//        return userinfoMapper.findUser(realUser);
        return new UserInfo();
    }
}
