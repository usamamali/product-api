package com.sitoo.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * @author usama
 */
@Validated
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Attribute {

    private String name;

    private String value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        if (name != null && attribute.name != null) return name.trim().equalsIgnoreCase(attribute.name.trim());
        return Objects.equals(name, attribute.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

