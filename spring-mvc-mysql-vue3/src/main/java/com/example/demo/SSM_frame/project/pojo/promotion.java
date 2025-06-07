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
public class promotion {
    private int promotionid;
    private String promotionname;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    private int farmerid; //
    public String tostring() {
        return "promotion{" +
                "promotionid=" + promotionid +
                ", promotionname='" + promotionname + '\'' +
                ", description='" + description + '\'' +
                ", starttime=" + startTime +
                ", endtime=" + endTime +
                ", productid=" + farmerid +
                '}';
    }
}
