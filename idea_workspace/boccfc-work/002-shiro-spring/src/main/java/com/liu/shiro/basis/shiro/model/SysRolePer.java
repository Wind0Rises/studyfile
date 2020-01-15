package com.liu.shiro.basis.shiro.model;

/**
 * @desc 角色权限实体类 
 * @author Liuweian
 * @createTime 2018-03-27 11:44:30
 * @version 1.0.0
 */
public class SysRolePer{

	private static final long serialVersionUID = 1L;

	/** 权限主键 */
    private Long sys_per_id;

    /** 角色主键 */
    private String sys_role_id;

    public Long getSys_per_id() {
        return sys_per_id;
    }

    public void setSys_per_id(Long sys_per_id) {
        this.sys_per_id = sys_per_id;
    }

    public String getSys_role_id() {
        return sys_role_id;
    }

    public void setSys_role_id(String sys_role_id) {
        this.sys_role_id = sys_role_id == null ? null : sys_role_id.trim();
    }
}