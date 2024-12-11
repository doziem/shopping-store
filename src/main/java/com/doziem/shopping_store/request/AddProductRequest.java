package com.doziem.shopping_store.request;

import com.doziem.shopping_store.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int unitInStock;

    private Category category;
}
