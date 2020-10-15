package com.lwn.common.request;

import com.lwn.common.enums.OrderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class PageCondition {

    @ApiModelProperty(value = "页号", example = "1", dataType = "int")
    @Min(value = 1, message = "页号最小为1")
    private Integer pageIndex = 1;

    @ApiModelProperty(value = "页容量", example = "10", dataType = "int")
    @Min(value = 1, message = "也容量最小为1")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "排序字段", example = "id,createDate,status,name", dataType = "string")
    private String sort = "";

    @ApiModelProperty(value = "排序方式", example = "asc,desc", dataType = "string")
    private OrderType order;

}
