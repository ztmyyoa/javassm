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
public class orders {
    private int orderid;
    private int consumerid; // 外键，关联消费者
    private int productid; // 外键，关联产品
    private int farmerid;
    private orderStatus orderstatus; //
    private double orderAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    private String logisticsInfo;
    private double quantity;
    public enum orderStatus {
        待支付,
        待发货,
        待收货,
        已完成,
        已取消
    }
    public String tostring() {
        return "order{" +
                "orderid=" + orderid +
                ", consumerid=" + consumerid +
                ", productid=" + productid +
                ", farmerid=" + farmerid +
                ", orderstatus=" + orderstatus +
                ", orderAmount=" + orderAmount +
                ", orderTime=" + orderTime +
                ", logisticsInfo='" + logisticsInfo + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
