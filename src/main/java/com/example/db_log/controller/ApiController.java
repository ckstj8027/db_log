package com.example.db_log.controller;

import com.example.db_log.domain.Orders;
import com.example.db_log.domain.Product;
import com.example.db_log.repository.OrdersRepository;
import com.example.db_log.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;

    /**
     * Retrieves all products belonging to a specific category.
     * This involves a JOIN between Product and ProductCategory tables.
     */
    @GetMapping("/categories/{categoryId}/products")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    /**
     * Retrieves all orders for a specific user.
     * This involves a JOIN between Orders and User tables.
     */
    @GetMapping("/users/{userId}/orders")
    public List<Orders> getOrdersByUser(@PathVariable Long userId) {
        return ordersRepository.findByUserId(userId);
    }

    /**
     * A simple, fast query to get all products.
     */
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // --- Test APIs for simulating different query patterns ---

    /**
     * Simulates a user rapidly browsing through all categories.
     * Generates many small, fast SELECT queries.
     */
    @GetMapping("/test/rapid-browsing")
    public String rapidBrowsing() {
        for (long i = 1; i <= 3; i++) {
            productRepository.findByCategoryId(i);
        }
        return "Simulated rapid category browsing. Check the 3D dashboard.";
    }

    /**
     * Simulates a large, complex report query.
     * In a real scenario, this would be a more complex query, but here we simulate it
     * by fetching all products and all orders sequentially.
     */
    @GetMapping("/test/complex-report")
    public String complexReport() {
        productRepository.findAll();
        ordersRepository.findAll();
        return "Simulated complex report query. Check the 3D dashboard.";
    }
}
