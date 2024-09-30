package com.example.market.controller;

import com.example.market.entity.model.Product;
import com.example.market.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Контроллер для управления товарами")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    @Operation(summary = "Создание товара для клиента")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/clients/{clientId}")
    @Operation(summary = "Получение списка всех товаров клиента")
    public ResponseEntity<List<Product>> getProductsForClient(@PathVariable Long clientId) {
        return new ResponseEntity<>(productService.findProductsForClient(clientId), HttpStatus.OK);
    }

    @GetMapping("/clients/{clientId}/{productId}")
    @Operation(summary = "Получение товара клиента по ID")
    public ResponseEntity<Product> getProduct(@PathVariable Long clientId, @PathVariable Long productId) {
        return new ResponseEntity<>(productService.findProductById(clientId, productId), HttpStatus.OK);
    }

    @DeleteMapping("clients/{clientId}/{productId}")
    @Operation(summary = "Удаление товара клиента")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteProduct(@PathVariable Long clientId, @PathVariable Long productId) {
        productService.deleteOne(clientId, productId);
    }

}
