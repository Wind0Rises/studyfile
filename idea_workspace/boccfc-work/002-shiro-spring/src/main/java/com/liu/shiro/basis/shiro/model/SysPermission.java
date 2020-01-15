package com.liu.shiro.basis.shiro.model;

/**
 * @desc 系统实体类 
 * @author Liuweian
 * @createTime 2018-03-27 11:41:42
 * @version 1.0.0
 */
public class SysPermission{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /** 权限名 */
    private String per_name;

    /** 类型 */
    private String type;

    /** url */
    private String url;

    /** 码 */
    private String percode;

    /** 父权限 */
    private Long parentid;

    /**  */
    private String parentids;

    /**  */
    private String sortstring;

    /** 状态 */
    private String status;

    public String getPer_name() {
        return per_name;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name == null ? null : per_name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPercode() {
        return percode;
    }

    public void setPercode(String percode) {
        this.percode = percode == null ? null : percode.trim();
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public String getParentids() {
        return parentids;
    }

    public void setParentids(String parentids) {
        this.parentids = parentids == null ? null : parentids.trim();
    }

    public String getSortstring() {
        return sortstring;
    }

    public void setSortstring(String sortstring) {
        this.sortstring = sortstring == null ? null : sortstring.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}