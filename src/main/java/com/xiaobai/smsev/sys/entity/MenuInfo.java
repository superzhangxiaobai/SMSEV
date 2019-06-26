package com.xiaobai.smsev.sys.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_menu")
public class MenuInfo {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "menuname")
    private String menuname;
    @Column(name = "url")
    private String url;
    @Column(name = "icon")
    private String icon;
    @Column(name = "pid")
    private Long pid;
    @Column(name = "isEnable")
    private boolean isEnable;
    @Column(name = "status")
    private Integer status;
    @Column(name = "type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // 创建时间
    @Column(name = "createtime",updatable = false)
    private Date createtime;
    // 创建人
    @Column(name = "creator",updatable = false)
    private String creator;
    // 修改人
    @Column(name = "updator")
    private String updator;
    // 修改时间
    @Column(name = "updatetime")
    private Date updatetime;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean enable) {
        isEnable = enable;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
