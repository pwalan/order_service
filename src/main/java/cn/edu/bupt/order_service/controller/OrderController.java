package cn.edu.bupt.order_service.controller;


import cn.edu.bupt.order_service.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("save")
    @HystrixCommand(fallbackMethod = "saveOrderFail")
    public Object save(@RequestParam("user_id") int userId, @RequestParam("product_id") int productId, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("data", orderService.save(userId, productId));
        return data;
    }

    /**
     * 在商品服务、用户服务等完全挂掉之后被调用
     *
     * @param userId
     * @param productId
     * @return
     */
    private Object saveOrderFail(int userId, int productId, HttpServletRequest request) {
        //监控报警
        String saveOrderKey = "save-order";
        String sendValue = redisTemplate.opsForValue().get(saveOrderKey);
        final String ip = request.getRemoteAddr();
        int timeout = 10;

        new Thread(() -> {
            if (StringUtils.isBlank(sendValue)) {
                //发送HTTP请求，调用短信服务或邮件服务 TODO
                System.out.println("紧急短信，用户下单失败，请立刻查找原因, ip地址是" + ip);

                redisTemplate.opsForValue().set(saveOrderKey, "save-order-fail", timeout, TimeUnit.SECONDS);
            } else {
                System.out.println("已经发送过短信，" + String.valueOf(timeout) + "秒内不重复发送");
            }
        }).start();

        Map<String, Object> msg = new HashMap<>();
        msg.put("code", -1);
        msg.put("msg", "抢购人数太多，您被挤出来了，请稍后重试！");
        return msg;
    }
}
