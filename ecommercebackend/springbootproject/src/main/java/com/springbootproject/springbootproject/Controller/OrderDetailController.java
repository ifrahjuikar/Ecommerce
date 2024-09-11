package com.springbootproject.springbootproject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.springbootproject.springbootproject.Entitities.OrderInput;
import com.springbootproject.springbootproject.ServiceImplementation.OrderDetailServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class OrderDetailController {

    @Autowired
    private OrderDetailServiceImpl orderDetailServiceImpl;

    @PostMapping("/placedorder")
    public void placeOrder(@RequestBody OrderInput orderInput) {
        
        
        orderDetailServiceImpl.placeOrder(orderInput);
    }
    


    
}
