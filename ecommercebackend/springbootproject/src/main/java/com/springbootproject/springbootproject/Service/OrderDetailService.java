package com.springbootproject.springbootproject.Service;

import java.util.List;
import java.util.Optional;

import com.springbootproject.springbootproject.Entitities.OrderDetail;
import com.springbootproject.springbootproject.Entitities.OrderInput;

public interface OrderDetailService {

    // Create or update an OrderDetail
    OrderDetail saveOrderDetail(OrderDetail orderDetail);

    // Fetch OrderDetail by orderId
    Optional<OrderDetail> getOrderDetailById(Integer orderId);

    // Delete an OrderDetail by orderId
    void deleteOrderDetail(Integer orderId);

    // Fetch all OrderDetails
    Iterable<OrderDetail> getAllOrderDetails();

    List<OrderDetail> placeOrder(OrderInput orderInput);
}
