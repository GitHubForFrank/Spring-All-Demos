package com.zmz.core.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-26 22:17
 */
@Setter
@Getter
@ApiModel(value="ResponseMetaInfo")
public class Meta {
    @ApiModelProperty(value="completed time")
    private long completedTime;

    @ApiModelProperty(value="message list")
    private List<Message> messages;
}