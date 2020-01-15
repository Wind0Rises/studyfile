package com.liu.shiro.basis.shiro.model;

/**
 * @desc 角色实体类 
 * @author Liuweian
 * @createTime 2018-03-27 11:43:36
 * @version 1.0.0
 */
public class SysRole{

	private static final long serialVersionUID = 1L;

	/** 角色名称 */
    private String role_Name;

    /** 状态(0：未启用，1：启用) */
    private String status;

    /** 描述 */
    private String description;

    public String getRole_Name() {
        return role_Name;
    }

    public void setRole_Name(String role_Name) {
        this.role_Name = role_Name == null ? null : role_Name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}