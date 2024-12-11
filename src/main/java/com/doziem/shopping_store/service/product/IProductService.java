package com.doziem.shopping_store.service.product;

import com.doziem.shopping_store.model.Product;
import com.doziem.shopping_store.request.AddProductRequest;
import com.doziem.shopping_store.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand,String name);



}
