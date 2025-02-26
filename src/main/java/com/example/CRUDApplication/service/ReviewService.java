package com.example.CRUDApplication.service;

import com.example.CRUDApplication.dto.ReviewDTO;
import com.example.CRUDApplication.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    Page<ReviewDTO> getAllReviewsByBookId(Long bookId, Pageable pageable);

}
