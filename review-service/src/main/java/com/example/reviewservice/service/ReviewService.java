package com.example.reviewservice.service;


import com.example.reviewservice.client.MemberServiceClient;
import com.example.reviewservice.domain.Review;
import com.example.reviewservice.dto.MemberDto;
import com.example.reviewservice.dto.ReviewRequestDto;
import com.example.reviewservice.dto.ReviewResponseDto;
import com.example.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberServiceClient memberServiceClient;


    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto request){
        Review review = Review.builder()
                .storeName(request.getStoreName())
                .rating(request.getRating())
                .content(request.getContent())
                .memberId(request.getMemberId())
                .build();
        Review saved=reviewRepository.save(review);
        MemberDto member = memberServiceClient.getMember(saved.getMemberId());
        return ReviewResponseDto.from(saved, member);
    }

    public List<ReviewResponseDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(review -> {
                    MemberDto member = memberServiceClient
                            .getMember((review.getMemberId()));
                    return ReviewResponseDto
                            .from(review, member);
                })
                .collect(Collectors.toList());
    }

    public ReviewResponseDto getReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을수 없습니다."));
        MemberDto member = memberServiceClient
                .getMember(review.getMemberId());
        return ReviewResponseDto.from(review, member);
    }

    @Transactional
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto request){
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을수 없습니다"));
        review.update(
                request.getStoreName(),
                request.getRating(),
                request.getContent()
        );

        MemberDto member = memberServiceClient
                .getMember(review.getMemberId());
        return ReviewResponseDto.from(review, member);
    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을수 없습니다"));
        reviewRepository.delete(review);
    }
}
