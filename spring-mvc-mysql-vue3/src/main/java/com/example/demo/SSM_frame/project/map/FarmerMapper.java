package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.farmer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FarmerMapper {
    @Select("SELECT f.*, u.* FROM Farmer f JOIN User u ON f.userid = u.userid WHERE f.userid = #{userid}")
    farmer selectFarmerById(int userId);
    @Select("SELECT f.*, u.* FROM farmer f JOIN user u ON f.userid = u.userid")
    List<farmer> selectAllFarmers();

    @Insert("INSERT INTO farmer (userid, address, subsidys) VALUES (#{userid}, #{address}, #{subsidys})")
    void insertFarmer(farmer farmer);


    @Update("UPDATE farmer SET address = #{address}, subsidys = #{subsidys} WHERE userid = #{userid}")
    void updateFarmer(farmer farmer);

    @Delete("DELETE FROM farmer WHERE userid = #{userid}")
    void deleteFarmer(int userid);


}
