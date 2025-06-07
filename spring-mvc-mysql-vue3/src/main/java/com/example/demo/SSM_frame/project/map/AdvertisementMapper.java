package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.Advertisement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdvertisementMapper {

    @Insert("INSERT INTO advertisements (title, adcontent, image_url, link_url, start_time, end_time, status) " +
            "VALUES (#{title}, #{adcontent}, #{imageUrl}, #{linkUrl}, #{startTime}, #{endTime}, #{status})")
    void insertAdvertisement(Advertisement advertisement);

    @Update("UPDATE advertisements SET title = #{title}, adcontent = #{adcontent}, image_url = #{imageUrl}, " +
            "link_url = #{linkUrl}, start_time = #{startTime}, end_time = #{endTime}, status = #{status} " +
            "WHERE advertid = #{advertid}")
    void updateAdvertisement(Advertisement advertisement);

    @Delete("DELETE FROM advertisements WHERE advertid = #{advertid}")
    void deleteAdvertisement(Integer advertid);

    @Select("SELECT * FROM advertisements WHERE advertid = #{advertid}")
    Advertisement getAdvertisementById(Integer advertid);

    @Select("SELECT * FROM advertisements")
    List<Advertisement> getAllAdvertisements();

    @Select("SELECT * FROM advertisements WHERE status = 1")
    List<Advertisement> getActiveAdvertisements();
}
