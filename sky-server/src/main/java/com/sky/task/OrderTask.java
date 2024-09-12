package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: tangjie
 * @Description: TODO
 * @DateTime: 2024/9/12 0:02
 **/
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     *处理超时订单
     */
    @Scheduled(cron = "0 * * * * ? ") //每分钟触发一次
    public void processTimeoutOrder()
    {
        log.info("定时处理超时订单:{}", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        //查询是否存在超时订单
        // select * from orders where status = ? and order_time < (当前时间 - 15分钟)
        List<Orders> ordersList = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);

        if(ordersList != null && ordersList.size() > 0){
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时，自动取消");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }

    }


    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨一点触发一次
    public void precessDeliveryOrder()
    {

        log.info("定时处理派送中的订单{}",LocalDateTime.now());
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(-60);
        List<Orders> OrderList = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, localDateTime);
        for (Orders orders : OrderList) {
            orders.setStatus(Orders.COMPLETED);
            orderMapper.update(orders);
        }

    }


}
