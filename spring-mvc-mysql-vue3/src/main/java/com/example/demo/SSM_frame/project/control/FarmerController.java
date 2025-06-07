package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.farmer;
import com.example.demo.SSM_frame.project.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.SSM_frame.project.utils.OssUtil;
import java.util.List;

@RestController
@RequestMapping("/farmer")
@CrossOrigin(origins = "http://localhost:5173")
public class FarmerController {

    @Autowired
    private OssUtil ossUtil;
    @Autowired
    private FarmerService farmerService;

    // 根据 userId 获取农户信息（包括 user 表和 farmer 表的联接数据）


    @GetMapping("/getfamerprofile")
    public ResponseEntity<farmer> getFarmerProfile(@RequestParam String username) {
        farmer farmer = farmerService.getFarmerById(username);
        if (farmer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(farmer);
    }
    @GetMapping("/all")
    public ResponseEntity<List<farmer>> getAllFarmers() {
        List<farmer> farmers = farmerService.getAllFarmers();
        for (farmer farmer : farmers) {
            if (farmer.getAvatar() != null && !farmer.getAvatar().isEmpty()) {
                String signedUrl = ossUtil.generatePrivateImageUrl(
                        farmer.getAvatar(), // objectName
                        36000
                );
                farmer.setAvatar(signedUrl); // 用签名 URL 替换
            }
        }
        if (farmers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(farmers);
    }
    // 新增农户
    @PostMapping("/add")
    public ResponseEntity<farmer> addFarmer(@RequestBody farmer farmer) {
        // 确保农户数据有效
        if (farmer == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        farmerService.addFarmer(farmer);
        return ResponseEntity.status(HttpStatus.CREATED).body(farmer);
    }

    // 更新农户信息
    @PutMapping("/update")
    public ResponseEntity<farmer> updateFarmer(@RequestBody farmer farmer) {
        if (farmer == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        farmerService.updateFarmer(farmer);
        return ResponseEntity.ok(farmer);
    }

    // 删除农户
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteFarmer(@PathVariable int userId) {
        if (userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        farmerService.deleteFarmer(userId);
        return ResponseEntity.noContent().build();
    }
}
