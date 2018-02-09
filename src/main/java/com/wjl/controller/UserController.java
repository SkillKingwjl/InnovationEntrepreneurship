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

    @RequestMapping(value = "findUser/{userName}/{userPass}",method = RequestMethod.POST)
    @ResponseBody
    public Object findUser(@PathVariable String userName, @PathVariable String userPass){
        return userService.findUser(userName,userPass);
    }

    @RequestMapping(value = "insert/userName/{userName}/userPass/{userPass}/flag/{flag}",method = RequestMethod.PUT)
    public Object insert(@PathVariable String userName, @PathVariable String userPass,@PathVariable Integer flag){
        return userService.insert(userName,userPass,flag);
    }
    @RequestMapping(value = "index")
    public String insert(){
        return "login";
    }
}
