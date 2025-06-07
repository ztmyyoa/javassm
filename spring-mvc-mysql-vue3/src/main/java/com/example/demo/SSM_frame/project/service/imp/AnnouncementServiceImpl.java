package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.AnnouncementMapper;
import com.example.demo.SSM_frame.project.pojo.Announcement;
import com.example.demo.SSM_frame.project.service.AnnouncementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public Announcement getAnnouncementById(int aid) {
        return announcementMapper.selectAnnouncementById(aid);
    }

    @Override
    public List<Announcement> getAllAnnouncement() {
        return announcementMapper.selectAllAnnouncements();
    }

    @Override
    public void addAnnouncement(Announcement announcement) {
        announcement.setPublishtime(new Timestamp(System.currentTimeMillis()));
        announcementMapper.insertAnnouncement(announcement);
    }

    @Override
    public void updateAnnouncement(Announcement announcement) {
        announcementMapper.updateAnnouncement(announcement);
    }

    @Override
    public void deleteAnnouncement(int aid) {
        announcementMapper.deleteAnnouncement(aid);
    }
}
