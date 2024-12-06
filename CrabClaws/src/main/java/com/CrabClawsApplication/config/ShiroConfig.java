package com.CrabClawsApplication.config;

import com.CrabClawsApplication.controller.login.MyRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 1. 配置安全管理器
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();

        byte[] newCipherKey = Base64.getDecoder().decode("823Cvv7bHVZlebvEnlZWfQ==");
        rememberMeManager.setCipherKey(newCipherKey);
        securityManager.setRememberMeManager(rememberMeManager);
        // 这里可以配置自定义的Realm，用于从数据库等数据源获取用户信息进行认证和授权，后续会详细介绍如何创建Realm
        securityManager.setRealm(MyRealm());
        return securityManager;
    }

    // 自定义Realm，用于实际的认证和授权操作，从数据库等获取用户信息
    @Bean
    public MyRealm MyRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

    // 2. 配置Shiro过滤器链，定义不同URL路径的访问权限规则

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 定义过滤规则，采用LinkedHashMap保证顺序
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置匿名访问的路径，例如登录页面等，可根据实际项目调整路径
        filterChainDefinitionMap.put("/**", "anon");
        // 配置需要认证才能访问的路径，这里用 /** 表示除了前面配置匿名访问路径外的所有路径
        //filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    // 3. 配置生命周期处理器，用于Shiro相关Bean的生命周期管理
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    // 4. 配置自动代理创建器，用于支持Shiro的AOP功能，如权限注解等
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    // 5. 配置授权属性源顾问，使Shiro的权限注解生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
