package com.zmz.app.api.controller;

import com.zmz.app.api.dto.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * 产品查询接口
 * @author ASNPHDG
 * @create 2020-01-26 21:31
 */
@Slf4j
@Api(value = "产品API")
@RestController
public class ProductController {

    /***
     * 一个标准的swagger注解
     */
    @ApiOperation(notes = "查询所有产品", value = "产品查询接口")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "产品编号", paramType = "path", defaultValue = "1")
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "无效的请求"),
            @ApiResponse(code = 401, message = "未经过授权认证"),
            @ApiResponse(code = 403, message = "已经过授权认证，但是没有该资源对应的访问权限"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源,商品不存在"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @GetMapping(value = "/product/{id}", produces = "application/json")
    public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {
        ProductDto product = new ProductDto(id, "product" + id, new Date());
        return ResponseEntity.ok(product);
    }


    /***
     * 如果用实体类接收参数,则用实体类对应的属性名称指定参数
     */
    @ApiOperation(notes = "保存产品", value = "产品保存接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品编号", paramType = "body", defaultValue = "1"),
            @ApiImplicitParam(name = "name", value = "产品名称", paramType = "body"),
            @ApiImplicitParam(name = "date", value = "产品生产日期", paramType = "body")
    })
    @PostMapping(value = "/product")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductDto product) {
        System.out.println(product);
        return ResponseEntity.ok().build();
    }


    /***
     * 在配置类中指明了该接口不被扫描到,可以在配置类中使用正则指定某一类符合规则的接口不被扫描到
     */
    @ApiOperation(notes = "该接口会被忽略", value = "产品保存接口")
    @PostMapping(value = "/ignore")
    public ResponseEntity<ProductDto> ignore() {
        return ResponseEntity.ok().build();
    }

    /**
     * 不加上任何swagger相关的注解也会被扫描到 如果不希望被扫描到，需要用 @ApiIgnore 修饰
     */
    @PostMapping(value = "/normal")
    public ResponseEntity<Void> normal() {
        return ResponseEntity.ok().build();
    }


    @ApiIgnore
    @PostMapping(value = "/apiIgnore")
    public ResponseEntity<Void> apiIgnore() {
        return ResponseEntity.ok().build();
    }

}