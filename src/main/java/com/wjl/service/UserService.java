package com.wjl.service;

import com.wjl.mapper.UserMapper;
import com.wjl.model.*;
import com.wjl.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public User findUser(String userName,int flag){

        return userMapper.findUser(userName,flag);
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
    @Transactional
    public int register(String username,String name,  String password, Integer flag,int sex,String studentNum,String college,String profession,
                        String inputEmail,String wechat,Integer wechatP,String phone,Integer phoneP,String pic,String feature,String exprience){
        int userId=this.insert(username,password,flag);
        int result=insertDetailService(userId,name,sex,studentNum,college,profession,inputEmail,wechat,wechatP,phone,phoneP,pic,feature,exprience);
        return result;
    }
    public int insertDetailService( Integer userId,String name,int sex,String studentNum,String college,String profession,
                                   String inputEmail,String wechat,Integer wechatP,String phone,Integer phoneP,String pic,String feature,String exprience){
        return userMapper.insertDetail(userId,name,sex,studentNum,college,profession,inputEmail,wechat,wechatP,phone,phoneP,pic,feature,exprience);
    }

    public UserDetail getUserDetailService(int userId){
        return userMapper.getUserDeatil(userId);
    }
    @Transactional
    public int  updateUserInfo(Integer userId,String name,int sex,String studentNum,String college,String profession,String inputEmail,
                          String wechat,Integer wechatP,String phone,Integer phoneP,String pic,String feature,String exprience,String password){
            int result=userMapper.updateDetail(userId,name,sex,studentNum,college,profession,inputEmail,wechat,wechatP,phone,phoneP,pic,feature,exprience);
            return result;
    }
    /*
      增加项目
     */
    public int insertProjectDetail(Integer userId,String title,String name,Integer type,Integer processType,String introdution
            ,String leder,String teacher,Integer findType,Integer findNum,String findIntrodution){
       return  userMapper.insertProjectDetail(userId,title,name,type,processType,introdution,leder,teacher,findType,findNum,findNum,findIntrodution);
    }
    /*
       分页获取职位列表
     */
    public UserInfo getUserList(int id,int page,int pageSize){
        UserInfo message=new UserInfo();
        int count=userMapper.getAllUserNum(id);
        if(count==0){
            message.setCount(0);
            message.setPageNum(0);
            message.setNowPage(page);
        }else{
            int allPage=(int)Math.ceil(Double.parseDouble(String.valueOf(count))/(double)pageSize);
            message.setCount(count);
            message.setPageNum(allPage);
            message.setNowPage(page);
        }
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
        message.setUserBean(list);
        return message;
     }
    /*
    分页获取我的项目
     */
    public ProjectMessage getProjectDetail(Integer userId,int page,int pageSize){
        ProjectMessage message=new ProjectMessage();
        int count=userMapper.getMeProjectDetailNum(userId);
        if(count==0){
            message.setCount(0);
            message.setPageNum(0);
            message.setNowPage(page);
        }else{
            int allPage=(int)Math.ceil(Double.parseDouble(String.valueOf(count))/(double)pageSize);
            message.setCount(count);
            message.setPageNum(allPage);
            message.setNowPage(page);
        }
        List<ProjectDetail> list=userMapper.getMeProjectDetail(userId,page,pageSize);
        if(list!=null&&list.size()>0){
            for(ProjectDetail projectDetail:list){
                String times=projectDetail.getCreateTime();
                projectDetail.setCreateTime(times.substring(0,13));
            }
        }
        message.setProjectDetail(list);
        return message;
    }
    /*
    分页获取所有项目
     */
    public ProjectMessage getAllProjectDetail(int flag,Integer userId,int page,int pageSize){
        ProjectMessage message=new ProjectMessage();
        if(flag==1){
            int hasNum=userMapper.getSignNum(userId);
            List<ProjectDetail> list=new ArrayList<>();
            int count=0;
            if(hasNum>0){
                list=userMapper.getAllProjectDetail(userId,page,pageSize);
                count=userMapper.getAllProjectDetailNum(userId);
            }else{
                list=userMapper.getAllProjectDetailNew(userId,page,pageSize);
                count=userMapper.getAllProjectDetailNumNew(userId);
            }
            if(count==0){
                message.setCount(0);
                message.setPageNum(0);
                message.setNowPage(page);
            }else{
                int allPage=(int)Math.ceil(Double.parseDouble(String.valueOf(count))/(double)pageSize);
                message.setCount(count);
                message.setPageNum(allPage);
                message.setNowPage(page);
            }
            if(list!=null&&list.size()>0){
                for(ProjectDetail projectDetail:list){
                    String times=projectDetail.getCreateTime();
                    projectDetail.setCreateTime(times.substring(0,13));
                }
            }
            message.setProjectDetail(list);
            userMapper.getMeProjectDetailNum(userId);
            return message;

        }
        List<ProjectDetail> list= userMapper.getMeProjectDetail(userId,page,pageSize);
        if(list!=null&&list.size()>0){
            for(ProjectDetail projectDetail:list){
                String times=projectDetail.getCreateTime();
                projectDetail.setCreateTime(times.substring(0,13));
            }
        }
        int count=userMapper.getMeProjectDetailNum(userId);
        if(count==0){
            message.setCount(0);
            message.setPageNum(0);
            message.setNowPage(page);
        }else{
            int allPage=(int)Math.ceil(Double.parseDouble(String.valueOf(count))/(double)pageSize);
            message.setCount(count);
            message.setPageNum(allPage);
            message.setNowPage(page);
        }
        message.setProjectDetail(list);
        return message;
    }


    public ProjectMessage getAllSign(int userId,int page,int pageSize){
        ProjectMessage message=new ProjectMessage();
        List<ProjectDetail> list=new ArrayList<>();
        int count=userMapper.getOwnProjectCount(userId);
        if(count==0){
            message.setCount(0);
            message.setPageNum(0);
            message.setNowPage(page);
        }else{
            int allPage=(int)Math.ceil(Double.parseDouble(String.valueOf(count))/(double)pageSize);
            message.setCount(count);
            message.setPageNum(allPage);
            message.setNowPage(page);
        }
        List<OwnProject> ownList=userMapper.getOwnProjectList(userId,page,pageSize);
        if(ownList!=null&&ownList.size()>0){
            for(OwnProject ownProject:ownList){
              int projectId=ownProject.getProjectID();
              int flag=ownProject.getFlat();
                ProjectDetail projectDetail=userMapper.getSingeProjectDeatil(projectId);
                if(projectDetail!=null){
                    String times=projectDetail.getCreateTime();
                    projectDetail.setCreateTime(times.substring(0,13));
                    projectDetail.setFlag(flag);
                    list.add(projectDetail);
                }
            }
        }
        message.setProjectDetail(list);
        return message;
    }

    public MessagePoJo getMessage(int flag,int userId,int page,int pageSize){
        MessagePoJo messagePoJo=new MessagePoJo();

        List<MessageInfo> list=new ArrayList<>();
        List<Message> messageList=null;
        if(flag==1){
            messageList=userMapper.getMeSendMessageList(userId,page,pageSize);
            int count=userMapper.getMeSendMessageListNum(userId);
            if(count==0){
                messagePoJo.setCount(0);
                messagePoJo.setPageNum(0);
                messagePoJo.setNowPage(page);
            }else{
                int allPage=(int)Math.ceil(Double.parseDouble(String.valueOf(count))/(double)pageSize);
                messagePoJo.setCount(count);
                messagePoJo.setPageNum(allPage);
                messagePoJo.setNowPage(page);
            }
        }else{
            messageList=userMapper.getMeMessageList(userId,page,pageSize);
            int count=userMapper.getMeMessageListNum(userId);
            if(count==0){
                messagePoJo.setCount(0);
                messagePoJo.setPageNum(0);
                messagePoJo.setNowPage(page);
            }else{
                int allPage=(int)Math.ceil(Double.parseDouble(String.valueOf(count))/(double)pageSize);
                messagePoJo.setCount(count);
                messagePoJo.setPageNum(allPage);
                messagePoJo.setNowPage(page);
            }
        }
        if(messageList!=null&&messageList.size()>0){
            for(Message message:messageList ){
                MessageInfo info=new MessageInfo();
                int ownId=message.getOwnID();
                int tmpId=message.getUserID();
                UserDetail ownuserDetail=userMapper.getUserDeatil(ownId);
                UserDetail userDetail=userMapper.getUserDeatil(tmpId);
                info.setId(message.getId());
                info.setCreateTime(message.getCreateTime().substring(0,13));
                info.setProjectId(message.getProjectID());
                int status=message.getStatus();
                info.setStatus("通过");
                if(status==1){
                    info.setStatus("审核中");
                }else if(status==2){
                    info.setStatus("拒绝");
                }
                int type=message.getType();
                info.setType(type);
                info.setTypeName("申请");
                info.setName(userDetail.getName());
                info.setOwnName(ownuserDetail.getName());
                if(type==1){
                    info.setTypeName("邀请");
                    info.setName(userDetail.getName());
                    info.setOwnName(ownuserDetail.getName());
                }
                ProjectDetail projectDetail=userMapper.getSingeProjectDeatil(message.getProjectID());
                if(projectDetail!=null){
                    info.setProjectName(projectDetail.getTitle());
                }
                list.add(info);
            }
        }
        messagePoJo.setMessageInfo(list);
        return messagePoJo;
    }

    public ProjectInfo getProjectInfo(int projectId,int ownId){
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
            //是否是已报名的项目
            info.setFlag(0);
            List<OwnProject> ownProjectsList=userMapper.getOwnProjectbyProjectIdAndUserId(projectId,ownId);
            if(ownProjectsList!=null&&ownProjectsList.size()>0){
                info.setFlag(1);
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
            User user=userMapper.getSingleUser(userId);
            UserDetail userDetail=userMapper.getUserDeatil(userId);
            messageBean.setUser(user);
            messageBean.setUserDetail(userDetail);
            ProjectDetail projectDetail=userMapper.getSingeProjectDeatil(message.getProjectID());
            int ownId=message.getOwnID();
            User own=userMapper.getSingleUser(ownId);
            UserDetail ownDetail=userMapper.getUserDeatil(ownId);
            messageBean.setOwn(own);
            messageBean.setOwnDetail(ownDetail);
            messageBean.setProjectDetail(projectDetail);
            messageBean.setMessage(message);
        }
        return messageBean;
    }

    public UserBean getSingleUser(int id,int ownId){
       UserBean userBean=new UserBean();
       User user=userMapper.getSingleUser(id);
       if(user!=null){
           userBean.setUser(user);
           UserDetail userDetail=userMapper.getUserDeatil(id);
           userBean.setUserDetail(userDetail);
           List<ProjectDetail> project=userMapper.getMeProjectDetail(ownId,0,Integer.MAX_VALUE);
           userBean.setProject(project);
       }
        return userBean;
    }
    @Transactional
    public int applicationProject(int userId,int ownId,int projectId,int type){
        ProjectDetail projectDetail= userMapper.getSingeProjectDeatil(projectId);
        //计算该项目是否还需要人员
        int leftNum=projectDetail.getLeftNum();
        if(leftNum==0){
           return -1;
        }
        int projectType=projectDetail.getType();
        //申请加入该项目ownId为申请人 userId为项目所有人
        if(type==0){
            //此人是否申请过该项目
            int count=userMapper.getOwnProjectNum(projectId,ownId,userId);
            if(count==0){
                //此人是否申请过该类型项目
                int typeCount=userMapper.getOwnProjectTypeNum(projectType,ownId);
                if(typeCount>0){
                    return -2;
                }
                Message message=new Message();
                message.setOwnID(userId);
                message.setUserID(ownId);
                message.setProjectID(projectId);
                message.setType(type);
                message.setStatus(1);
                userMapper.insertMessage(message);
                if(message.getId()>0){
                    int result2=userMapper.insertOwnProject(ownId,message.getId(),userId,projectId,type,1);
                    if(result2>0){
                        return 1;
                    }
                }
            }else{
                return 2;
            }
            return 0;
        }else{//邀请此人加入该项目 userId为被邀请人 ownId为项目所有人
            //此人是否申请过该项目
            int count=userMapper.getOwnProjectNum(projectId,userId,ownId);
            if(count==0){
                //此人是否申请过该类型项目
                int typeCount=userMapper.getOwnProjectTypeNum(projectType,userId);
                if(typeCount>0){
                    return -2;
                }
                Message message=new Message();
                message.setOwnID(ownId);
                message.setUserID(userId);
                message.setProjectID(projectId);
                message.setType(type);
                message.setStatus(1);
                userMapper.insertMessage(message);
                if(message.getId()>0){
                    int result2=userMapper.insertOwnProject(userId,message.getId(),ownId,projectId,type,1);
                    if(result2>0){
                        return 1;
                    }
                }
            }else{
                return 2;
            }
            return 0;
        }

    }
    @Transactional
    public int updateMessage(int projectId,int userId,int messageId,int status){
        if(status==0){
            ProjectDetail projectDetail=userMapper.getSingeProjectDeatil(projectId);
            int leftNum=projectDetail.getLeftNum();
            if(leftNum==0){
                return -1;
            }
        }
        int result1=userMapper.updateMessage(status,messageId);
        if(result1>0){
            int result2=userMapper.updateOwnProject(status,messageId);
            if(status==0){
                int result3=userMapper.updateProjectLeftNum(projectId);
                if(result3>0){
                    return 1;
                }
            }else{
                return 1;
            }

        }
        return 0;
    }

    public int getUserByName(String name){
        List<User> list=userMapper.getUserByName(name);
        if(list==null||list.size()==0){
            return 0;
        }
        return 1;
    }

    public int getStudentIDNum(String studentID){
        int count=userMapper.getStudentIDNum(studentID);
        return count;
    }
}
