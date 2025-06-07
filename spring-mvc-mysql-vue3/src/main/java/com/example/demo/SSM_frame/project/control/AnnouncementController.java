package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.Announcement;
import com.example.demo.SSM_frame.project.pojo.consumer;
import com.example.demo.SSM_frame.project.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/a")
@CrossOrigin(origins = "http://localhost:5173")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/{aid}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable int aid) {
        Announcement announcement = announcementService.getAnnouncementById(aid);
        return ResponseEntity.ok(announcement);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncement();
        if (announcements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(announcements);
    }

    @PostMapping("/add")
    public ResponseEntity<Announcement> addAnnouncement(@RequestBody Announcement announcement) {
        if (announcement == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        announcementService.addAnnouncement(announcement);
        return ResponseEntity.status(HttpStatus.CREATED).body(announcement);
    }

    @PutMapping("/update")
    public ResponseEntity<Announcement> updateAnnouncement(@RequestBody Announcement announcement) {
        if (announcement == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        announcementService.updateAnnouncement(announcement);
        return ResponseEntity.ok(announcement);
    }

    @DeleteMapping("/delete/{aid}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable int aid) {
        if (aid <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        announcementService.deleteAnnouncement(aid);
        return ResponseEntity.noContent().build();
    }
}
