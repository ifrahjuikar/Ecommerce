package com.springbootproject.springbootproject.Repositories;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springbootproject.springbootproject.Entitities.OrderDetail;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Integer>{


}