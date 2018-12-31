package cn.edu.bupt.order_service.controller;


import cn.edu.bupt.order_service.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id") int userId, @RequestParam("product_id") int productId) {
        Map<String, Object> data=new HashMap<>();
        data.put("code",0);
        data.put("data",orderService.save(userId, productId));
        return data;
    }

    /**
     * 在商品服务完全挂掉之后被调用
     * @param userId
     * @param productId
     * @return
     */
    private Object saveOrderFail(int userId, int productId){
        Map<String, Object> msg=new HashMap<>();
        msg.put("code",-1);
        msg.put("msg","抢购人数太多，您被挤出来了，请稍后重试！");
        return msg;
    }
}
