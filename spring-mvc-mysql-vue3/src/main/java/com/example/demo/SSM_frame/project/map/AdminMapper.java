package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("SELECT * FROM admin WHERE userid = #{userId}")
    admin selectAdminById(int userId);

    @Select("SELECT * FROM admin")
    List<admin> selectAllAdmins();

    @Insert("INSERT INTO admin (userid, adminkey) VALUES (#{userId}, #{adminkey})")
    void insertAdmin(admin admin);

    @Update("UPDATE admin SET adminkey = #{adminkey} WHERE userid = #{userId}")
    void updateAdmin(admin admin);

    @Delete("DELETE FROM admin WHERE userid = #{userId}")
    void deleteAdmin(int userId);
}
