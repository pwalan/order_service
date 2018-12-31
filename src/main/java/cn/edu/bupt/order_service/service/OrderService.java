package cn.edu.bupt.order_service.service;


import cn.edu.bupt.order_service.domain.Order;

public interface OrderService {

    Order save(int userId, int productId);
}
