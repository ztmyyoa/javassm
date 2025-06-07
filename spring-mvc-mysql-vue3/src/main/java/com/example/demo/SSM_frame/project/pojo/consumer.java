package com.example.demo.SSM_frame.project.pojo;

import lombok.*;




@AllArgsConstructor
@NoArgsConstructor
@Getter

public class consumer extends user{
    private String address;
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
                '}';
    }
}
