package com.consolefire.spcli.authserver.web;

import com.consolefire.spcli.authserver.data.dto.ClientRegistrationRequestDto;
import com.consolefire.spcli.authserver.data.dto.ClientRegistrationResponseDto;
import com.consolefire.spcli.authserver.service.ClientRegistryService;
import jakarta.validation.Valid;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<ClientRegistrationResponseDto> createClient(
        @Valid @RequestBody Mono<ClientRegistrationRequestDto> requestMono) { // Changed return type
        return requestMono.flatMap(clientRegistryService::registerClient);
    }

    @GetMapping("/{clientId}")
    public Mono<RegisteredClient> getClient(@PathVariable String clientId) {
        return clientRegistryService.findClientByClientId(clientId)
            .switchIfEmpty(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with ID: " + clientId)));
    }

    @GetMapping
    public Flux<RegisteredClient> getAllClients() {
        return clientRegistryService.findAllClients();
    }

    @PutMapping("/{clientId}")
    public Mono<RegisteredClient> updateClient(@PathVariable String clientId,
        @Valid @RequestBody Mono<ClientRegistrationRequestDto> requestMono) {
        return requestMono.flatMap(request -> clientRegistryService.updateClient(clientId, request))
            .switchIfEmpty(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with ID: " + clientId)));
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Integer> deleteClient(@PathVariable String clientId) {
        return clientRegistryService.deleteClient(clientId)
            .switchIfEmpty(
                Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with ID: " + clientId)));
    }
}