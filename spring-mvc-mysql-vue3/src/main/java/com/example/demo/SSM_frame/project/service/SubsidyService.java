package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.Subsidy;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface SubsidyService {
    int applySubsidy(Subsidy subsidy);
    void insertsubsidy(Subsidy subsidy);
    void insertsubsidysign(Subsidy subsidy);
    List<Subsidy> getSubsidiesByFarmerId(String username);
    Subsidy getSubsidyById(int subid);
    void updateSubsidy(Subsidy subsidy);
    int updateSubsidyStatus(int subid, String status);
    List<Subsidy> getAllSubsidies();

    List<Subsidy> getAllSubsidiessign();

    int deleteSubsidy(int subid);
}
