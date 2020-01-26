package com.zmz.core.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ASNPHDG
 * @create 2020-01-26 22:17
 */
@Setter
@Getter
@ApiModel(description = "Response data, will be null if HTTP.STATUS is not 1XX or 2XX")
public class Response<T> {
    @ApiModelProperty
    private Meta meta;

    @ApiModelProperty
    private T data;
}
