package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.review;
import com.example.demo.SSM_frame.project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{reviewid}")
    public ResponseEntity<review> getReviewById(@PathVariable int reviewid) {
        review review = reviewService.getReviewById(reviewid);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/all")
    public ResponseEntity<List<review>> getAllReviews() {
        List<review> reviews = reviewService.getAllReviews();
       if(reviews.isEmpty()){
           return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
       }
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/add")
    public ResponseEntity<review> addReview(@RequestBody review review) {
        if(review == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        reviewService.addReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @PutMapping("/update")
    public ResponseEntity<review> updateReview(@RequestBody review review) {
        review.setReviewtime(new Timestamp(System.currentTimeMillis()));
      if(review == null){
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
      }
        reviewService.updateReview(review);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/delete/{reviewid}")
    public ResponseEntity<Void> deleteReview(@PathVariable int reviewid) {
       if(reviewid == 0){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }
        reviewService.deleteReview(reviewid);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getfarmerreview")
    public ResponseEntity<List<review>> getReviewsByFarmerId(@RequestParam String username) {
        List<review> reviews = reviewService.getReviewsByFarmerId(username);
        if(reviews.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/getconsumerreview")
    public ResponseEntity<List<review>> getReviewsByconsumerId(@RequestParam String username) {
        List<review> reviews = reviewService.getReviewsByConsumerId(username);
        if (reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(reviews);
    }
}
