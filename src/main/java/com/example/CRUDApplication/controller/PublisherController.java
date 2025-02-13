package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.dto.PublisherDTO;
import com.example.CRUDApplication.dto.PublisherRequest;
import com.example.CRUDApplication.model.Publisher;
import com.example.CRUDApplication.repo.PublisherRepo;
import com.example.CRUDApplication.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
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

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPublishers() {
        try {
            List<Publisher> publisherList = publisherService.getAllPublishers();
            return ResponseEntity.ok(publisherList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving publishers");
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable Long id) {
        try {
            PublisherDTO publisher = publisherService.getPublisherById(id)
                    .orElseThrow(() -> new NoSuchElementException("No publisher found with this ID"));

            return ResponseEntity.ok(publisher);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving publisher");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPublisher(@RequestBody PublisherRequest publisher) {
        try {
            Publisher savedPublisher = publisherService.addPublisher(publisher);
            return new ResponseEntity<>(savedPublisher, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating publisher");
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updatePublisherById(@PathVariable Long id, @RequestBody PublisherRequest updateData) {
        try {
            Publisher updatedPublisher = publisherService.updatePublisherName(id, updateData);
            return new ResponseEntity<>(updatedPublisher, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updting publisher");
        }

    }

    @DeleteMapping("/deleteById/{id}")      // Define um endpoint DELETE para remover um publisher pelo ID
    public ResponseEntity<?> deletePublisherById(@PathVariable Long id) {
        try {
            boolean deletedPublisher = publisherService.deletePublisherById(id);

            if (deletedPublisher) {
                return ResponseEntity.status(HttpStatus.OK).body("Publisher deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting publisher");
        }
    }
}
