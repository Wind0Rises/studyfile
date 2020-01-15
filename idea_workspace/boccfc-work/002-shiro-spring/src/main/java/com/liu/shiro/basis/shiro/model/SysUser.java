package com.liu.shiro.basis.shiro.model;

import java.util.Date;

/**
 * @desc 用户实体类 
 * @author Liuweian
 * @createTime 2018-03-27 11:43:15
 * @version 1.0.0
 */
public class SysUser{

	private static final long serialVersionUID = 1L;

	/** 登录ID */
    private String loginId;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 状态（0：未启用，1启用） */
    private String status;

    /** 邮件 */
    private String email;

    /** 电话 */
    private String phone;

    /** 最后创建时间 */
    private Date last_login_time;

    /** 盐 */
    private String salt;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId == null ? null : loginId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }
}