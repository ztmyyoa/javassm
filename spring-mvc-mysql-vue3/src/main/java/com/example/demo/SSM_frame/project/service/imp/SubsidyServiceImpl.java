package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.OrderMapper;
import com.example.demo.SSM_frame.project.map.SubsidyMapper;
import com.example.demo.SSM_frame.project.map.UserMapper;
import com.example.demo.SSM_frame.project.pojo.Subsidy;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.SubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubsidyServiceImpl implements SubsidyService {

    @Autowired
    private SubsidyMapper subsidyMapper;
    @Autowired
    private UserMapper UserMapper;

    @Override
    public int applySubsidy(Subsidy subsidy) {
        return subsidyMapper.insertSubsidy(subsidy);
    }

    @Override
    public void insertsubsidy(Subsidy subsidy) {
        subsidyMapper.insertSubsidy(subsidy);
    }

    @Override
    public void insertsubsidysign(Subsidy subsidy) {
        subsidyMapper.insertSubsidysign(subsidy);
    }

    @Override
    public List<Subsidy> getSubsidiesByFarmerId(String username) {
        user user= UserMapper.getUserByUsername(username);
        int farmerId=user.getUserid();
        return subsidyMapper.findByFarmerId(farmerId);
    }

    @Override
    public Subsidy getSubsidyById(int subid) {
        return subsidyMapper.findById(subid);
    }

    @Override
    public void updateSubsidy(Subsidy subsidy) {
        subsidyMapper.updateSubsidy(subsidy);
    }

    @Override
    public int updateSubsidyStatus(int subid, String status) {
        return subsidyMapper.updateStatus(subid, status);
    }

    @Override
    public List<Subsidy> getAllSubsidies() {
        return subsidyMapper.getAllSubsidies();
    }

    @Override
    public List<Subsidy> getAllSubsidiessign() {
        return subsidyMapper.getSubsidiessign();
    }


    @Override
    public int deleteSubsidy(int subid) {
        return subsidyMapper.deleteSubsidy(subid);
    }
}
