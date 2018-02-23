package com.wjl.service;

import com.wjl.mapper.UserMapper;
import com.wjl.model.User;
import com.wjl.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author wjl
 * @date 2018/2/8
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUser(String userName, String userPass){
        userPass = MD5Util.getMD5(userPass);
        return userMapper.findUser(userName, userPass);
    }

    public int insert(String userName, String userPass, Integer flag) {
        userPass = MD5Util.getMD5(userPass);
        return userMapper.insert(userName, userPass, flag);
    }
}
