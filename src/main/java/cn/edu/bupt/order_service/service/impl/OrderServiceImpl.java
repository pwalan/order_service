package cn.edu.bupt.order_service.service.impl;

import cn.edu.bupt.order_service.domain.Order;
import cn.edu.bupt.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Order save(int userId, int productId) {
        Map<String, Object> productMap = restTemplate.getForObject("http://product-service/api/v1/product/find?id=" + productId, Map.class);

        Order order = new Order();
        order.setCreateTime(new Date());
        order.setUserId(userId);
        order.setTradeNo(UUID.randomUUID().toString());
        order.setProductName(productMap.get("name").toString());
        order.setPrice(Integer.parseInt(productMap.get("price").toString()));

        return order;
    }
}
