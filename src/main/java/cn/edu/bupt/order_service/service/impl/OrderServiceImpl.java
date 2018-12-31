package cn.edu.bupt.order_service.service.impl;

import cn.edu.bupt.order_service.domain.Order;
import cn.edu.bupt.order_service.service.OrderService;
import cn.edu.bupt.order_service.service.ProductClient;
import cn.edu.bupt.order_service.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductClient productClient;

    @Override
    public Order save(int userId, int productId) {
        //使用feign调用订单服务
        String response = productClient.findById(productId);
        //调用用户服务，主要是后去用户名、级别或积分信息
        //TODO

        JsonNode jsonNode = JsonUtils.str2JsonNode(response);

        Order order = new Order();
        order.setCreateTime(new Date());
        order.setUserId(userId);
        order.setTradeNo(UUID.randomUUID().toString());
        order.setProductName(jsonNode.get("name").toString());
        order.setPrice(Integer.parseInt(jsonNode.get("price").toString()));
        return order;
    }
}
