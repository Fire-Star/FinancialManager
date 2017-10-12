package cn.ejie.springsecurity.model;

import cn.ejie.dao.ResMapper;
import cn.ejie.dao.ResRoleMapper;
import cn.ejie.po.Res;
import cn.ejie.po.Role;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.*;

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
    private Map<String,Collection<ConfigAttribute>> loadAllResource() throws Exception {
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
        String tempUrl = ((FilterInvocation)object).getRequestUrl();
        int endIndex = tempUrl.indexOf("?");
        String url = null;
        if(endIndex != -1){
            url = tempUrl.substring(0,endIndex);
        }
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
