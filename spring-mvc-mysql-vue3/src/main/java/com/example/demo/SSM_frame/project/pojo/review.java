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
public class review {
    private int reviewid;
    private int consumerid; // 外键，关联用户
    private int productid; // 外键，关联产品
    private int farmerid;
    private String reviewcontent;
    private java.util.Date reviewtime;
    private int rating; // 1-5
    private String reply;
    public String tostring() {
        return "review{" +
                "reviewid=" + reviewid +
                ", userid=" + consumerid +
                ", productid=" + productid +
                ", farmerid=" + farmerid +
                ", reviewcontent='" + reviewcontent + '\'' +
                ", reviewtime=" + reviewtime +
                ", rating=" + rating +
                ",  reply='" + reply + '\'' +
                '}';
    }
}
