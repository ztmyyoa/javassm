package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.Subsidy;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SubsidyMapper {

    @Insert("INSERT INTO subsidy(title, amount, content )" +
            "VALUES(#{title}, #{amount}, #{content})")
    int insertSubsidy(Subsidy subsidy);
    @Insert("INSERT INTO subsidysign(title, amount, content ,farmerid,substatus)" +
            "VALUES(#{title}, #{amount}, #{content},#{farmerid},#{substatus})")
    int insertSubsidysign(Subsidy subsidy);
    @Select("SELECT * FROM subsidy")
    List<Subsidy> getAllSubsidies();
    @Select("SELECT * FROM subsidysign")
    List<Subsidy> getSubsidiessign();
    @Select("SELECT * FROM subsidysign WHERE farmerid = #{farmerid}")
    List<Subsidy> findByFarmerId(@Param("farmerid") int farmerid);

    @Select("SELECT * FROM subsidy WHERE subid = #{subid}")
    Subsidy findById(@Param("subid") int subid);

    @Update("UPDATE subsidysign SET substatus = #{substatus} WHERE subid = #{subid}")
    int updateStatus(@Param("subid") int subid, @Param("substatus") String substatus);
    @Update("UPDATE subsidy SET title = #{title}, amount = #{amount}, content = #{content}WHERE subid = #{subid}")
    void updateSubsidy(Subsidy subsidy);
    @Delete("DELETE FROM subsidy WHERE subid = #{subid}")
    int deleteSubsidy(@Param("subid") int subid);


}
