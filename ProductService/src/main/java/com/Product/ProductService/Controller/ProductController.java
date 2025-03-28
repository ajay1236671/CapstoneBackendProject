package com.Product.ProductService.Controller;


import com.Product.ProductService.Dto.OrderRequestDto;
import com.Product.ProductService.Model.Product;
import com.Product.ProductService.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final RestTemplateBuilder templateBuilder;

    public ProductController(ProductService productService, RestTemplateBuilder templateBuilder) {
        this.productService = productService;
        this.templateBuilder = templateBuilder;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

//    @GetMapping("/{id}")
//    public String getProductById(@PathVariable String id, @RequestAttribute("userId") String userId) {
//        return "Product details for ID: " + id + " accessed by User: " + userId;
//    }

    @PostMapping("/{username}")
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
         productService.deleteProduct(id);
         return ResponseEntity.ok(id);
    }

    @GetMapping("/categories/{category}")
    public Page<Product> getProductByCategory(@PathVariable String category,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        return productService.getProductsByCategory(category,pageable);
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<Map<String, Object>> checkOut(HttpServletRequest request, @PathVariable Long id, @RequestBody OrderRequestDto orderRequestDto) {
        String userId = (String) request.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized user"));
        }

        // Call Payment Service using RestTemplate

        RestTemplate restTemplate = templateBuilder.build();
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:8082/payments/process",
                orderRequestDto,
                Map.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}

