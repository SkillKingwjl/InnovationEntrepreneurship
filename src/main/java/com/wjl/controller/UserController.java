package com.wjl.controller;

import com.wjl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author wjl
 * @date 2018/2/8
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Object findUser(String userName,String userPass){
        return userService.findUser(userName,userPass);
    }

    @RequestMapping(value = "register",method = RequestMethod.PUT)
    @ResponseBody
    public Object insert(String userName,  String userPass, Integer flag){
        return userService.insert(userName,userPass,flag);
    }
    @RequestMapping(value = "index")
    public String skinToLogin(){
        return "login";
    }
    @RequestMapping(value = "register")
    public String skinToRegister(){
        return "register";
    }
}
