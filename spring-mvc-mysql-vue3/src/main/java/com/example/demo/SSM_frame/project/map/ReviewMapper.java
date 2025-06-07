package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.review;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Select("SELECT * FROM review WHERE reviewid = #{reviewid}")
    review selectReviewById(int reviewid);

    @Select("SELECT * FROM review")
    List<review> selectAllReviews();

    @Select("SELECT * FROM review WHERE productid = #{productid}")
    List<review> selectReviewsByProductId(int productid);

    @Select("SELECT * FROM review WHERE consumerid = #{consumerid}")
    List<review> selectReviewsByConsumerId(int consumerid);
    @Select("SELECT * FROM review WHERE farmerid = #{farmerid}")
    List<review> selectReviewsByFarmerId(int farmerid);

    @Insert("INSERT INTO review (consumerid, productid, farmerid,reviewcontent, reviewtime, rating,reply) VALUES (#{consumerid}, #{productid},#{farmerid}, #{reviewcontent}, #{reviewtime}, #{rating},#{reply})")
    void insertReview(review review);

    @Update("UPDATE review SET consumerid = #{consumerid}, productid = #{productid}, farmerid=#{farmerid},reviewcontent = #{reviewcontent}, reviewtime = #{reviewtime}, rating = #{rating} ,reply=#{reply}WHERE reviewid = #{reviewid}")
    void updateReview(review review);

    @Delete("DELETE FROM review WHERE reviewid = #{reviewid}")
    void deleteReview(int reviewid);
}
