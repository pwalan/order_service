package cn.edu.bupt.order_service.fallback;

import cn.edu.bupt.order_service.service.ProductClient;
import org.springframework.stereotype.Component;

/**
 * 针对商品服务，做降级处理
 */
@Component
public class ProductClientFallback implements ProductClient{

    @Override
    public String findById(int id) {
        System.out.println("feign调用product-service findById异常");

        return null;
    }
}
