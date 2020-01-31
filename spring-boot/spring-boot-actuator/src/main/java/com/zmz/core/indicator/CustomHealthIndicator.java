package com.zmz.core.indicator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * @author ASNPHDG
 * @create 2020-01-31 10:14
 * @description : 自定义健康检查指标
 */
@Component
public class CustomHealthIndicator extends AbstractHealthIndicator{
    @Override
    protected void doHealthCheck(Health.Builder builder) {
        // 这里用随机数模拟健康检查的结果
        double random = Math.random();
        //使用构建器构建应报告的运行状况详细信息。
        //如果引发异常，则状态为DOWN且显示异常消息。
        if (random > 0.5) {
            builder.up().status("FATAL").withDetail("error code", "某健康专项检查失败");
            int s=1/0;
        } else {
            builder.up().withDetail("success code", "自定义检查一切正常");
        }
    }
}
