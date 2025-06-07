package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.ApiResponse;
import com.example.demo.SSM_frame.project.pojo.Result;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody user loginData) {
        String username = loginData.getUsername();
        String password = loginData.getPassword();
        String role = loginData.getUsertype().toString();
        System.out.println(loginData);
        user user = selectByUsername(username); // 你需要有此方法
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户不存在");
        }

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密码错误");
        }

        if (!user.getUsertype().name().equals(role)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户类型不匹配");
        }

        return ResponseEntity.ok(user); // 登录成功，返回用户信息
    }

    @GetMapping("/{userId}")
    public ResponseEntity<user> getUserById(@PathVariable int userId) {
        user user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<user>> getAllUsers() {
        List<user> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody user user) {
        user existingUser = userService.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("用户名已存在");
        }else{
            userService.insertUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<user> updateUser(@RequestBody user user) {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username")
    public user selectByUsername(@RequestParam
                                     String username) {

        return userService.getUserByUsername(username);
    }
    @PostMapping("/approve")
    public ResponseEntity<String> approveUser(@RequestBody Map<String, Object> payload) {
        Number userIdNum = (Number) payload.get("userid");
        if (userIdNum == null) {
            return ResponseEntity.badRequest().body("user_id 为空");
        }
        int userId = userIdNum.intValue();
        userService.auditUser(userId, 1);
        return ResponseEntity.ok("审核通过");
    }


    @PostMapping("/reject")
    public ResponseEntity<String> rejectUser(@RequestBody Map<String, Object> payload) {
        Integer userId = (Integer) payload.get("userid");
        userService.auditUser(userId, 2); // 2 表示审核拒绝
        return ResponseEntity.ok("审核已拒绝");
    }
    @GetMapping("/getregister")
    public ResponseEntity<Result> getPendingUsers() {
        List<user> audituser = userService.getUsersByRestatus(0);
        return ResponseEntity.ok(Result.success(audituser));
    }
    @GetMapping("/getbytype")
    public ResponseEntity<Map<String, Integer>>getUsersByType() {
        try {
            Map<String, Integer> result = userService.getUsersByType();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
