package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.admin;

import java.util.List;

public interface AdminService {
    void addAdmin(admin admin);
    admin getAdminById(int userid);
    List<admin> getAllAdmins();
    void updateAdmin(admin admin);
    void deleteAdmin(int userid);

}
