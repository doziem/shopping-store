package com.doziem.shopping_store.controller;


import com.doziem.shopping_store.exceptions.ProductNotFoundException;
import com.doziem.shopping_store.exceptions.ResourceNotFoundException;
import com.doziem.shopping_store.model.Product;
import com.doziem.shopping_store.request.AddProductRequest;
import com.doziem.shopping_store.request.ProductUpdateRequest;
import com.doziem.shopping_store.response.ApiResponse;
import com.doziem.shopping_store.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@RequestMapping("/${api.prefix}/products")
public class ProductController {
    private final IProductService productService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("All Products found",products));
    }

    @GetMapping("/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id){
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("All Products found",product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found",null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){

        try {
            Product newproduct = productService.addProduct(product);

            return ResponseEntity.ok(new ApiResponse("Product created successfully",newproduct));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long id){

        try {
            Product updateProduct = productService.updateProduct(product,id);
            return ResponseEntity.ok(new ApiResponse("Product updated successfully",updateProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id){

        try {
             productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Product deleted successfully!",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName, @PathVariable String name){


        try {
            List<Product> products =   productService.getProductsByBrandAndName(brandName,name);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success!",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/product/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){


        try {
            List<Product> products =   productService.getProductsByBrandAndName(category,brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success!",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
        }

    }

    @GetMapping("/{name}/product-by-name")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){

        try {
            List<Product> products = productService.getProductsByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success!",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{brand}/product-by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand){

        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success!",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/{category}/product-by-category")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category){

        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Products Found",null));
            }
            return ResponseEntity.ok(new ApiResponse("success!",products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/product/count/brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name){

        try {
            var count =   productService.countProductsByBrandAndName(brand,name);

            return ResponseEntity.ok(new ApiResponse("success!",count));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
        }

    }

}
