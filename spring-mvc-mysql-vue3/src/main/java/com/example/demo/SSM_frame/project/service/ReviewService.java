package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.review;

import java.util.List;

public interface ReviewService {
    review getReviewById(int reviewid);
    List<review> getAllReviews();
    List<review> getReviewsByProductId(int productid);
    List<review> getReviewsByConsumerId(String username);
    List<review> getReviewsByFarmerId(String username);
    void addReview(review review);
    void updateReview(review review);
    void deleteReview(int reviewid);
}
