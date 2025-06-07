package com.example.demo.SSM_frame.project.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Announcement {
    private int aid;
    private String atitle;
    private String acontent;
    private java.util.Date publishtime;
    private String tostring(){
        return "Announcement{" +
                "announcementId=" + aid +
                ", announcementTitle='" + atitle + '\'' +
                ", announcementContent='" + acontent + '\'' +
                ", publishTime=" + publishtime +
                '}';
    }
}
