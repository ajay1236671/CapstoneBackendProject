package com.Product.ProductService.Service;

import com.Product.ProductService.Model.Product;
import com.Product.ProductService.Repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private EntityManager entityManager;
    private RedisTemplate<Long, Object> redisTemplate;

    public ProductService(ProductRepository productRepository, EntityManager entityManager, RedisTemplate<Long, Object> redisTemplate) {
        this.productRepository = productRepository;
        this.entityManager = entityManager;
        this.redisTemplate = redisTemplate;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product getProductById(Long id) {
        try {
            Product cacheProduct = (Product) redisTemplate.opsForHash().get(id, "PRODUCTS");
            if (cacheProduct != null) {
                return cacheProduct;
            }
        } catch (Exception e) {
            System.err.println("Redis is unavailable, proceeding without cache: " + e.getMessage());
        }

        Optional<Product> product = productRepository.findById(id);
        return product.map(p -> {
            try {
                redisTemplate.opsForHash().put(id, "PRODUCTS", p);
            } catch (Exception e) {
                System.err.println("Failed to store product in Redis: " + e.getMessage());
            }
            return p;
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }


    public Product saveProduct(Product product) {

        if (product.getCategory() != null && product.getCategory().getId() != null) {
            product.setCategory(entityManager.merge(product.getCategory()));
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> getProductsByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }
}
