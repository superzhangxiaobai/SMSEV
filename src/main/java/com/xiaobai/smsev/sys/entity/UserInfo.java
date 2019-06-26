package com.xiaobai.smsev.sys.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//@Data
@Entity
@Table(name = "t_userinfo")
public class UserInfo implements Serializable {
	// 记录id
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	// 用户登录名
	@Column(name = "loginname")
	private String loginname;
	// 用户昵称
	@Column(name = "username")
	private String username;
	// 登录密码
	@Column(name = "password")
	private String password;
	// 加密盐
	@Column(name = "salt")
	private String salt;
	// 用户电话
	@Column(name = "tel")
	private String tel;
	// 身份证号
	@Column(name = "cardid")
	private String cardid;
	// 邮箱地址
	@Column(name = "email")
	private String email;
	// 家庭地址
	@Column(name = "address")
	private String address;
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
	// 备注
	@Column(name = "memo")
	private String memo;
	// 状态
	@Column(name = "status")
	private Integer status;
	// 是否激活
	@Column(name = "isactive")
	private boolean isactive;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
}
