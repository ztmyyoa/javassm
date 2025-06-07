package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.admin;
import com.example.demo.SSM_frame.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/{userId}")
    public ResponseEntity<admin> getAdminById(@PathVariable int userId) {
        admin admin = adminService.getAdminById(userId);
        return ResponseEntity.ok(admin);
    }

    @GetMapping("/all")
    public ResponseEntity<List<admin>> getAllAdmins() {
        List<admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @PostMapping("/add")
    public ResponseEntity<admin> addAdmin(@RequestBody admin admin) {
        adminService.addAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(admin);
    }

    @PutMapping("/update")
    public ResponseEntity<admin> updateAdmin(@RequestBody admin admin) {
        adminService.updateAdmin(admin);
        return ResponseEntity.ok(admin);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable int userId) {
        adminService.deleteAdmin(userId);
        return ResponseEntity.noContent().build();
    }
}
