package com.sitoo.product.configuration;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sitoo.product.model.Product;
import com.sitoo.product.model.SearchResult;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author usama
 */
@Component
public class ProductFilter {

    public MappingJacksonValue filterResult(SearchResult searchResult, String fields) {

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(searchResult);

        mappingJacksonValue.setFilters(getProductFilter(fields));

        return mappingJacksonValue;
    }

    public MappingJacksonValue filterProduct(Product product, String fields) {

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(product);

        mappingJacksonValue.setFilters(getProductFilter(fields));

        return mappingJacksonValue;
    }

    private FilterProvider getProductFilter(String fields) {
        FilterProvider productFilter = null;
        if (Optional.ofNullable(fields).isPresent() && !fields.isBlank())
            productFilter = new SimpleFilterProvider()
                    .addFilter("ProductFilter", SimpleBeanPropertyFilter
                            .filterOutAllExcept(fields.split(",")));

        else
            productFilter = new SimpleFilterProvider()
                    .addFilter("ProductFilter", SimpleBeanPropertyFilter
                            .serializeAll());
        return productFilter;
    }
}
