package com.wjl.mapper;

import com.wjl.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wjl
 * @date 2018/2/8
 */
@Mapper
public interface UserMapper {
    /*
     查找用户
     */
    @Select("SELECT * FROM user WHERE username = #{userName} and flag=#{flag}")
    public User findUser(@Param("userName") String userName,@Param("flag") int flag);
    /*
      分页获取所有用户
     */
    @Select("SELECT * FROM user where id!=#{id} limit #{page},#{pageSize}")
    public List<User> getAllUser(@Param("id") Integer id,@Param("page") Integer page, @Param("pageSize") Integer pageSize);

    @Select("SELECT count(*) FROM user where id!=#{id} ")
    public int getAllUserNum(@Param("id") Integer id);
    /*
    获取单个用户
     */
    @Select("SELECT * FROM user where id=#{id}")
    public User getSingleUser(@Param("id") Integer id);
    /*
     新增用户
     */
    @Insert({"INSERT INTO user(username,password,flag) VALUES (#{user.username},#{user.password},#{user.flag})"})
    @Options(useGeneratedKeys=true, keyProperty = "user.id",keyColumn="id")
    public void insert(@Param("user")User user);
    /*
     新增用户的具体信息
     */
    @Insert("INSERT INTO userdetil(userID,name,sex,studentID,department,major,email,weichart,isShowWei,phoneNum,isShowPhoneNum,photo,specialty,competitionExperience,type)" +
            "values(#{userID},#{name},#{sex},#{studentID},#{department},#{major},#{email},#{weichart},#{isShowWei},#{phoneNum},#{isShowPhoneNum},#{photo},#{specialty}," +
            "#{competitionExperience},#{type})")
    public int insertDetail(@Param("userID") int userId,@Param("name")String name,@Param("sex") int sex,@Param("studentID") String studentNum,@Param("department")String college,
                     @Param("major")String profession,@Param("email")String inputEmail,@Param("weichart")String wechat,@Param("isShowWei")int wechatP,
                     @Param("phoneNum")String phone,@Param("isShowPhoneNum")int phoneP,@Param("photo")String pic,@Param("specialty")String feature,
                     @Param("competitionExperience")String exprience ,@Param("type") String type);
    /*
     获取用户具体信息根据userID
     */
    @Select("Select * from userdetil where userID=${userID}")
    public UserDetail getUserDeatil(@Param("userID") int userId);
    /*
     更新用户
     */
    @Update("update user set username=${userName},password=#{password} where id=${id}")
    public int updateUser(@Param("userName") String userName,@Param("id") int id,@Param("password") String password);
    @Update("update userdetil set sex=#{sex},name=#{name},studentID=#{studentID},department=#{department},major=#{major},email=#{email},weichart=#{weichart},isShowWei=#{isShowWei}" +
            ",phoneNum=#{phoneNum},isShowPhoneNum=#{isShowPhoneNum},photo=#{photo},specialty=#{specialty},competitionExperience=#{competitionExperience} where userId=#{userID}" )
    public int updateDetail(@Param("userID") int userId,@Param("name")String name,@Param("sex") int sex,@Param("studentID") String studentNum,@Param("department")String college,
                            @Param("major")String profession,@Param("email")String inputEmail,@Param("weichart")String wechat,@Param("isShowWei")int wechatP,
                            @Param("phoneNum")String phone,@Param("isShowPhoneNum")int phoneP,@Param("photo")String pic,@Param("specialty")String feature,
                            @Param("competitionExperience")String exprience );
    /*
     新增项目
     */
    @Insert("insert into projetdetail(userId,title,name,type,processType,introdution,leder,teacher,findType,findNum,leftNum,findIntrodution)" +
            "values(#{userId},#{title},#{name},#{type},#{processType},#{introdution},#{leder},#{teacher},#{findType},#{findNum},#{leftNum},#{findIntrodution})")
    public int insertProjectDetail(@Param("userId") Integer userId,@Param("title") String title,@Param("name")String name,@Param("type")Integer type,
                                   @Param("processType")Integer processType,@Param("introdution")String introdution
            ,@Param("leder")String leder,@Param("teacher")String teacher,@Param("findType")Integer findType,@Param("findNum")Integer findNum,
                                   @Param("leftNum")Integer leftNum,@Param("findIntrodution")String findIntrodution);
   /*
    分页查找我创建的项目
    */
    @Select("select * from projetdetail where userId=#{userId} limit #{page},#{pageSize}")
    public List<ProjectDetail> getMeProjectDetail(@Param("userId") Integer userID,@Param("page") Integer page, @Param("pageSize") Integer pageSize);
    @Select("select count(*) from projetdetail where userId=#{userId} ")
    public int  getMeProjectDetailNum(@Param("userId") Integer userID);

    /*
     获取去掉userid已报名外的其他的项目
     */
    @Select("select * from projetdetail where id not in (select  projectId from ownproject where userId=#{userId} and flag!=2) and userId!=#{userId} and leftNum>0 order by createTime desc limit #{page},#{pageSize}")
    public List<ProjectDetail> getAllProjectDetail(@Param("userId") Integer userID,@Param("page") Integer page, @Param("pageSize") Integer pageSize);
    /*
     获取项目表中不属于userId的项目集合
     */
    @Select("select count(*) from projetdetail where userId!=#{userId} and leftNum>0")
    public int getAllProjectDetailNumNew(@Param("userId") Integer userID);
    /*
    获取项目表中不属于userId的项目数量
     */
    @Select("select count(*) from projetdetail where userId!=#{userId} and leftNum>0 order by createTime desc limit #{page},#{pageSize}")
    public List<ProjectDetail> getAllProjectDetailNew(@Param("userId") Integer userID,@Param("page") Integer page, @Param("pageSize") Integer pageSize);
    /*
     获取已报名但是没有拒绝的项目数量
     */
    @Select("select count(*) from ownproject where userId=#{userId} and flag!=2")
    public int getSignNum(@Param("userId") Integer userID);
    /*
         获取去掉userid已报名外的其他的项目的数量
         */
    @Select("select count(*) from projetdetail where id not in (select  projectId from ownproject where userId=#{userId} and flag!=2) and userId!=#{userId} and leftNum>0 ")
    public int getAllProjectDetailNum(@Param("userId") Integer userID);
    /*
     查询单个项目
     */
    @Select("select * from projetdetail where id=#{id}")
    public ProjectDetail getSingeProjectDeatil(@Param("id")Integer id);
    /*
     新增消息
     */
    @Insert("insert into message(userID,ownID,projectID,type,status)values(#{userID},#{ownID},#{projectID},#{type},#{status}) ")
    public int insertMessage(@Param("userID")Integer userID,@Param("ownID")Integer ownID,@Param("projectID")Integer projectID,@Param("type")Integer type,
                             @Param("status") Integer status);
    /*
     获取消息内容
     */
    @Select("select * from message where id=#{id}")
    public Message getSingleMessage(@Param("id") int id);
    /*
     获取我发送的消息列表
     */
    @Select("select  * from message where (ownID=#{ownId} and type=1) or(userID=#{ownId} and type=0) limit #{page},#{pageSize}")
    public List<Message> getMeSendMessageList(@Param("ownId") Integer userId,@Param("page") int page,@Param("pageSize") int  pageSize);
    @Select("select  count(*) from message where (ownID=#{ownId} and type=1) or(userID=#{ownId} and type=0) ")
    public int getMeSendMessageListNum(@Param("ownId") Integer userId);
    /*
     获取我接受的消息列表
     */
    @Select("select  * from message where (userID=#{userId} and type=1) or(ownID=#{userId} and type=0) limit #{page},#{pageSize}")
    public List<Message> getMeMessageList(@Param("userId") Integer userId,@Param("page") int page,@Param("pageSize") int  pageSize);
    @Select("select  count(*) from message where (userID=#{userId} and type=1) or(ownID=#{userId} and type=0) ")
    public int getMeMessageListNum(@Param("userId") Integer userId);
    /*
     获取申请的状态
     */
    @Insert("insert into ownproject(userID,ownID,projectID,status,flag)values(#{userID},#{ownID},#{projectID},#{status},#{flag})")
    public int insertOwnProject(@Param("userID")Integer userID,@Param("ownID")Integer ownID,@Param("projectID")Integer projectID,@Param("status")Integer status,@Param("flag") Integer flag);

    @Select("select * from ownproject where status=#{status} and userID=#{userID}  order by createTime desc limit #{page},#{pageSize}")
    public List<OwnProject> getOwnProjectList(@Param("status") Integer status,@Param("userID") Integer userId,@Param("page") Integer page,@Param("pageSize")Integer pageSize);
    @Select("select * from ownproject where flag=0 and projectID=#{projectId}  order by createTime desc")
    public List<OwnProject> getOwnProjectUserList(@Param("projectId") Integer projectId);
    @Update("update message set status=${status} where id=${id}")
    public int updateMessage(@Param("status") Integer status,@Param("id") int id);
    @Update("update ownproject set flag=${flag} where userID=${userID} and projectID=${projectID}")
    public int updateOwnProject(@Param("flag") Integer flag,@Param("userID") int userId,@Param("projectID")Integer projectId);
    @Select("select* from user where username=#{name}")
    public List<User> getUserByName(@Param("name") String name);
    @Select("select * from ownproject where  projectID=#{projectId} and userID=#{userId} and flag=0  order by createTime desc")
    public List<OwnProject> getOwnProjectbyProjectIdAndUserId(@Param("projectId") Integer projectId,@Param("userId") Integer userId);
    @Select("select count(*)  from ownproject where status=#{status} and userID=#{userID} ")
    public int getOwnProjectCount(@Param("status") Integer status,@Param("userID") Integer userId);
    @Update("update projetdetail set leftNum=leftNum-1 where id=#{projectId}")
    public int updateProjectLeftNum(@Param("projectId") Integer projectId);
    @Select("select count(*) from ownproject where  projectID=#{projectId} and userID=#{userId} and ownID=#{ownId} and status=#{status} and flag!=2")
    public int getOwnProjectNum(@Param("projectId") Integer projectId,@Param("userId") Integer userId,@Param("ownId") Integer ownId,@Param("status") Integer status);

}
