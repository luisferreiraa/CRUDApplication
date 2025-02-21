package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.PublisherDTO;
import com.example.CRUDApplication.dto.PublisherNameDTO;
import com.example.CRUDApplication.model.Publisher;
import com.example.CRUDApplication.repo.PublisherRepo;
import com.example.CRUDApplication.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Trata requisições HTTP (recebe e responde)
// Converte dados da requisição para o formato adequado
// Chama os serviços para processar a lógica de negócio
// Retorna as respostas HTTP apropriadas

@RestController     // Define a classe como controlador REST
@RequestMapping("/api/publishers")      // Define a rota base para os endpoints deste controlador
public class PublisherController {

    @Autowired      // Injeta automaticamente a dependência do repositório
    private PublisherRepo publisherRepo;

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/")
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publisherList = publisherService.getAllPublishers();
        return ResponseEntity.ok(publisherList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PublisherDTO>> getPublisherById(@PathVariable Long id) {
        Optional<PublisherDTO> publisher = publisherService.getPublisherById(id);
        return ResponseEntity.ok(publisher);
    }

    @PostMapping("/")
    public ResponseEntity<Publisher> addPublisher(@RequestBody PublisherNameDTO publisher) {
        Publisher savedPublisher = publisherService.addPublisher(publisher);
        return new ResponseEntity<>(savedPublisher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisherById(@PathVariable Long id, @RequestBody PublisherNameDTO updateData) {
            Publisher updatedPublisher = publisherService.updatePublisherName(id, updateData);
            return new ResponseEntity<>(updatedPublisher, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")      // Define um endpoint DELETE para remover um publisher pelo ID
    public ResponseEntity<?> deletePublisherById(@PathVariable Long id) {
            boolean deletedPublisher = publisherService.deletePublisherById(id);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
