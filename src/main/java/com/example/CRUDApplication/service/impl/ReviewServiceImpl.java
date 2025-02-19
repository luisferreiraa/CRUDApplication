package com.example.CRUDApplication.service.impl;

import com.example.CRUDApplication.exception.ObjectNotFoundException;
import com.example.CRUDApplication.model.Review;
import com.example.CRUDApplication.repo.ReviewRepo;
import com.example.CRUDApplication.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

// Contém a lógica de negócio
// Faz validações relacionadas às regras do domínio
// Interage com repositórios para acessar a base de dados
// Não deve lidar com detalhes de HTTP (como códigos de status)

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Override
    public List<Review> getAllReviewsByBookId(Long bookId) {
        // Busca todos as reviews de um livro na base de dados
        List<Review> reviewList = reviewRepo.findByBookId(bookId);

        // Se não encontrar reviews, lança excepção
        if (reviewList.isEmpty()) {
            throw new ObjectNotFoundException("No reviews found book with ID: " + bookId);
        }
        // Se encontrar, devolve a lista de reviews
        return reviewList;
    }
}
