package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.map.CartMapper;
import com.example.demo.SSM_frame.project.map.OrderMapper;
import com.example.demo.SSM_frame.project.pojo.*;
import com.example.demo.SSM_frame.project.pojo.cart;
import com.example.demo.SSM_frame.project.service.CartService;
import com.example.demo.SSM_frame.project.service.ProductService;
import com.example.demo.SSM_frame.project.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:5173")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OssUtil ossUtil;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductService productService;


    @GetMapping("/{cartid}")
    public ResponseEntity<cart> getCartById(@PathVariable int cartid) {
        cart cart = cartService.getCartById(cartid);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/all")
    public ResponseEntity<List<cart>> getAllCarts() {
        List<cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @PostMapping("/add")
    public ResponseEntity<cart> addCart(@RequestBody cart cart) {
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @PostMapping("/update")
    public void updateQuantity(@RequestBody Map<String, Object> map) {
        int cartid = (int) map.get("cartid");
        int quantity = (int) map.get("quantity");
        double totalprice = (double) map.get("totalprice");
        cartService.updateCartQuantity(cartid, quantity,totalprice);
    }

    @DeleteMapping("/delete")
    public void deleteCartItem(@RequestParam int cartid) {
        cartService.deleteCartItem(cartid);
    }
    @GetMapping("/getbyconsumerid")
    public ResponseEntity<List<CartItemDTO>> getOrdersByConsumerId(@RequestParam String username) {
        List<CartItemDTO> cart = cartService.getcartByConsumerId(username);
        for (CartItemDTO cartItemDTO : cart){
            if(cartItemDTO.getImage()!=null && !cartItemDTO.getImage().isEmpty()){
                String signedUrl = ossUtil.generatePrivateImageUrl(
                        cartItemDTO.getImage(),
                        36000
                );
                cartItemDTO.setImage(signedUrl);
            }
        }
        if (cart.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.ok(cart);
    }
    @DeleteMapping("/clear")
    public void clearCart(@RequestParam String username) {
        cartService.clearCartByUsername(username);
    }
    @PostMapping("/submit")
    public Result submitOrder(@RequestBody List<orders> ordersList) {
        int count = 0;
        for (orders o : ordersList) {
            product product = productService.getProductById(o.getProductid());
            o.setOrderstatus(orders.orderStatus.待支付);
            o.setFarmerid(product.getFarmerId());
            o.setOrderTime(new Timestamp(System.currentTimeMillis()));
            o.setOrderAmount(o.getQuantity() * product.getPrice());
            product.setStock(product.getStock() - o.getQuantity());
            o.setLogisticsInfo("待发货");
            orderMapper.insertOrder(o);
            productService.updateProduct(product);
            count++;
        }
        return Result.success("成功提交 " + count + " 个订单！");
    }
}
