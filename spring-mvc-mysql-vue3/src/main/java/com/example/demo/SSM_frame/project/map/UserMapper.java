package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.user;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM zhunong.user WHERE userid = #{userid}")
    user selectUserById(int userid);

    @Select("SELECT * FROM zhunong.user")
    List<user> selectAllUsers();

    @Insert("INSERT INTO zhunong.user(name, username, password, usertype, email, phone, regtime, avatar,restatus) " +
            "VALUES(#{name}, #{username}, #{password}, #{usertype}, #{email}, #{phone}, #{regtime}, #{avatar},#{restatus})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insertUser(user user);

    @Update("UPDATE zhunong.user SET name=#{name}, username=#{username}, password=#{password}, usertype=#{usertype}, " +
            "email=#{email}, phone=#{phone}, regtime=#{regtime}, avatar=#{avatar} ,restatus=#{restatus} WHERE userid=#{userid}")
    int updateUser(user user);
    @Update("UPDATE zhunong.user SET name=#{name}, username=#{username} ,email=#{email}, phone=#{phone},avatar=#{avatar} WHERE userid=#{userid}")
    int updatefarmer(user user);

    @Delete("DELETE FROM zhunong.user WHERE userid = #{userid}")
    int deleteUser(int userid);
    @Select("SELECT * FROM zhunong.user WHERE username = #{username}")
    user getUserByUsername(String username);

    @Update("UPDATE zhunong.user SET restatus = #{restatus} WHERE userid = #{userId}")
    void auditUser (@Param("userId") Integer userId, @Param("restatus") int restatus);
    @Select("SELECT * FROM zhunong.user WHERE restatus = #{restatus}")
    List<user> selectUsersByRestatus(int restatus);

    @Select("SELECT usertype, COUNT(*) AS count FROM user GROUP BY usertype")
    List<Map<String, Object>> countUsersByRole();

}
