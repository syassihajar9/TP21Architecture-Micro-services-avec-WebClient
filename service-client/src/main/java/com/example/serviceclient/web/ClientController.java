package com.example.serviceclient.web;


import com.example.serviceclient.entities.Client;
import com.example.serviceclient.repositories.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository repo;

    public ClientController(ClientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Client> all() {
        return repo.findAll();
    }

    @PostMapping
    public Client save(@RequestBody Client c) {
        return repo.save(c);
    }
}
