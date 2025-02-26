package com.example.CRUDApplication.service.impl;

import com.example.CRUDApplication.dto.PublisherDTO;
import com.example.CRUDApplication.dto.PublisherWithBooksDTO;
import com.example.CRUDApplication.dto.PublisherNameDTO;
import com.example.CRUDApplication.exception.ObjectNotFoundException;
import com.example.CRUDApplication.exception.RequestDataMissingException;
import com.example.CRUDApplication.model.Publisher;
import com.example.CRUDApplication.repo.PublisherRepo;
import com.example.CRUDApplication.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Contém a lógica de negócio
// Faz validações relacionadas às regras do domínio
// Interage com repositórios para acessar a base de dados
// Não deve lidar com detalhes de HTTP (como códigos de status)

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepo publisherRepo;

    @Override
    public Page<PublisherDTO> getAllPublishers(Pageable pageable) {
        // Busca todos os publishers da base de dados
        Page<Publisher> publisherList = publisherRepo.findAll(pageable);

        // Se não encontrar publishers, lança uma excepção
        if (publisherList.isEmpty()) {
            throw new ObjectNotFoundException("No publishers found in the system");
        }
        // Se encontrar, devolve a lista de publishers
        return publisherList.map(PublisherDTO::new);
    }

    @Override
    public Optional<PublisherWithBooksDTO> getPublisherById(Long id) {
        return publisherRepo.findById(id)
                .map(PublisherWithBooksDTO::new)
                .or(() -> {
                    throw new ObjectNotFoundException("No publisher found with ID: " + id);
                });
    }

    @Override
    public PublisherDTO addPublisher(PublisherNameDTO newPublisher) {

        // Cria uma nova entidade a partir do DTO recebido
        Publisher publisher = new Publisher();
        publisher.setName(newPublisher.getName());

        // Salva o publisher na base de dados
        Publisher savedPublisher = publisherRepo.save(publisher);

        // Converte a entidade persistida para DTO antes de retornar
        return new PublisherDTO(savedPublisher);
    }

    @Override
    public PublisherDTO updatePublisherName(Long id, PublisherNameDTO updateData) {

        // Busca o publisher pelo ID e lança uma excepção se não for encontrado
        Publisher publisherDB = publisherRepo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Publisher not found with ID: " + id));

        // Atualiza o nome do publisher
        publisherDB.setName(updateData.getName());

        // Salva o publisher atualizado na base de dados
        Publisher updatedPublisher = publisherRepo.save(publisherDB);

        // Converte a entidade persistida para DTO antes de retornar
        return new PublisherDTO(updatedPublisher);
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
