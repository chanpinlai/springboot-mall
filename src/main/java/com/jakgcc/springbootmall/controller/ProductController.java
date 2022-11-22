package com.jakgcc.springbootmall.controller;

import com.jakgcc.springbootmall.dto.ProductRequest;
import com.jakgcc.springbootmall.model.Product;
import com.jakgcc.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId) throws IOException {
        Product product = productService.getProductById(productId);
        if(null==product){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
    }
    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) throws IOException {
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return  ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,@RequestBody @Valid ProductRequest productRequest) throws IOException {
        Product product = productService.getProductById(productId);
        if(null==product){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productId,productRequest);
        Product updateProduct = productService.getProductById(productId);
        return  ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer productId) throws IOException {
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
