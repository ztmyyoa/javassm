package com.example.demo.SSM_frame.project.control;


import com.example.demo.SSM_frame.project.pojo.Advertisement;

import com.example.demo.SSM_frame.project.pojo.consumer;
import com.example.demo.SSM_frame.project.service.AdvertisementService;
import com.example.demo.SSM_frame.project.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ad")
@CrossOrigin(origins = "http://localhost:5173")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private OssUtil ossUtil;


    // 更新广告
    @PutMapping("/{advertid}")
    public String updateAdvertisement(@PathVariable Integer advertid, @RequestBody Advertisement advertisement) {
        advertisement.setAdvertid(advertid);
        advertisementService.updateAdvertisement(advertisement);
        return "广告更新成功";
    }

    // 删除广告
    @DeleteMapping("/delete/{aid}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable int aid) {
        if (aid <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        advertisementService.deleteAdvertisement(aid);
        return ResponseEntity.noContent().build();
    }

    // 获取所有广告
    @GetMapping("/all")
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementService.getAllAdvertisements();
    for(Advertisement advertisement : advertisements){
        if (advertisement.getImageUrl() != null && !advertisement.getImageUrl().isEmpty()) {
            String signedUrl = ossUtil.generatePrivateImageUrl(
                    advertisement.getImageUrl(),
                    36000 // 有效时间：1小时
            );
            advertisement.setImageUrl(signedUrl);

        }
    }
        if (advertisements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(advertisements);

    }

    // 获取单个广告
    @GetMapping("/{advertid}")
    public Advertisement getAdvertisementById(@PathVariable Integer advertid) {
        return advertisementService.getAdvertisementById(advertid);
    }
    // 添加广告
    @PostMapping("/add")
    public ResponseEntity<Advertisement> addAdvertisement(@RequestBody Advertisement advertisement) {
        if (advertisement == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        advertisementService.addAdvertisement(advertisement);
        return ResponseEntity.status(HttpStatus.CREATED).body(advertisement);
    }
    @PutMapping("/update")
    public ResponseEntity<Advertisement> updateAdvertisement(@RequestBody Advertisement advertisement) {
        if (advertisement == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        advertisementService.updateAdvertisement(advertisement);
        return ResponseEntity.ok(advertisement);
    }

    // 获取所有显示中的广告
    @GetMapping("/active")
    public List<Advertisement> getActiveAdvertisements() {
        return advertisementService.getActiveAdvertisements();
    }
}
