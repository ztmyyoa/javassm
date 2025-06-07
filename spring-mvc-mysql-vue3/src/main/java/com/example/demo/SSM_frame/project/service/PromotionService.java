package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.promotion;

import java.util.List;

public interface PromotionService {
    promotion getPromotionById(int promotionid);
    List<promotion> getAllPromotions();
    void addPromotion(promotion promotion);
    void addFAPromotion(promotion promotion);
    void updatePromotion(promotion promotion);
    void deletePromotion(int promotionid);
    List<promotion> getFarmerpro(String username);
}
