package com.example.demo.SSM_frame.project.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Advertisement {
    private Integer advertid;
    private String title;
    private String adcontent;
    private String imageUrl;
    private String linkUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private Integer status;
    public String  tostring(){
        return "advertisement{"+
                "advertid="+advertid+
                ",title='"+title+'\''+
                ",position='"+adcontent+'\''+
                ",linkUrl='"+linkUrl+'\''+
                ",startTime="+startTime+'\''+
                ",endTime="+endTime+'\''+
                ",status="+status+'\''+
                '}' ;
    }
}
