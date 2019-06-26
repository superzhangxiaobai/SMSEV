package com.xiaobai.smsev.sys.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_user_role")
public class UserRole {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "roleid")
    private Long roleid;
    @Column(name = "userid")
    private Long userid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
