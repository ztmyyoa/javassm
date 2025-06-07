package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.Announcement;

import java.util.List;

public interface AnnouncementService {
    Announcement getAnnouncementById(int aid);
    List<Announcement> getAllAnnouncement();
    void addAnnouncement(Announcement announcement);
    void updateAnnouncement(Announcement announcement);
    void deleteAnnouncement(int aid);
}
