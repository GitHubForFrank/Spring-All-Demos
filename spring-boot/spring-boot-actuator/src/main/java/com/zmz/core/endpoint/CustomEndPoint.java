package com.zmz.core.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ASNPHDG
 * @create 2020-01-31 10:14
 * @description : 自定义端点
 */
@Endpoint(id = "customEndPoint")
@Component
public class CustomEndPoint {
    /**
     * 可以自定义端点获取想要的系统信息
     * @return
     */
    @ReadOperation
    public Map<String, Object> getCustomDefineInfo() {
        Map<String, Object> cupInfoMap = new LinkedHashMap<>();
        cupInfoMap.put("key01","value01");
        cupInfoMap.put("key02","value02");
        cupInfoMap.put("key03","value03");
        return cupInfoMap;
    }
}
