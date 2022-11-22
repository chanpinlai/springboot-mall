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
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> getProducts() throws IOException {
        List<Product> productList = productService.getProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productList);



    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) throws IOException {
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

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProductById(@PathVariable Integer productId, @RequestBody @Valid ProductRequest productRequest) throws IOException {
        Product product = productService.getProductById(productId);
        if(null==product){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProductById(productId,productRequest);
        Product updateProduct = productService.getProductById(productId);
        return  ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer productId) throws IOException {
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
