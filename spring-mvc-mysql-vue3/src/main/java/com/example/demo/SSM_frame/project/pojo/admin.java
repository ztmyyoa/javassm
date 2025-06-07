package com.example.demo.SSM_frame.project.pojo;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class admin extends user {
    // 管理员类可以添加与管理员相关的属性
    private String adminkey;
    public String tostring(){
        return "admin{"+
                "userid="+userid+
                ",name='"+name+'\''+
                ",username='"+username+'\''+
                ",password='"+password+'\''+
                ",usertype="+usertype+
                ",email='"+email+'\''+
                ",phone='"+phone+'\''+
                ",regtime="+regtime+'\''+
                ",avater"+ avatar+
                ",adminkey='"+adminkey+'\''+
                '}';
    }

}
