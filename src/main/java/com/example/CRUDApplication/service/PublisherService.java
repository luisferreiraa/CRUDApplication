package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.PublisherDTO;
import com.example.CRUDApplication.dto.PublisherRequest;
import com.example.CRUDApplication.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    List<Publisher> getAllPublishers();
    Optional<PublisherDTO> getPublisherById(Long id);
    Publisher addPublisher(PublisherRequest publisher);
    Publisher updatePublisherName(Long id, PublisherRequest updateData);
    boolean deletePublisherById(Long id);
}
