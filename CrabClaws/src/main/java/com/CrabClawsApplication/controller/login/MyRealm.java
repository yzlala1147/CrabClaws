package com.CrabClawsApplication.controller.login;

import com.CrabClawsApplication.service.UserService;
import com.CrabClawsApplication.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    //用于鉴权

    /**
     * 当前登录用户，我们可以从数据库中查询到该用户的权限，并通过该方法，将我们查询到的
     * 该用户拥有的角色，保存在shiro框架中，后面我们使用shiro框架去核对用户权限时，所比较的
     * 就是我们在这里查询、并且保存到的权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 这个方法是用于登录认证
     * 我们要自定义自己的登录逻辑，就需要在该方法中编写自己的登录逻辑
     *
     * @param
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //首先，从token中获取登录时保存的用户信息，注意，该方法是登录时shiro会调用的方法
        //也就是说我们在登录时，会将名称和密码封装成token，所以这里不要疑惑为什么token中可以
        //获取到用户的信息，后续我们编写登录时，你就会恍然大悟
        String name = token.getPrincipal().toString();//得到的就是用户名称
        System.out.println("name = " + name);//可以打印出来看看
        //从数据库中查询用户信息，根据name查询
        User user = userService.getUserByName(name);
        //查到用户信息之后，将登录时的密码，和现在从数据库中查到的密码进行比较
        //我们将相关的数据封装到AuthenticationInfo中，知道密码的盐，用户名、密码，
        //该对象会帮我们进行校验，并且将校验结果返回给shiro框架
        if (user != null) {
            //创建AuthenticationInfo对象,在构造器中传递相关参数
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    token.getPrincipal(),
                    user.getPassword(),//用户密码
                    token.getPrincipal().toString()
            );
            return info;
        }
        return null;
    }
}

