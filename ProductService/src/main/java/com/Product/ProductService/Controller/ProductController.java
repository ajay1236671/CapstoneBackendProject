package com.Product.ProductService.Controller;


import com.Product.ProductService.Model.Product;
import com.Product.ProductService.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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
    public Product createProduct(@RequestBody Product product, @PathVariable String username) {
        return productService.saveProduct(product, username);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
         productService.deleteProduct(id);
         return ResponseEntity.ok(id);
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<Map<String, Object>> buyProduct(HttpServletRequest request, @PathVariable Long id, @RequestBody PaymentRequest paymentRequest) {
        String userId = (String) request.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized user"));
        }

        // Call Payment Service using RestTemplate
        RestTemplate restTemplate = templateBuilder.build();
        ResponseEntity<Map> response = restTemplate.postForEntity(
                "http://localhost:8082/payments/process",
                paymentRequest,
                Map.class
        );

        return ResponseEntity.ok(response.getBody());
    }
}

