package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.consumer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConsumerMapper {
    @Select("SELECT c.*,u.* FROM consumer c JOiN user u ON c.userid=u.userid WHERE c.userid = #{userid}")
    consumer selectConsumerById(int userid);

    @Select("SELECT c.*,u.* FROM consumer c JOIN user u ON c.userid= u.userid")
    List<consumer> selectAllConsumers();

    @Insert("INSERT INTO consumer (userid, address) VALUES (#{userid}, #{address})")
    void insertConsumer(consumer consumer);

    @Update("UPDATE consumer SET address = #{address} WHERE userid = #{userid}")
    void updateConsumer(consumer consumer);

    @Delete("DELETE FROM consumer WHERE userid = #{userid}")
    void deleteConsumer(int userid);
}
