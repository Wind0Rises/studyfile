package com.liu.shiro.basis.shiro.model;

/**
 * @desc 用户角色实体类 
 * @author Liuweian
 * @createTime 2018-03-27 11:44:06
 * @version 1.0.0
 */
public class SysUserRole{

	private static final long serialVersionUID = 1L;

	/** userid */
    private String sys_user_id;

    /** roleId */
    private String sys_role_id;

    public String getSys_user_id() {
        return sys_user_id;
    }

    public void setSys_user_id(String sys_user_id) {
        this.sys_user_id = sys_user_id == null ? null : sys_user_id.trim();
    }

    public String getSys_role_id() {
        return sys_role_id;
    }

    public void setSys_role_id(String sys_role_id) {
        this.sys_role_id = sys_role_id == null ? null : sys_role_id.trim();
    }
}