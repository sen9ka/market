package com.example.market.service;

import com.example.market.controller.exceptionHandler.exceptions.ClientNotFoundException;
import com.example.market.controller.exceptionHandler.exceptions.ProductNotFoundException;
import com.example.market.entity.model.Client;
import com.example.market.entity.model.Product;
import com.example.market.repository.ClientRepository;
import com.example.market.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Product saveProduct(Product product) {
        if (product.getClient() == null) {
            throw new ClientNotFoundException(Constant.CLIENT_NOT_SPECIFIED_ERROR_MESSAGE);
        }
        Optional<Client> optionalClient = clientRepository.findById(product.getClient().getId());
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(Constant.CLIENT_NOT_FOUND_ERROR_MESSAGE + product.getClient().getId());
        }
        return productRepository.save(product);
    }

    @Transactional
    public List<Product> findProductsForClient(Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(Constant.CLIENT_NOT_FOUND_ERROR_MESSAGE + clientId);
        }
        return productRepository.findByClientId(clientId);
    }

    @Transactional
    public Product findProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElseThrow(() -> new ProductNotFoundException(Constant.PRODUCT_NOT_FOUND_ERROR_MESSAGE + id));
    }

    @Transactional
    public void deleteOne(Long id) {
        productRepository.deleteById(id);
    }
}
