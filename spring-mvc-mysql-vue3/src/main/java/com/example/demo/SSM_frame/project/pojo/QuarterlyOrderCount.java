package com.example.demo.SSM_frame.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuarterlyOrderCount {
    private int year;           // 年份
    private int quarter;        // 季度
    private double tquantity; // 该季度的订单数量合计
}
