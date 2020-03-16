package com.api.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author usama
 */
@Validated
@Data
@Builder
public class SearchResult {

    @ApiModelProperty()
    @JsonProperty("totalCount")
    private Long totalCount = 0L;

    @ApiModelProperty()
    @JsonProperty("items")
    @Valid
    private List<Product> items = null;

}
