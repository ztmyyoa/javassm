package com.example.demo.SSM_frame.project.pojo;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class cart {
    private int cartid;
    private int consumerid; // 外键，关联消费者
    private int productid; // 外键，关联产品
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalprice;
    private String image;
    public String tostring() {
        return "cart{" +
                "cartid=" + cartid +
                ", consumerid=" + consumerid +
                ", productid=" + productid +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalprice=" + totalprice +
                ", image='" + image + '\'' +
                '}';
    }
}
