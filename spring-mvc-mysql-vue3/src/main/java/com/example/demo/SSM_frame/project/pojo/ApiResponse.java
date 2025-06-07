package com.example.demo.SSM_frame.project.pojo;

public class ApiResponse {
    private boolean success;
    private String message;

    // 构造器
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getter 和 Setter 方法
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
