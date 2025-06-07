package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.QuarterlyOrderCount;
import com.example.demo.SSM_frame.project.pojo.orders;
import com.example.demo.SSM_frame.project.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderid}")
    public ResponseEntity<orders> getOrderById(@PathVariable int orderid) {
        orders order = orderService.getOrderById(orderid);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/all")
    public ResponseEntity<List<orders>> getAllOrders() {
        List<orders> orders = orderService.getAllOrders();
        if (orders.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(orders);
    }

        @PostMapping("/add")
    public ResponseEntity<orders> addOrder(@RequestBody orders order) {
       if(order == null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }
        orderService.addOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("/update")
    public ResponseEntity<orders> updateOrder(@RequestBody orders order) {
       if (order == null){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }
        orderService.updateOrder(order);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/delete/{orderid}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderid) {
       if(orderid <= 0){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
       }
        orderService.deleteOrder(orderid);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getquantity")
    public List<Map<String, Object>> getProductSales() {
        return orderService.getProductSales();
    }
    @GetMapping("/quarterly")
    public List<QuarterlyOrderCount> getQuarterlyOrderCount() {
        List<QuarterlyOrderCount> countList = orderService.getQuarterlyOrderCount();
        if (countList == null || countList.isEmpty()) {
            System.out.println("返回的季度订单数据为空");
        } else {
            System.out.println("返回的季度订单数据: " + countList);
        }
        return countList;
    }
    @GetMapping("/getbyfarmerid")
    public ResponseEntity<List<orders>> getOrdersByFarmerId(@RequestParam String username) {
        List<orders> orders = orderService.getOrdersByFarmerId(username);
        if (orders.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/getbyconsumerid")
    public ResponseEntity<List<orders>> getOrdersByConsumerId(@RequestParam String username) {
        List<orders> orders = orderService.getOrdersByConsumerId(username);
        if (orders.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(orders);
    }

}
