package com.aivle.bookapp.dto;

// 향후 book patch 범용 메서드 작성 시, UpdateBookCommand로 통폐합
public record UpdateBookCoverImageUrlCommand(
        String CoverImageUrl
) {
}
