package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.ReviewMapper;
import com.example.demo.SSM_frame.project.map.UserMapper;
import com.example.demo.SSM_frame.project.pojo.review;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public review getReviewById(int reviewid) {
        return reviewMapper.selectReviewById(reviewid);
    }

    @Override
    public List<review> getAllReviews() {
        return reviewMapper.selectAllReviews();
    }

    @Override
    public List<review> getReviewsByProductId(int productid) {
        return reviewMapper.selectReviewsByProductId(productid);
    }

    @Override
    public List<review> getReviewsByConsumerId(String username) {
        user user=userMapper.getUserByUsername(username);
        int consumerid=user.getUserid();
        return reviewMapper.selectReviewsByConsumerId(consumerid);
    }

    @Override
    public List<review> getReviewsByFarmerId(String username) {
        user user=userMapper.getUserByUsername(username);
         int farmerid=user.getUserid();
        return reviewMapper.selectReviewsByFarmerId(farmerid);
    }

    @Override
    public void addReview(review review) {
        review.setReviewtime(new Timestamp(System.currentTimeMillis()));
        reviewMapper.insertReview(review);
    }

    @Override
    public void updateReview(review review) {
        reviewMapper.updateReview(review);
    }

    @Override
    public void deleteReview(int reviewid) {
        reviewMapper.deleteReview(reviewid);
    }
}
