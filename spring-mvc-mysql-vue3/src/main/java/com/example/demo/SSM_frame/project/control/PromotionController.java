package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.Advertisement;
import com.example.demo.SSM_frame.project.pojo.promotion;
import com.example.demo.SSM_frame.project.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotion")
@CrossOrigin(origins = "http://localhost:5173")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping("/{promotionid}")
    public ResponseEntity<promotion> getPromotionById(@PathVariable int promotionid) {
        promotion promotion = promotionService.getPromotionById(promotionid);
        return ResponseEntity.ok(promotion);
    }

    @GetMapping("/all")
    public ResponseEntity<List<promotion>> getAllPromotions() {
        List<promotion> promotions = promotionService.getAllPromotions();
        if (promotions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(promotions);
    }

    @PostMapping("/add")
    public ResponseEntity<promotion> addPromotion(@RequestBody promotion promotion) {
        if (promotion == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        promotionService.addPromotion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(promotion);
    }

    @PutMapping("/update")
    public ResponseEntity<promotion> updatepromotion(@RequestBody promotion promotions) {
        if (promotions == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        promotionService.updatePromotion(promotions);
        return ResponseEntity.ok(promotions);
    }

    @DeleteMapping("/delete/{promotionid}")
    public ResponseEntity<Void> deletePromotion(@PathVariable int promotionid) {
       if(promotionid<=0){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }
        promotionService.deletePromotion(promotionid);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getfarmerpro")
    public ResponseEntity<List<promotion>> getFarmerPromotions(@RequestParam String username) {
        List<promotion> promotions = promotionService.getFarmerpro(username);
        if (promotions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(promotions);
    }
    @PostMapping("/addfarmerpro")
    public ResponseEntity<promotion> addfarmerPromotion(@RequestBody promotion promotion) {
        if (promotion == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        promotionService.addFAPromotion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(promotion);
    }

}
