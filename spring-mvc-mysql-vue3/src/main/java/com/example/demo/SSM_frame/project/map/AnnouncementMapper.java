package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.Announcement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
    @Select("SELECT * FROM announcement WHERE aid = #{aid}")
    Announcement selectAnnouncementById(int aid);

    @Select("SELECT * FROM announcement")
    List<Announcement> selectAllAnnouncements();

    @Insert("INSERT INTO announcement (atitle, acontent, publishtime) VALUES (#{atitle}, #{acontent}, #{publishtime})")
    void insertAnnouncement(Announcement announcement);

    @Update("UPDATE announcement SET atitle = #{atitle}, acontent = #{acontent}, publishtime = #{publishtime} WHERE aid = #{aid}")
    void updateAnnouncement(Announcement announcement);

    @Delete("DELETE FROM announcement WHERE aid = #{aid}")
    void deleteAnnouncement(int aid);
}
