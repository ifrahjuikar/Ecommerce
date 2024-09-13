package com.springbootproject.springbootproject.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.springbootproject.springbootproject.Entitities.OrderDetail;
import com.springbootproject.springbootproject.Entitities.OrderInput;
import com.springbootproject.springbootproject.Exception.ApiResponse;
import com.springbootproject.springbootproject.ServiceImplementation.OrderDetailServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailServiceImpl orderDetailServiceImpl;

    @PostMapping("/placedorder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderInput orderInput) {
        List<OrderDetail> orderDetails=orderDetailServiceImpl.placeOrder(orderInput);
        return new ResponseEntity<>(new ApiResponse("success", HttpStatus.OK.value(), "OK", orderDetails), HttpStatus.OK);
    }

    
    

   
    
}
