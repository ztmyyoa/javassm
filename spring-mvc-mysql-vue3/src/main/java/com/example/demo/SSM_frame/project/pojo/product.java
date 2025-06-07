package com.example.demo.SSM_frame.project.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class product {
    private int productid;
    private String productname;
    private double price;
    private double stock;
    private String description;
    private int farmerId;
    private String image;
    private String type;
    public String tostring() {
        return "product{" +
                "productid=" + productid +
                ", productname='" + productname + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                ", farmerId=" + farmerId +
                ", image=" + image +
                ", type=" + type +
                '}';
    }
}
