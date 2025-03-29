package com.Product.ProductService.Controller;


import com.Product.ProductService.Model.Product;
import com.Product.ProductService.Service.OrderService;
import com.Product.ProductService.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final RestTemplateBuilder templateBuilder;
    private final OrderService orderService;

    public ProductController(ProductService productService, RestTemplateBuilder templateBuilder, OrderService orderService) {
        this.productService = productService;
        this.templateBuilder = templateBuilder;
        this.orderService = orderService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }


    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/categories/{category}")
    public Page<Product> getProductByCategory(@PathVariable String category, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getProductsByCategory(category, pageable);
    }

}

