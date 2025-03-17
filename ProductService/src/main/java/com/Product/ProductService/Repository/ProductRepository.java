package com.Product.ProductService.Repository;

import com.Product.ProductService.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
