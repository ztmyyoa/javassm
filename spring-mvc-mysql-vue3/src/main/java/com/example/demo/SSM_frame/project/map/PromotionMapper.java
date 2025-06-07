package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.promotion;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PromotionMapper {
    @Select("SELECT * FROM promotion WHERE promotionid = #{promotionid}")
    promotion selectPromotionById(int promotionid);

    @Select("SELECT * FROM promotion")
    List<promotion> selectAllPromotions();

    @Insert("INSERT INTO promotionsign (promotionname, description, starttime, endtime, farmerid) VALUES (#{promotionname}, #{description}, #{startTime}, #{endTime}, #{farmerid})")
    void insertfarmerPromotion(promotion promotion);
    @Insert("INSERT INTO promotion (promotionname, description, starttime, endtime) VALUES (#{promotionname}, #{description}, #{startTime}, #{endTime})")
    void insertPromotion(promotion promotion);
    @Update("UPDATE promotion SET promotionname = #{promotionname}, description = #{description}, starttime = #{startTime}, endtime = #{endTime}, farmerid = #{farmerid} WHERE promotionid = #{promotionid}")
    void updatePromotion(promotion promotion);

    @Delete("DELETE FROM promotion WHERE promotionid = #{promotionid}")
    void deletePromotion(int promotionid);
    @Select("SELECT * FROM promotionsign WHERE farmerid = #{farmerid}")
    List<promotion> getFarmerpro(int farmerid);
}
