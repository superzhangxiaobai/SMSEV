package com.xiaobai.smsev.sys.entity;

import javax.persistence.*;

@Entity
@Table(name="t_role")
public class RoleInfo {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "rolename")
    private String rolename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
