package com.springbootproject.springbootproject.ServiceImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootproject.springbootproject.Entitities.OrderDetail;
import com.springbootproject.springbootproject.Entitities.OrderInput;
import com.springbootproject.springbootproject.Entitities.OrderProductQuantity;
import com.springbootproject.springbootproject.Entitities.Product;
import com.springbootproject.springbootproject.Repositories.OrderDetailRepository;
import com.springbootproject.springbootproject.Repositories.ProductRepository;
import com.springbootproject.springbootproject.Service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private static final String ORDER_PLACED="Placed";

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<OrderDetail> placeOrder(OrderInput orderInput){
        List<OrderProductQuantity> productQuantityList=orderInput.getOrderProductQuantityList();
        List<OrderDetail> orderDetails = new ArrayList<>(); // List to store placed orders
        
        for(OrderProductQuantity o: productQuantityList){
            Product product =productRepository.findById(o.getProductId()).get();
             
            OrderDetail orderDetail=new OrderDetail( 
                orderInput.getFullName(),
                orderInput.getFullAddress(),
                orderInput.getContactNumber(),
                orderInput.getAlternateContactNumber(),
                ORDER_PLACED,
                
                product.getProductActualPrice() * o.getQuantity(),
                product
            );
            orderDetailRepository.save(orderDetail);
            orderDetails.add(orderDetail);

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
