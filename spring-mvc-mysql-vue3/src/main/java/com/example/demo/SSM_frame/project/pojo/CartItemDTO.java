package com.example.demo.SSM_frame.project.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CartItemDTO {
    private int cartid;
    private int consumerid;
    private int productid;
    private int quantity;
    private String productname;
    private String image;
    private double price;
    private String tostring(){
        return "CartItemDTO{" +
                "consumerid=" + consumerid +
                ", productid=" + productid +
                ", quantity=" + quantity +
                ", productname='" + productname + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}
