package com.zmz.app.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 页面直接输入如下URL返回Json
 * http://localhost:8080/springmvc_war/lance/search?keyword=123
 * @author ASNPHDG
 * @create 2020-01-04 17:21
 */
@Controller
@RequestMapping("/lance")
public class SpringMvcController {

    @RequestMapping(value ="/search",produces={"application/json; charset=UTF-8"})
    @ResponseBody
    public JSONObject search(@RequestParam("keyword") String keyword) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("Status", "200");
        resultJson.put("Message", "查询成功");
        resultJson.put("result", keyword);
        return resultJson;
    }


}
