package com.liu.shiro.basis.shiro;

import com.liu.shiro.basis.shiro.model.SysPermission;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @desc Realm：先认证，后授权。
 * @author Liuweian
 * @version 1.0.0
 * @createTime 2020/1/9 14:44
 */
public class CustomRealm extends AuthorizingRealm {
    private final static Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    @Override
    public void setName(String name) {
        super.setName("customizeName");
    }


    /**
     * 认证过程。前台传来的数据先进行认证。确定用户名和密码是否正确
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("开始执行定义认证过程。loginId为[{}]", token.getCredentials().toString());
        //前台form表单的value = userName的值
        String loginId = (String) token.getPrincipal();

        // 这个密码是数据库中村的，需要和token（用户在登录页面输入的）中的密码进行校验的。
        // 测试密码 - 密文：liu  盐：123456  密文：cf27bcf8c0b5db994d4a43df900cfe26    经过2次hash
        String password = "cf27bcf8c0b5db994d4a43df900cfe26";
        String salt = "123456";

        logger.info("认证参数loginId：[{}],password:[{}],salt:[{}]", loginId, password, salt);
        AuthenticationInfo info = new SimpleAuthenticationInfo(token.getPrincipal(), password, ByteSource.Util.bytes(salt), this.getName());
        return info;
    }

    // PartialOrder

    /**
     * 授权过程：
     * @Principal:身份信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("授权过程开始。loginId为：[{}]", principals.getPrimaryPrincipal());

        // 01、从数据库获取这个用户的权限信息。
        List<SysPermission> sysRoles = new ArrayList<>();

        // 02、获取这个用户的权限表示符。
        Set<String> perCodes = new HashSet<>();
        for (SysPermission item : sysRoles) {
            perCodes.add(item.getPercode());
        }

        perCodes.add("order:add:*");
        perCodes.add("user:get:*");
        perCodes.add("sys:shiro:*");

        // 03、构建权限信息。
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 04、添加授权信息
        info.addStringPermissions(perCodes);  // 添加许可
        info.addRole("system");     // 添加角色
        return info;
    }

    public static void main(String[] args) {
        System.out.println(new Md5Hash("liu", "123456",2).toString());
    }

}
