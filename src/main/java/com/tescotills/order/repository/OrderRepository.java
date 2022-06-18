package com.tescotills.order.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tescotills.order.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, String> {

}