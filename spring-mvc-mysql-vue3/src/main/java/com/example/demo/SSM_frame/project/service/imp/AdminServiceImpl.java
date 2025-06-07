package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.AdminMapper;
import com.example.demo.SSM_frame.project.pojo.admin;
import com.example.demo.SSM_frame.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void addAdmin(admin admin) {
        adminMapper.insertAdmin(admin); // 假设insertAdmin是Mapper中定义的方法
    }
    @Override
    public admin getAdminById(int userId) {
        return adminMapper.selectAdminById(userId);
    }

    @Override
    public List<admin> getAllAdmins() {
        return adminMapper.selectAllAdmins();
    }

    @Override
    public void updateAdmin(admin admin) {
        adminMapper.updateAdmin(admin);
    }

    @Override
    public void deleteAdmin(int userId) {
        adminMapper.deleteAdmin(userId);
    }
}
