package com.jakgcc.springbootmall.controller;

import com.jakgcc.springbootmall.constant.ProductCategory;
import com.jakgcc.springbootmall.model.Product;
import com.jakgcc.springbootmall.rowmapper.dto.ProductRequest;
import com.jakgcc.springbootmall.rowmapper.dto.ProductRequestParams;
import com.jakgcc.springbootmall.service.ProductService;
import com.jakgcc.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/getProducts")
    public ResponseEntity<Page> getProducts(
//            查詢條件Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
//            排序sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
//            分頁Pagination
            @RequestParam(defaultValue = "5") @Min(0) @Max(1000) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) throws IOException {
        ProductRequestParams productRequestParams = new ProductRequestParams();
        productRequestParams.setProductCategory(category);
        productRequestParams.setSearch(search);
        productRequestParams.setOrderBy(orderBy);
        productRequestParams.setSort(sort);
        productRequestParams.setLimit(limit);
        productRequestParams.setOffset(offset);

        Page page = new Page();
        page.setLimit(limit);
        page.setOffset(offset);

        Integer total = productService.getCountProduct(productRequestParams);
        page.setTotal(total);



        List<Product> productList = productService.getProducts(productRequestParams);
        page.setProductList(productList);
        return ResponseEntity.status(HttpStatus.OK).body(page);


    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) throws IOException {
        Product product = productService.getProductById(productId);
        if (null == product) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) throws IOException {
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProductById(@PathVariable Integer productId, @RequestBody @Valid ProductRequest productRequest) throws IOException {
        Product product = productService.getProductById(productId);
        if (null == product) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProductById(productId, productRequest);
        Product updateProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer productId) throws IOException {
        productService.deleteProductById(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
