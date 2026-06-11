package com.aivle.bookapp.dto;

import jakarta.validation.constraints.NotNull;

public record PutBookRequest(
        @NotNull(message="도서 제목은 필수적으로 작성되어야 합니다.")
        String title,
        @NotNull(message="도서 저자는 필수적으로 작성되어야 합니다.")
        String author,
        @NotNull(message="도서 본문은 필수적으로 작성되어야 합니다.")
        String content,
        String coverImageUrl,
        String category,
        // createdAt(생성일자)는 수정되지 않으므로 요청에 포함하지 않습니다.
        @NotNull(message="JPA Auditing이 적용되어있지 않으므로, 생성일자를 주입해주십시오.")
        String updatedAt
) {
    public UpdateBookCommand toCommand() {
        return new UpdateBookCommand(
                this.title,
                this.author,
                this.content,
                this.coverImageUrl,
                this.category,
                this.updatedAt
        );
    }
}
