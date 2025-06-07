package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.farmer;

import java.util.List;

public interface FarmerService {
    farmer getFarmerById(String username);
    List<farmer> getAllFarmers();
    void updateFarmer(farmer farmer);
    void deleteFarmer(int userId);
    void addFarmer(farmer farmer);
}
