package com.wjl.service;

import com.wjl.mapper.UserMapper;
import com.wjl.model.*;
import com.wjl.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wjl
 * @date 2018/2/8
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUser(String userName, String userPass,int flag){
        userPass = MD5Util.getMD5(userPass);
        return userMapper.findUser(userName, userPass,flag);
    }

    public int insert(String userName, String password, Integer flag) {
        password = MD5Util.getMD5(password);
        User user=new User();
        user.setFlag(flag);
        user.setPassword(password);
        user.setUsername(userName);
        userMapper.insert(user);
        return user.getId();
    }

    public int insertDetailService( Integer userId,int sex,String studentNum,String college,String profession,
                                   String inputEmail,String wechat,Integer wechatP,String phone,Integer phoneP,String pic,String feature,String exprience){
        return userMapper.insertDetail(userId,sex,studentNum,college,profession,inputEmail,wechat,wechatP,phone,phoneP,pic,feature,exprience);
    }

    public UserDetail getUserDetailService(int userId){
        return userMapper.getUserDeatil(userId);
    }

    public int  updateUserInfo(String userName,  Integer flag,Integer userId,int sex,String studentNum,String college,String profession,
                          String inputEmail,String wechat,Integer wechatP,String phone,Integer phoneP,String pic,String feature,String exprience){
        int result1=userMapper.updateUser(userName,userId);
        if(result1>0){
            int result=userMapper.updateDetail(userId,sex,studentNum,college,profession,inputEmail,wechat,wechatP,phone,phoneP,pic,feature,exprience);
            return result;
        }
        return 0;
    }
    /*
      增加项目
     */
    public int insertProjectDetail(Integer userId,String title,String name,Integer type,Integer processType,String introdution
            ,String leder,String teacher,Integer findType,Integer findNum,String findIntrodution){
       return  userMapper.insertProjectDetail(userId,title,name,type,processType,introdution,leder,teacher,findType,findNum,findIntrodution);
    }
    /*
       分页获取职位列表
     */
    public List<UserBean> getUserList(int id,int page,int pageSize){
         List<UserBean> list=new ArrayList<>();
         List<User> userList=userMapper.getAllUser(id,page,pageSize);
         if(userList!=null&&userList.size()>0){
             for(User user:userList){
                    int userId=user.getId();
                    UserDetail userDetail=userMapper.getUserDeatil(userId);
                    UserBean userBean=new UserBean();
                    userBean.setUser(user);
                    userBean.setUserDetail(userDetail);
                    list.add(userBean);
             }
         }
         return list;
     }
    /*
    分页获取我的项目
     */
    public List<ProjectDetail> getProjectDetail(Integer userId,int page,int pageSize){
        return userMapper.getMeProjectDetail(userId,page,pageSize);
    }
    /*
    分页获取所有项目
     */
    public List<ProjectDetail> getAllProjectDetail(int flag,Integer userId,int page,int pageSize){
        if(flag==1){
            return userMapper.getAllProjectDetail(userId,page,pageSize);
        }
        return userMapper.getMeProjectDetail(userId,page,pageSize);
    }


    public List<ProjectDetail> getAllSign(int userId,int page,int pageSize){
        List<ProjectDetail> list=new ArrayList<>();
        List<OwnProject> ownList=userMapper.getOwnProjectList(0,userId,page,pageSize);
        if(ownList!=null&&ownList.size()>0){
            for(OwnProject ownProject:ownList){
              int projectId=ownProject.getProjectID();
                ProjectDetail projectDetail=userMapper.getSingeProjectDeatil(projectId);
                if(projectDetail!=null){
                    list.add(projectDetail);
                }
            }
        }
        return list;
    }

    public List<MessageInfo> getMessage(int flag,int userId,int page,int pageSize){
        List<MessageInfo> list=new ArrayList<>();
        List<Message> messageList=null;
        if(flag==1){
            messageList=userMapper.getMeMessageList(userId,page,pageSize);
        }else{
            messageList=userMapper.getSendMessageList(userId,page,pageSize);
        }
        if(messageList!=null&&messageList.size()>0){
            for(Message message:messageList ){
                MessageInfo info=new MessageInfo();
                int ownId=message.getOwnID();
                User user=userMapper.getSingleUser(ownId);
                info.setName(user.getUsername());
                info.setId(message.getId());
                info.setCreateTime(message.getCreateTime());
                info.setProjectId(message.getProjectID());
                int status=message.getStatus();
                info.setStatus("通过");
                if(status==1){
                    info.setStatus("审核中");
                }else if(status==2){
                    info.setStatus("拒绝");
                }
                int type=message.getType();
                info.setTypeName("申请");
                if(type==1){
                    info.setTypeName("邀请");
                }
                list.add(info);
            }
        }
        return list;
    }

    public ProjectInfo getProjectInfo(int projectId){
        ProjectInfo info=new ProjectInfo();
        ProjectDetail projectDetail=userMapper.getSingeProjectDeatil(projectId);
        if(projectDetail!=null){
            List<UserBean> userList=new ArrayList<>();
            List<OwnProject> list=userMapper.getOwnProjectUserList(projectId);
            if(list!=null&&list.size()>0){
                for(OwnProject ownProject:list){
                    int userId=ownProject.getUserID();
                    User user=userMapper.getSingleUser(userId);
                    UserDetail userDetail=userMapper.getUserDeatil(userId);
                    UserBean userBean=new UserBean();
                    userBean.setUserDetail(userDetail);
                    userBean.setUser(user);
                    userList.add(userBean);

                }
            }
            info.setProjectDetail(projectDetail);
            info.setUserList(userList);
        }
        return info;
    }

    public MessageBean getSingleMessage(int id){
        MessageBean messageBean=new MessageBean();
        Message message=userMapper.getSingleMessage(id);
        if(message!=null){
            int userId=message.getUserID();
            User user=userMapper.getSingleUser(id);
            UserDetail userDetail=userMapper.getUserDeatil(userId);
            int ownId=message.getOwnID();
            messageBean.setUser(user);
            messageBean.setUserDetail(userDetail);
            ProjectDetail projectDetail=userMapper.getSingeProjectDeatil(message.getProjectID());
            messageBean.setProjectDetail(projectDetail);
            messageBean.setMessage(message);
        }
        return messageBean;
    }

    public UserBean getSingleUser(int id){
       UserBean userBean=new UserBean();
       User user=userMapper.getSingleUser(id);
       if(user!=null){
           userBean.setUser(user);
           UserDetail userDetail=userMapper.getUserDeatil(id);
           userBean.setUserDetail(userDetail);
       }
        return userBean;
    }

    public int applicationProject(int userId,int ownId,int projectId,int type){
       int result=userMapper.insertMessage(userId,ownId,projectId,type,1);
       if(result>0){
           int result2=userMapper.insertOwnProject(userId,ownId,projectId,type,1);
           if(result2>0){
               return 1;
           }
       }
       return 0;
    }

    public int updateMessage(int projectId,int userId,int messageId,int status){
        int result1=userMapper.updateMessage(status,messageId);
        if(result1>0){
            int result2=userMapper.updateOwnProject(status,userId,projectId);
            if(result2>0){
                return 1;
            }
        }
        return 0;
    }
}
