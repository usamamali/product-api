package com.api.product.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author usama
 */
@Data
@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
@JsonFilter("ProductFilter")
public class Product {

    @ApiModelProperty(example = "45", readOnly = true)
    @JsonProperty("productId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false, unique = true, nullable = false)
    private Integer productId;

    @ApiModelProperty(example = "Batman socks", required = true, value = "")
    @JsonProperty("title")
    @Column(name = "title", nullable = false)
    private String title;

    @ApiModelProperty(example = "SCK-4511", required = true, value = "")
    @JsonProperty("sku")
    @Column(name = "sku", nullable = false)
    private String sku;

    @ApiModelProperty(example = "[\"AB123\",\"CD545\"]", value = "")
    @JsonProperty("barcodes")
    @ElementCollection
    @CollectionTable(name = "product_barcode", joinColumns = {@JoinColumn(name = "product_id")})
    @Column(name = "barcode", nullable = true, unique = true)
    private List<String> barcodes;

    @ApiModelProperty(example = "Socks with Batman logo", value = "")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(value = "")
    @JsonProperty("attributes")
    @ElementCollection
    @CollectionTable(name = "product_attribute", joinColumns = {@JoinColumn(name = "product_id")})
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "name", unique = true)),
            @AttributeOverride(name = "value", column = @Column(name = "value", nullable = false))})
    private List<Attribute> attributes;

    @ApiModelProperty(example = "98.00")
    @JsonProperty(value = "price")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "##.##")
    @Column(scale = 2)
    private BigDecimal price;

    @ApiModelProperty(readOnly = true)
    @JsonProperty("created")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    @Column(name = "created", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created;

    @ApiModelProperty(readOnly = true, example = "123232")
    @JsonProperty("lastUpdated")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @PreUpdate
    protected void setModifiedDate() {
        lastUpdated = new Date();
    }

}
