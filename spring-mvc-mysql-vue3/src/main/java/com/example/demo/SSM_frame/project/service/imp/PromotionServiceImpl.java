package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.PromotionMapper;
import com.example.demo.SSM_frame.project.map.UserMapper;
import com.example.demo.SSM_frame.project.pojo.promotion;
import com.example.demo.SSM_frame.project.pojo.user;
import com.example.demo.SSM_frame.project.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionMapper promotionMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public promotion getPromotionById(int promotionid) {
        return promotionMapper.selectPromotionById(promotionid);
    }

    @Override
    public List<promotion> getAllPromotions() {
        return promotionMapper.selectAllPromotions();
    }

    @Override
    public void addPromotion(promotion promotion) {
        promotionMapper.insertPromotion(promotion);
    }

    @Override
    public void addFAPromotion(promotion promotion) {
        promotionMapper.insertfarmerPromotion(promotion);
    }

    @Override
    public void updatePromotion(promotion promotion) {
        promotionMapper.updatePromotion(promotion);
    }

    @Override
    public void deletePromotion(int promotionid) {
        promotionMapper.deletePromotion(promotionid);
    }

    @Override
    public List<promotion> getFarmerpro(String username) {
        user user=userMapper.getUserByUsername(username);
        int farmerid=user.getUserid();
     return   promotionMapper.getFarmerpro(farmerid);
    }
}
