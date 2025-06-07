package com.example.demo.SSM_frame.project.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SalesReport {
    private int reportid;
    private int farmerid; // 外键，关联农户
    private String reportcontent;
    private java.util.Date reporttime;
    private String tostring() {
        return "SalesReport{" +
                "reportId=" + reportid +
                ", farmerId=" + farmerid +
                ", reportContent='" + reportcontent + '\'' +
                ", reportTime=" + reporttime +
                '}';
    }
}
