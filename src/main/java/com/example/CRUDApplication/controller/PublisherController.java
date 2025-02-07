package com.example.CRUDApplication.controller;

import com.example.CRUDApplication.model.Publisher;
import com.example.CRUDApplication.repo.PublisherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController     // Define a classe como controlador REST
@RequestMapping("/api/publishers")      // Define a rota base para os endpoints deste controlador
public class PublisherController {

    @Autowired      // Injeta automaticamente a dependência do repositório
    private PublisherRepo publisherRepo;

    @GetMapping("/getAll")      // Endpoint para obter todos os publishers
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherRepo.findAll();       // Busca todos os publishers na base de dados
        if (publishers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);     // Retorna 204 se não encontrar publishers
        }
        return new ResponseEntity<>(publishers, HttpStatus.OK);     // Retorna lista de publishers com 200 (OK)
    }

    @PostMapping("/add")        // Endpoint para adicionar um novo publisher
    public ResponseEntity<?> addPublisher(@RequestBody Publisher publisher) {       // Objeto Publisher enviado no corpo da requisição
        try {
            if (publisher.getName() == null || publisher.getName().trim().isEmpty()) {
                return new ResponseEntity<>("Publisher name is required", HttpStatus.BAD_REQUEST);
            }
            Publisher savedPublisher = publisherRepo.save(publisher);
            return new ResponseEntity<>(savedPublisher, HttpStatus.CREATED);        // Retorna Publisher guardado com 201 (CREATED)
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving publisher", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getById/{id}")     // Endpoint para obter um publisher pelo ID
    public ResponseEntity<?> getPublisherById(@PathVariable Long id) {      // ID do publisher a ser procurado enviadp no corpo da requisição
        if (id == null || id <= 0) {
            return new ResponseEntity<>("Invalid ID", HttpStatus.BAD_REQUEST);
        }
        Optional<Publisher> publisher = publisherRepo.findById(id);
        return publisher.map(value -> new ResponseEntity<>(value, HttpStatus.OK))       // Retorna Publisher correspoondente ao ID
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));       // Retorna 404 se não for encontrado
    }

    @PutMapping("/updateById/{id}")     // Define um endpoint PUT para atualizar um Publisher pelo ID
    public ResponseEntity<?> updatePublisherById(@PathVariable Long id, @RequestBody Publisher newPublisherData) {
        Optional<Publisher> oldPublisherData = publisherRepo.findById(id);      // Busca o Publisher na base de dados

        if (oldPublisherData.isPresent()) {     // Verifica se o Publisher foi encontrado
            Publisher updatedPublisher = oldPublisherData.get();        // Obtém o Publisher existente
            updatedPublisher.setName(newPublisherData.getName());       // Atualiza o nome

            Publisher savedPublisher = publisherRepo.save(updatedPublisher);        // Guarda as alterações na base de dados
            return new ResponseEntity<>(savedPublisher, HttpStatus.OK);     // Retorna 200 (OK)
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);      // Retorna 400 se o Publisher não for encontrado
    }

    @DeleteMapping("/deleteById/{id}")      // Define um endpoint DELETE para remover um publisher pelo ID
    public ResponseEntity<HttpStatus> deletePublisherById(@PathVariable Long id) {
        try {
            publisherRepo.deleteById(id);       // Remove o publisher pelo ID
            return new ResponseEntity<>(HttpStatus.OK);     // Retorna 200 (OK) se a exclusão for bem-sucedida
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);      // Retorna 500 em caso de erro
        }
    }
}
