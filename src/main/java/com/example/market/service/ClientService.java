package com.example.market.service;

import com.example.market.controller.exceptionHandler.exceptions.ClientNotFoundException;
import com.example.market.entity.model.Client;
import com.example.market.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Transactional
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Transactional
    public Client findOne(Long clientId) {
        Optional<Client> client = clientRepository.findById(clientId);
        checkIfClientExists(client, clientId);
        return client.orElse(null);
    }

    @Transactional
    public void deleteOne(Long id) {
        clientRepository.deleteById(id);
    }

    public void checkIfClientExists(Optional<Client> optionalClient, Long clientId) {
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(Constant.CLIENT_NOT_FOUND_ERROR_MESSAGE + clientId);
        }
    }
}
