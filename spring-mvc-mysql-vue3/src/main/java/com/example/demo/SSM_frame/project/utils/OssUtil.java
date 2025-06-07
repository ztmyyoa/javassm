package com.example.demo.SSM_frame.project.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;
@Component
public class OssUtil {
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    /**
     * 生成私有 Bucket 的访问签名 URL
     *
     * @param objectName 对象名（如 "avatar/123.jpg"）
     * @param expireInSeconds URL 有效时间（单位：秒）
     * @return 签名 URL
     */
    public String generatePrivateImageUrl(String objectName, int expireInSeconds) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            Date expiration = new Date(System.currentTimeMillis() + expireInSeconds * 1000L);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, objectName);
            request.setExpiration(expiration);
            URL signedUrl = ossClient.generatePresignedUrl(request);
            return signedUrl.toString();  //  已经是完整的 HTTPS URL，不要再拼接 endpoint
        } finally {
            ossClient.shutdown();
        }
    }

}
