package com.nuc.securedatabasesystem.service;

import com.nuc.securedatabasesystem.mapper.UserMapper;
import com.nuc.securedatabasesystem.pojo.User;
import com.nuc.securedatabasesystem.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author DeepBlue
 * @Date 2020/11/12 16:18
 */
@Service
public class UserServerImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServerImpl.class);
    @Autowired
    private UserMapper userMapper;

    /**
     * Login 方法的实现，返回username=传进来的name and password=传进来的password的人，
     * 如果找到，证明有这个用户，否则没这个用户
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public User login(User user) {
        log.info(user.getUsername());
        log.info(user.getPassword());
        User login = userMapper.login(user);
        if (login != null) {
            return login;
        }
        throw new RuntimeException("登陆失败");
    }


    @Override
    public void register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        System.out.println("--------------------");
        System.out.println(user);
        userMapper.insert(user);
    }
}
