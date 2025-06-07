package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.ConsumerMapper;
import com.example.demo.SSM_frame.project.map.UserMapper;
import com.example.demo.SSM_frame.project.pojo.consumer;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerMapper consumerMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public consumer getConsumerById(String username) {
        user user = userMapper.getUserByUsername(username);
        int userId = user.getUserid();
        return consumerMapper.selectConsumerById(userId);
    }

    @Override
    public List<consumer> getAllConsumers() {
        return consumerMapper.selectAllConsumers();
    }

    @Override
    public void updateConsumer(consumer consumer) {
        user user = new user();
        user.setUserid(consumer.getUserid());
        user.setName(consumer.getName());
        user.setAvatar(consumer.getAvatar());
        user.setUsername(consumer.getUsername());
        user.setPhone(consumer.getPhone());
        user.setEmail(consumer.getEmail());
        userMapper.updatefarmer(user);
        consumerMapper.updateConsumer(consumer);
    }

    @Override
    public void deleteConsumer(int userId) {
        System.out.println("即将删除用户：" + userId);  // 添加日志
        consumerMapper.deleteConsumer(userId);
        int deleted = userMapper.deleteUser(userId);
        System.out.println("删除 user 表结果：" + deleted);
    }

    @Override
    public void addConsumer(consumer consumer) {
        user user = new user();
        user.setName(consumer.getName());
        user.setUsername(consumer.getUsername());
        user.setPassword("123");
        user.setUsertype(user.usertype.消费者);
        user.regtime = new Timestamp(System.currentTimeMillis());
        user.setRestatus(1);
        userMapper.insertUser(user);
        user user1 =userMapper.getUserByUsername(consumer.getUsername());
        consumer.setUserid(user1.getUserid());
        consumerMapper.insertConsumer(consumer);
    }
}
