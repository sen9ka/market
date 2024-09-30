package com.example.market.service;

import com.example.market.controller.exceptionHandler.exceptions.ClientNotFoundException;
import com.example.market.controller.exceptionHandler.exceptions.ProductHasDifferentOwnerException;
import com.example.market.controller.exceptionHandler.exceptions.ProductNotFoundException;
import com.example.market.entity.model.Product;
import com.example.market.repository.ClientRepository;
import com.example.market.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;

    @Transactional
    public Product saveProduct(Product product) {
        if (product.getClient() == null) {
            throw new ClientNotFoundException(Constant.CLIENT_NOT_SPECIFIED_ERROR_MESSAGE);
        }
        clientService.checkIfClientExists(clientRepository.findById(product.getClient().getId()), product.getClient().getId());
        return productRepository.save(product);
    }

    @Transactional
    public List<Product> findProductsForClient(Long clientId) {
        clientService.checkIfClientExists(clientRepository.findById(clientId), clientId);
        return productRepository.findByClientId(clientId);
    }

    @Transactional
    public Product findProductById(Long clientId, Long productId) {
        clientService.checkIfClientExists(clientRepository.findById(clientId), clientId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        checkProductPresenceAndOwner(optionalProduct, productId, clientId);
        return optionalProduct.orElse(null);
    }

    @Transactional
    public void deleteOne(Long clientId, Long productId) {
        clientService.checkIfClientExists(clientRepository.findById(clientId), clientId);
        checkProductPresenceAndOwner(productRepository.findById(productId), productId, clientId);
        productRepository.deleteById(productId);
    }

    // Проверка на существование товара и его принадлежность к данному клиенту
    public void checkProductPresenceAndOwner(Optional<Product> optionalProduct, Long productId, Long clientId) {
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(Constant.PRODUCT_NOT_FOUND_ERROR_MESSAGE + productId);
        }
        if (!Objects.equals(optionalProduct.get().getClient().getId(), clientId)) {
            throw new ProductHasDifferentOwnerException(Constant.PRODUCT_HAS_DIFFERENT_OWNER_ERROR_MESSAGE);
        }
    }
}
