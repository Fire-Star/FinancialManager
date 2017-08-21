<<<<<<< HEAD
# Learn-SpringSecurity
_学习SpringSecurity时，写的小案例。_

### 已达目标：完成了资源与权限的数据库持久化。

主要功能实现都是归功于该博客：http://blog.csdn.net/jaune161/article/details/17639305

数据库文件下载：https://github.com/Fire-Star/Learn-SpringSecurity/blob/master/SpringSecurity-firstDao/springsecurity.sql

你只需要写一个类，就是下面的类，再把该类配置一下(配置在SpringSecurity.xml里面)就能够实现上面的目标了。
该打注释的地方，我写了的，祝福你能够看懂，谢谢！！！


```

/**
 * Created by Administrator on 2017/8/9.
 */
public class URLFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource,InitializingBean{
    //权限集合
    private Map<String, Collection<ConfigAttribute>> requestMap;

    /**
     * 2、afterPropertiesSet方法，初始化bean的时候执行，
     * 可以针对某个具体的bean进行配置。afterPropertiesSet 必须实现 InitializingBean接口。
     * 实现 InitializingBean接口必须实现afterPropertiesSet方法。
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        requestMap = loadAllResource();
    }

    /**
     * 该方法用作 RequestMap 的数据刷新。
     */
    public void refreshRequestData(){
        try {
            afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private ResMapper resMapper;

    @Autowired
    private ResRoleMapper resRoleMapper;
    /**
     * 该方法目的是查询数据库里面的 String(代表URL),和该 URL 所需要的权限 Collection<ConfigAttribute>
     * @return
     */
    private Map<String,Collection<ConfigAttribute>> loadAllResource(){
        //声明一个缓存容器，缓存所有关于资源和权限的信息。
        Map<String , Collection<ConfigAttribute>> result = new HashMap<>();

        //初始化所有的资源对应的权限信息。
        List<Res> res = resMapper.findAllRes();
        for (Res resi : res) {
            //遍历URL
            String url = resi.getRes_url();

            //通过 URL 查询出所有关于该资源的权限信息。
            //这里 我把 url 封装了一下，把 url 放进了 Res 实体类里面。
            Res requestMessage = new Res("",url,"");
            List<Role> roles = resRoleMapper.findAllRolesByRes(requestMessage);

            //开始封装当前资源对应的权限信息。
            Collection<ConfigAttribute> attrs = new ArrayList<>();
            for (Role role : roles) {
                attrs.add(new SecurityConfig(role.getRname()));
            }
            result.put(url,attrs);
        }

        return result;
    }

    /**
     * 每次用户登录时，都会调用该方法。你可以查看方法发里面的输出！！！
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation)object).getRequestUrl();

        System.out.println(url);
        System.out.println(requestMap.get(url));

        return requestMap.get(url);
    }

    /**
     * getAllConfigAttributes方法如果返回了所有定义的权限资源，
     * Spring Security会在启动时校验每个ConfigAttribute是否配置正确，不需要校验直接返回null。
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Collection<ConfigAttribute> allConfigAttrs = new ArrayList<>();

        System.out.println("调用了 getAllConfigAttributes()方法！！！");

        Set<String> set = requestMap.keySet();

        for (String s : set) {
            allConfigAttrs.addAll(requestMap.get(s));
        }

        return allConfigAttrs;
    }

    /**
     * supports方法返回类对象是否支持校验，
     * web项目一般使用FilterInvocation来判断，或者直接返回true。
     * 在上面我们主要定义了两个权限码：
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}


```

还有配置文件：

```

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:security="http://www.springframework.org/schema/security"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/security
       http://www.springframework.org/schema/security/spring-security.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

<!-- 配置不过滤的资源（静态资源及登录相关） -->
    <security:http pattern="/**/*.css" security="none"></security:http>
    <security:http pattern="/**/*.jpg" security="none"></security:http>
    <security:http pattern="/**/*.jpeg" security="none"></security:http>
    <security:http pattern="/**/*.gif" security="none"></security:http>
    <security:http pattern="/**/*.png" security="none"></security:http>
    <security:http pattern="/**/*.js" security="none"></security:http>

    <security:http pattern="/login.jsp" security="none"></security:http>
    <security:http pattern="/index.jsp" security="none"></security:http>
    <security:http pattern="/getCode" security="none" /><!-- 不过滤验证码 -->
    <security:http pattern="/test/**" security="none"></security:http><!-- 不过滤测试内容 -->

    <!-- 配置资源权限信息 -->
    <security:http auto-config="true" use-expressions="false">

        <security:custom-filter ref="filterSecurityInterceptor" before="FILTER_SECURITY_INTERCEPTOR"/>

        <!-- 配置登出 -->
        <!-- 有时候，你会发现，就算重启了 Tomcat ，session 也不会过期，那么你需要配置退出时，session 过期。 -->
        <security:logout logout-url="/logoutSecurity" invalidate-session="true" delete-cookies="JSESSIONID"/>
        <!-- 在配置登出时，如果不把 csrf 设置为 true 的话，那么登出时的链接将会发生 404 错误。 -->
        <security:csrf disabled="true"/>
    </security:http>

    <!-- 配置用户角色信息 -->
    <security:authentication-manager alias="authenticationManagerw">
        <security:authentication-provider user-service-ref="customUserService">
        </security:authentication-provider>
    </security:authentication-manager>

    <bean id="MyaccessManager" class="org.springframework.security.access.vote.AffirmativeBased">
        <constructor-arg name="decisionVoters">
            <list>
                <ref bean="roleVoter"/>
                <ref bean="authVoter"/>
            </list>
        </constructor-arg>
    </bean>

    <bean id="roleVoter" class="org.springframework.security.access.vote.RoleVoter">
        <property name="rolePrefix" value="ROLE_"/>
    </bean>

    <bean id="authVoter" class="org.springframework.security.access.vote.AuthenticatedVoter"/>

    <bean id="securityMetadataSource" class="cn.ejie.springsecurity.model.URLFilterInvocationSecurityMetadataSource" />
    
    <!-- 数据库管理url -->
    <bean id="filterSecurityInterceptor" class="org.springframework.security.web.access.intercept.FilterSecurityInterceptor">
        <property name="accessDecisionManager" ref="MyaccessManager"></property>
        <property name="authenticationManager" ref="authenticationManagerw"></property>
        <property name="securityMetadataSource" ref="securityMetadataSource"></property>
    </bean>
</beans>

```


### 不同权限在页面显示不同内容

首先在SpringSecurity.xml中配置如下内容：

```
    <!-- 要让页面的不同权限显示不同内容，你需要写上这个 -->
    <bean id="webexpressionHandler" class="org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler" />
```

之后在页面写：

```
<%-- ifAnyGranted="ROLE_USER" 这种表示法已经被弃用了！！！ --%>
<security:authorize access="hasAnyRole('ROLE_USER,ROLE_ADMIN')">
    <a href="#">用户界面的秘密通道</a><br>
</security:authorize>
<security:authorize access="hasAnyRole('ROLE_ADMIN')">
    <a href="#">管理员界面的秘密通道</a><br>
</security:authorize>
```

好了，尝试运行吧，觉得不会有问题的！！！


### 相关属性介绍

配置登录相关
login-page :实际的登录界面。
login-processing-url:对应登录表单的 action
username-parameter:对应登录表单的 登录名 文本框的name属性值。
password-parameter:对应登录表单的 密码 文本框的name 属性值。
default-target-url：若在登录前，没有访问受保护的页面（即：直接访问的是登录页面），则登录成功后的响应页面。默认为index.jsp 或者 index.html
always-use-default-target:只要登录成功就响应该属性指定的页面。默认情况下，若在登录前，访问了受保护的页面，则登录成功后，响应受保护页面。
authentication-failure-url:登录失败的响应页面。
authentication-success-handler-ref:指向AuthenticationSuccessHandler Bean 。作用为登录成功后，SpringSecurity将调用AuthenticationSuccessHandler Bean 的 onAuthenticationSuccess方法，此时将忽略 default-target-url 和 always-use-default-target
authentication-failure-handler-ref:类似上面这个。
=======
# FinancialManager
资产管理系统
>>>>>>> 661295f0946d1dfd040063c21ccc8c46af65d3cd
