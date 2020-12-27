package com.zmz.core.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ASNPHDG
 * @create 2020-01-26 22:18
 */
@Setter
@Getter
@ToString
@ApiModel(value="ResponseMessage")
public class Message {

    @ApiModelProperty(value="message code")
    private String code;

    @ApiModelProperty(value="message description")
    private String description;

}
