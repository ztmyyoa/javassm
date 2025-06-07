package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.consumer;
import com.example.demo.SSM_frame.project.service.ConsumerService;
import com.example.demo.SSM_frame.project.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer")
@CrossOrigin(origins = "http://localhost:5173")
public class ConsumerController {

    @Autowired
    private OssUtil ossUtil;
    @Autowired
    private ConsumerService consumerService;


    @GetMapping("/getconsumerprofile")
    public ResponseEntity<consumer> getConsumerProfile(@RequestParam String username) {
        consumer consumer = consumerService.getConsumerById(username);
        if (consumer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(consumer);
    }
    @GetMapping("/all")
    public ResponseEntity<List<consumer>> getAllConsumers() {
        List<consumer> consumers = consumerService.getAllConsumers();
        for(consumer consumer : consumers){
            if (consumer.getAvatar() != null && !consumer.getAvatar().isEmpty()) {
                String signedUrl = ossUtil.generatePrivateImageUrl(
                        consumer.getAvatar(),
                        36000 // 有效时间：1小时
                );
                consumer.setAvatar(signedUrl);
            }
        }
        if (consumers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(consumers);
    }

    @PostMapping("/add")
    public ResponseEntity<consumer> addConsumer(@RequestBody consumer consumer) {
        if (consumer == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        consumerService.addConsumer(consumer);
        return ResponseEntity.status(HttpStatus.CREATED).body(consumer);
    }

    @PutMapping("/update")
    public ResponseEntity<consumer> updateConsumer(@RequestBody consumer consumer) {
        if (consumer == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } consumerService.updateConsumer(consumer);
        return ResponseEntity.ok(consumer);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable int userId) {
        if (userId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        consumerService.deleteConsumer(userId);
        return ResponseEntity.noContent().build();
    }
}
