package cn.ejie.springsecurity.model;

import cn.ejie.dao.UserMapper;
import cn.ejie.dao.UserRoleMapper;
import cn.ejie.po.Role;
import cn.ejie.pocustom.UserCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */
@Component("customUserService")
public class CustomUserService implements UserDetailsService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserCustom requestMessage = new UserCustom(username,"","");

        cn.ejie.po.User resultUser = null;
        try {
            resultUser = userMapper.findUserByUsername(requestMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultUser == null) {
            return null;
        }
        String password = resultUser.getPassword();
        String city = resultUser.getCity();
        boolean enabled = true;
        boolean accountNonLoked = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExipred = true;

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        List<Role> roles = userRoleMapper.findRolesByUsername(requestMessage);

        if (roles == null) {
            return null;
        }
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRname()));
        }

        User user = new User(username,password,enabled,accountNonExpired,credentialsNonExipred,accountNonLoked,authorities);
        return user;
    }
}
