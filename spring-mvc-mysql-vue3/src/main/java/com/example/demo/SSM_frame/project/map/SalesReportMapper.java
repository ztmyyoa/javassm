package com.example.demo.SSM_frame.project.map;

import com.example.demo.SSM_frame.project.pojo.SalesReport;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SalesReportMapper {
    @Select("SELECT * FROM SalesReport WHERE reportid = #{reportid}")
    SalesReport selectSalesReportById(int reportid);

    @Select("SELECT * FROM SalesReport")
    List<SalesReport> selectAllSalesReports();

    @Insert("INSERT INTO SalesReport (farmerid, reportcontent, reporttime) VALUES (#{farmerid}, #{reportcontent}, #{reporttime})")
    void insertSalesReport(SalesReport salesReport);

    @Update("UPDATE SalesReport SET farmerid = #{farmerid}, reportcontent = #{reportcontent}, reporttime = #{reporttime} WHERE reportid = #{reportid}")
    void updateSalesReport(SalesReport salesReport);

    @Delete("DELETE FROM SalesReport WHERE reportid = #{reportid}")
    void deleteSalesReport(int reportid);
}
