package com.wjl.controller;

import com.alibaba.druid.util.StringUtils;
import com.wjl.config.UploadSrc;
import com.wjl.model.ProjectInfo;
import com.wjl.model.User;
import com.wjl.model.UserDetail;
import com.wjl.service.UserService;
import com.wjl.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wjl
 * @date 2018/2/8
 */
@Controller
public class UserController {
    @Autowired
    private UploadSrc uploadSrc;
    @Autowired
    private UserService userService;
    @Autowired
    private  HttpSession session;
    @RequestMapping(value = "index")
    public String skinToLogin(){
        return "login";
    }
    @RequestMapping(value = "loginout")
    public String skinToLoginOut(){
        session.removeAttribute("user");
        session.removeAttribute("userDetail");
        return "login";
    }
    @RequestMapping(value = "register")
    public String skinToRegister(){
        return "register";
    }
    @RequestMapping(value = "meproject")
    public String skinToMeproject(){
        return "meproject";
    }
    @RequestMapping(value = "message")
    public String skinToMessage(Model model, Integer flag){
        model.addAttribute("flag",flag);
        return "message";
    }
    @RequestMapping(value = "pubproject")
    public String skinToPubProject(){
        return "pubproject";
    }
    @RequestMapping(value = "searchperson")
    public String skinToSearchPerson(){return "searchperson";}
    @RequestMapping(value = "searchproject")
    public String skinToSearchProject(){
        return "searchproject";
    }
    @RequestMapping(value = "showinfo")
    public String skinToShowinfo(){
        return "showinfo";
    }
    @RequestMapping(value = "signupproject")
    public String skinToSignupProject(){
        return "signupproject";
    }
    @RequestMapping(value = "projectdetail")
    public String skinToProjectDetail(Model model, Integer projectId){
        model.addAttribute("projectId",projectId);
        User user=(User)session.getAttribute("user");
        if(user!=null){
            model.addAttribute("userId",user.getId());
        }

        return "projectdetail";
    }
    @RequestMapping(value = "showperson")
    public String skinToShowPerson(Model model, Integer userId){
        model.addAttribute("userId",userId);
        return "showperson";
    }
    @RequestMapping(value = "messageinfo")
    public String skinToMessageInfo(Model model, Integer messageId){
        User user= (User) session.getAttribute("user");
        if(user!=null){
            int userId=user.getId();
            model.addAttribute("userId",userId);
        }
        model.addAttribute("messageId",messageId);

        return "messageInfo";
    }
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Object findUser(String userName,String passWord,int role){
        User user= userService.findUser(userName,role);
        passWord = MD5Util.getMD5(passWord);
        if(user==null){
            return -1;
        }
        String userPass=user.getPassword();
        if(!userPass.equals(passWord)){
           return -2;
        }
        int userId=user.getId();
        UserDetail userDetail=userService.getUserDetailService(userId);
        session.setAttribute("user",user);
        session.setAttribute("userDetail",userDetail);
        return 1;
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public Object insert(String username,String name,  String password, Integer flag,int sex,String studentNum,String college,String profession,
    String inputEmail,String wechat,Integer wechatP,String phone,Integer phoneP,String pic,String feature,String exprience){
        int count=userService.getUserByName(username);
        if(count>0){
            return -1;
        }
        if(flag==0||flag==3){
            int num=userService.getStudentIDNum(studentNum);
            if(num>0){
                return -2;
            }
        }
        int result= userService.register(username,name,password,flag,sex,studentNum,college,profession,inputEmail,wechat,wechatP,phone,phoneP,pic,feature,exprience);
        return result;
    }
    @RequestMapping(value = "user",method = RequestMethod.GET)
    @ResponseBody
    public Object getUser(HttpServletRequest request){
        Map<String,Object> result=new HashMap<>();
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        result.put("user",session.getAttribute("user"));
        result.put("userDetail",session.getAttribute("userDetail"));
        return result;
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Object updateUser(String name, Integer flag,int sex,String studentNum,String college,String profession,String password,
                              String inputEmail,String wechat,Integer wechatP,String phone,Integer phoneP,String pic,String feature,String exprience){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int userId=user.getId();
        feature=feature.trim();
        exprience=exprience.trim();
        int flag1=user.getFlag();
        if(flag1==0||flag1==3){
          UserDetail userDetail=(UserDetail)session.getAttribute("userDetail");
          if(!userDetail.getStudentID().equals(studentNum)){
              int num=userService.getStudentIDNum(studentNum);
              if(num>0){
                  return -2;
              }
          }
        }
        int result= userService.updateUserInfo(userId,name,sex,studentNum,college,profession,inputEmail,wechat,wechatP,phone,phoneP,pic,feature,exprience,password);
        if(result>0){
            UserDetail userDetail=userService.getUserDetailService(userId);
            session.setAttribute("userDetail",userDetail);
        }
        return result;
    }


    @RequestMapping(value = "projectdetail",method = RequestMethod.POST)
    @ResponseBody
    public Object insertProjectDetail(String title,String name,Integer type,Integer processType,String introdution
    ,String leder,String teacher,Integer findType,Integer findNum,String findIntrodution){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int flag=user.getFlag();
        if(flag==4){
           return -1;
        }
        int userId=user.getId();
        int result=userService.insertProjectDetail(userId,title,name,type,processType,introdution,leder,teacher,findType,findNum,findIntrodution);
        if(result>0){
            return 1;
        }
        return 0;
    }
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public Object fileUpload(HttpServletRequest request,@RequestParam("file1") MultipartFile file){
        Map<String,Object> result=new HashMap<>();
        if(file.isEmpty()){
            result.put("status",1);
            return result;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path =uploadSrc.getSrc();
        String src=uploadSrc.getIpHost()+fileName;
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            result.put("status",0);
            result.put("filename",src);
            return result;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result.put("status",1);
            return result;
        }
    }
    @RequestMapping(value = "getprojectdetail",method = RequestMethod.POST)
    @ResponseBody
    public Object getMeProjectDetail(Integer flag,Integer page,Integer pageSize){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int userId=user.getId();
        return userService.getProjectDetail(userId,(page-1)*pageSize,pageSize);
    }
    @RequestMapping(value = "getalluser",method = RequestMethod.POST)
    @ResponseBody
    public Object getAllUser(Integer page,Integer pageSize){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int userId=user.getId();
        return userService.getUserList(userId,(page-1)*pageSize,pageSize);
    }
    @RequestMapping(value = "getallprojectdetail",method = RequestMethod.POST)
    @ResponseBody
    public Object getAllProjectDetail(Integer flag,Integer page,Integer pageSize){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int userId=user.getId();
        return userService.getAllProjectDetail(flag,userId,(page-1)*pageSize,pageSize);
    }
    @RequestMapping(value = "getsignprojectdetail",method = RequestMethod.POST)
    @ResponseBody
    public Object getSignProjectDetail(Integer page,Integer pageSize){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int userId=user.getId();
        return userService.getAllSign(userId,(page-1)*pageSize,pageSize);
    }

    @RequestMapping(value = "getmessage",method = RequestMethod.POST)
    @ResponseBody
    public Object getAllMessage(Integer flag,Integer page,Integer pageSize){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int userId=user.getId();
        return userService.getMessage(flag,userId,(page-1)*pageSize,pageSize);
    }
    @RequestMapping(value = "getsingleproject",method = RequestMethod.POST)
    @ResponseBody
    public Object getSingleProject(Integer projectId){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int userId=user.getId();
        ProjectInfo info= userService.getProjectInfo(projectId,userId);
        info.setUser(user);
        return info;
    }

    @RequestMapping(value = "getsinglemessage",method = RequestMethod.POST)
    @ResponseBody
    public Object getSingleMessage(Integer messageId){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int userId=user.getId();
        return userService.getSingleMessage(messageId);
    }

    @RequestMapping(value = "getsingleuser",method = RequestMethod.POST)
    @ResponseBody
    public Object getSingleUser(Integer userId){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int ownId=user.getId();
        return userService.getSingleUser(userId,ownId);
    }

    @RequestMapping(value = "application",method = RequestMethod.POST)
    @ResponseBody
    public Object application(Integer projectId,Integer userId,Integer type){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return false;
        }
        int ownId=user.getId();
        return userService.applicationProject(userId,ownId,projectId,type);
    }
    @RequestMapping(value = "updatemessage",method = RequestMethod.POST)
    @ResponseBody
    public Object updateMessage(Integer projectId,Integer userId,Integer messageId,Integer status){
        return userService.updateMessage(projectId,userId,messageId,status);
    }
    @RequestMapping(value = "checkname",method = RequestMethod.POST)
    @ResponseBody
    public Object checkName(String username){
        User user= (User) session.getAttribute("user");
        if(user==null){
            return userService.getUserByName(username);
        }
        String name=user.getUsername();
        if(name.equals(username)){
            return 0;

        }
        return userService.getUserByName(name);
    }
}
