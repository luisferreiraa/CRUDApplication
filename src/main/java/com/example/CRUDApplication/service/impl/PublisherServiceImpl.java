package com.example.CRUDApplication.service.impl;

import com.example.CRUDApplication.dto.PublisherDTO;
import com.example.CRUDApplication.dto.PublisherRequest;
import com.example.CRUDApplication.exception.ObjectNotFoundException;
import com.example.CRUDApplication.exception.RequestDataMissingException;
import com.example.CRUDApplication.model.Publisher;
import com.example.CRUDApplication.repo.PublisherRepo;
import com.example.CRUDApplication.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

// Contém a lógica de negócio
// Faz validações relacionadas às regras do domínio
// Interage com repositórios para acessar a base de dados
// Não deve lidar com detalhes de HTTP (como códigos de status)

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepo publisherRepo;

    @Override
    public List<Publisher> getAllPublishers() {
        // Busca todos os publishers da base de dados
        List<Publisher> publisherList = publisherRepo.findAll();

        // Se não encontrar publishers, lança uma excepção
        if (publisherList.isEmpty()) {
            throw new ObjectNotFoundException("No publishers found in the system");
        }
        // Se encontrar, devolve a lista de publishers
        return publisherList;
    }

    @Override
    public Optional<PublisherDTO> getPublisherById(Long id) {
        // Busca um publisher pelo ID
        Optional<Publisher> publisherDB = publisherRepo.findById(id);

        // Se o publisher não existir, lança uma excepção
        if (publisherDB.isEmpty()) {
            throw new ObjectNotFoundException("No publisher found with ID: " + id);
        }
        // Converte Publisher em PublisherDTO e retorna
        return publisherDB.map(PublisherDTO::new);
    }

    @Override
    public Publisher addPublisher(PublisherRequest newPublisher) {
        // Valida se o nome do publisher foi fornecido
        if (newPublisher.getName() == null || newPublisher.getName().trim().isEmpty()) {
            throw new RequestDataMissingException("Publisher name is required");
        }

        // Cria uma nova entidade a partir do DTO recebido
        Publisher savedPublisher = new Publisher();
        savedPublisher.setName(newPublisher.getName());

        // Salva o publisher na base de dados e retorna a entidade persistida
        return publisherRepo.save(savedPublisher);
    }

    @Override
    public Publisher updatePublisherName(Long id, PublisherRequest updateData) {
        // Valida se o nome do publisher foi fornecido
        if (updateData.getName() == null || updateData.getName().trim().isEmpty()) {
            throw new RequestDataMissingException("Publisher name is required");
        }

        // Busca o publisher pelo ID e lança uma excepção se não for encontrado
        Publisher publisherDB = publisherRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Publisher not found with ID: " + id));

        // Atualiza o nome do publisher
        publisherDB.setName(updateData.getName());

        // Salva e retorna o publisher atualizado
        return publisherRepo.save(publisherDB);
    }

    @Override
    public boolean deletePublisherById(Long id) {
        // Verifica se o publisher existe antes de remover
        if (publisherRepo.existsById(id)) {
            publisherRepo.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Publisher not found with ID: " + id);
    }
}
