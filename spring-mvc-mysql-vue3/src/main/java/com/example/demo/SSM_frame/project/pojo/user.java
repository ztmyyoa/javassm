package com.example.demo.SSM_frame.project.pojo;

import lombok.*;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class user {
    public int userid;
    public String name;
    public String username;
    public String password;
    public Usertype usertype;
    public String email;
    public String phone;
    public Timestamp regtime;
    public String avatar;
    public int restatus;//审核状态:0为待审核，1为审核通过，2为审核不通过
    public enum Usertype {
        消费者,
        农户,
        管理员
    }
    public String toString(){
       return "user{"+
               "userid="+userid+
               ",name='"+name+'\''+
               ",username='"+username+'\''+
               ",password='"+password+'\''+
               ",usertype="+usertype+
               ",email='"+email+'\''+
               ",phone='"+phone+'\''+
               ",regtime="+regtime+'\''+
               ",avater"+ avatar+
               ",restatus"+restatus+
               '}';
    }

}
