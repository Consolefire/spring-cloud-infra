package com.consolefire.spcli.authserver.web;

import com.consolefire.spcli.authserver.data.dto.ClientRegistrationRequestDto;
import com.consolefire.spcli.authserver.data.dto.ClientRegistrationResponseDto;
import com.consolefire.spcli.authserver.service.ClientRegistryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin/clients")
@PreAuthorize("hasRole('ADMIN')")
public class ClientRegistryController {

    private final ClientRegistryService clientRegistryService;

    public ClientRegistryController(ClientRegistryService clientRegistryService) {
        this.clientRegistryService = clientRegistryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientRegistrationResponseDto createClient(
            @Valid @RequestBody ClientRegistrationRequestDto request) {
        return clientRegistryService.registerClient(request);
    }

    @GetMapping("/{clientId}")
    public RegisteredClient getClient(@PathVariable String clientId) {
        RegisteredClient client = clientRegistryService.findClientByClientId(clientId);
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with ID: " + clientId);
        }
        return client;
    }

    @GetMapping
    public List<RegisteredClient> getAllClients() {
        return clientRegistryService.findAllClients();
    }

    @PutMapping("/{clientId}")
    public RegisteredClient updateClient(@PathVariable String clientId,
                                         @Valid @RequestBody ClientRegistrationRequestDto request) {
        try {
            return clientRegistryService.updateClient(clientId, request);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable String clientId) {
        try {
            clientRegistryService.deleteClient(clientId);
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}