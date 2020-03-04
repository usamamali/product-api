package com.sitoo.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author usama
 */
@Validated
@AllArgsConstructor
@Data
public class ApiErrorMessage {

    @ApiModelProperty(example = "Can’t find product (<productId>)", required = true)
    @NotNull
    @JsonProperty("errorText")
    private String errorText;
}
