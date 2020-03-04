package com.sitoo.product.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Embeddable;

/**
 * @author usama
 */
@Validated
@Data
@Embeddable
public class Attribute {

    private String name;

    private String value;
}

