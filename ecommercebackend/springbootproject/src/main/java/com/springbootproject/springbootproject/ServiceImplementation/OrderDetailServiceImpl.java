package com.springbootproject.springbootproject.ServiceImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootproject.springbootproject.Entitities.Cart;
import com.springbootproject.springbootproject.Entitities.OrderDetail;
import com.springbootproject.springbootproject.Entitities.OrderInput;
import com.springbootproject.springbootproject.Entitities.OrderProductQuantity;
import com.springbootproject.springbootproject.Entitities.Product;
import com.springbootproject.springbootproject.Entitities.User;
import com.springbootproject.springbootproject.Jwt.JwtAuthenticationFilter;
import com.springbootproject.springbootproject.Repositories.CartRepository;
import com.springbootproject.springbootproject.Repositories.OrderDetailRepository;
import com.springbootproject.springbootproject.Repositories.ProductRepository;
import com.springbootproject.springbootproject.Repositories.UserRepository;
import com.springbootproject.springbootproject.Service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private static final String ORDER_PLACED="Placed";

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<OrderDetail> placeOrder(OrderInput orderInput, boolean isCartCheckout){
        List<OrderProductQuantity> productQuantityList=orderInput.getOrderProductQuantityList();
        List<OrderDetail> orderDetails = new ArrayList<>(); // List to store placed orders
        
        for(OrderProductQuantity o: productQuantityList){
            Product product =productRepository.findById(o.getProductId()).get();
            String curretUser=JwtAuthenticationFilter.CURRENT_USER;
            User user=userRepository.findById(curretUser).get();
             
            OrderDetail orderDetail=new OrderDetail( 
                orderInput.getFullName(),
                orderInput.getFullAddress(),
                orderInput.getContactNumber(),
                orderInput.getAlternateContactNumber(),
                ORDER_PLACED,
                
                product.getProductActualPrice() * o.getQuantity(),
                product,
                user
            );
            orderDetailRepository.save(orderDetail);
            orderDetails.add(orderDetail);

            //empty my cart
            if(isCartCheckout){
                List<Cart> carts=cartRepository.findByUser(user);
                carts.stream().forEach(x-> cartRepository.delete(x));
            }


        }
        return orderDetails;

    }

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public Optional<OrderDetail> getOrderDetailById(Integer orderId) {
        return orderDetailRepository.findById(orderId);
    }

    @Override
    public void deleteOrderDetail(Integer orderId) {
        orderDetailRepository.deleteById(orderId);
    }

    @Override
    public Iterable<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }
}
