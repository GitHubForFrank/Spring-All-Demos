package com.zmz.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;

/**
 * @author ASNPHDG
 * @create 2020-01-27 17:34
 */
@Configuration
public class WebSocketConfig {

    /***
     * 检测{@link javax.websocket.server.ServerEndpointConfig}和{@link ServerEndpoint} 类型的bean，
     * 并在运行时使用标准Java WebSocket时注册。
     * 我们在{@link WebSocketConfig}中就是使用@ServerEndpoint去声明websocket服务
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
