package com.example.demo.SSM_frame.project.pojo;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Subsidy {
    private int subid;
    private String title;
    private double amount;
    private String content;
    private Substatus substatus;
    private int farmerid;
    private enum Substatus{
        待审核,
        已通过,
        已拒绝
    }

    public String toString(){
        return "Subsidy{"+
                "subid="+subid+
                ",amount="+amount+
                ",content='"+content+'\''+
                ",status='"+substatus+'\''+
                ",farmerId="+farmerid+
                '}';
    }


}
