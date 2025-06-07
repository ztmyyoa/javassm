package com.example.demo.SSM_frame.project.pojo;

import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter

public class farmer extends user {
    // 农户类可以添加与农户相关的属性
    private String address;
    private BigDecimal subsidys;

    public String tostring()
    {
        return "consumer{"+
                "userid="+userid+
                ",name='"+name+'\''+
                ",username='"+username+'\''+
                ",password='"+password+'\''+
                ",usertype="+usertype+
                ",email='"+email+'\''+
                ",phone='"+phone+'\''+
                ",regtime="+regtime+'\''+
                ",avater"+ avatar+
                ",address='"+address+'\''+
                ",subsidys="+subsidys+'\''+
                '}';

    }
}
