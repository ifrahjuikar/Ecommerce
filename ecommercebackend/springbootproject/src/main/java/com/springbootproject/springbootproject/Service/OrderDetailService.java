package com.springbootproject.springbootproject.Service;

import java.util.Optional;

import com.springbootproject.springbootproject.Entitities.OrderDetail;

public interface OrderDetailService {

    // Create or update an OrderDetail
    OrderDetail saveOrderDetail(OrderDetail orderDetail);

    // Fetch OrderDetail by orderId
    Optional<OrderDetail> getOrderDetailById(Integer orderId);

    // Delete an OrderDetail by orderId
    void deleteOrderDetail(Integer orderId);

    // Fetch all OrderDetails
    Iterable<OrderDetail> getAllOrderDetails();
}
