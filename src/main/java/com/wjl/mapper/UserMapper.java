package com.wjl.mapper;

import com.wjl.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author wjl
 * @date 2018/2/8
 */
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE userName = #{userName} and userPass = #{userPass}")
    User findUser(@Param("userName") String userName, @Param("userPass") String userPass);

    @Select("INSERT INTO user(userName,userPass,flag) VALUES (#{userName},)#{userPass},#{flag}")
    int insert(@Param("userName") String userName, @Param("userPass") String userPass, @Param("flag") Integer flag);
}
