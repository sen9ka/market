package com.example.market.controller;

import com.example.market.entity.model.Client;
import com.example.market.service.ClientService;
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
@RequestMapping("/clients")
@Tag(name = "Контроллер для управления клиентами")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/new")
    @Operation(summary = "Создание клиента")
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        return new ResponseEntity<>(clientService.saveClient(client), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Получение списка всех клиентов")
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{clientId}")
    @Operation(summary = "Получение клиента по ID")
    public ResponseEntity<Client> getClient(@PathVariable Long clientId) {
        return new ResponseEntity<>(clientService.findOne(clientId), HttpStatus.OK);
    }

    @DeleteMapping("/{clientId}")
    @Operation(summary = "Удаление клиента")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteClient(@PathVariable Long clientId) {
        clientService.deleteOne(clientId);
    }

}
