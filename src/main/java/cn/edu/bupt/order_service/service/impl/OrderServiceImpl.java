package cn.edu.bupt.order_service.service.impl;

import cn.edu.bupt.order_service.domain.Order;
import cn.edu.bupt.order_service.service.OrderService;
import cn.edu.bupt.order_service.service.ProductClient;
import cn.edu.bupt.order_service.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;
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

    @Autowired
    private ProductClient productClient;

    @Override
    public Order save(int userId, int productId) {
        //使用ribbon
        //Map<String, Object> productMap = restTemplate.getForObject("http://product-service/api/v1/product/find?id=" + productId, Map.class);

        //使用feign
        String response = productClient.findById(productId);
        JsonNode jsonNode = JsonUtils.str2JsonNode(response);

        Order order = new Order();
        order.setCreateTime(new Date());
        order.setUserId(userId);
        order.setTradeNo(UUID.randomUUID().toString());
        //order.setProductName(productMap.get("name").toString());
        //order.setPrice(Integer.parseInt(productMap.get("price").toString()));
        order.setProductName(jsonNode.get("name").toString());
        order.setPrice(Integer.parseInt(jsonNode.get("price").toString()));
        return order;
    }
}
