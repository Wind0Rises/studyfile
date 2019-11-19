package com.liu.boot.zookeeper.test.managerconfig;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据库配置实体，用户保存数据库配置。
 */
@Data
public class DataConfigModel implements Serializable {
    public DataConfigModel() {
    }

    public DataConfigModel(String url, String port, String username, String password) {
        this.url = url;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    private String url;

    private String port;

    private String username;

    private String password;

    @Override
    public String toString() {
        return "url: " + url + "port: " + port + "pasword: " + password + "username: " + password;
    }
}
