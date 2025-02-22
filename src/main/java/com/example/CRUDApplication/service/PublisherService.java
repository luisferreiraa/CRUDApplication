package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.PublisherDTO;
import com.example.CRUDApplication.dto.PublisherWithBooksDTO;
import com.example.CRUDApplication.dto.PublisherNameDTO;
import com.example.CRUDApplication.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    List<PublisherDTO> getAllPublishers();
    Optional<PublisherWithBooksDTO> getPublisherById(Long id);
    PublisherDTO addPublisher(PublisherNameDTO publisher);
    PublisherDTO updatePublisherName(Long id, PublisherNameDTO updateData);
    boolean deletePublisherById(Long id);
}
