package com.aivle.bookapp.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record PostBookRequest(
        @NotNull(message="도서 제목은 필수적으로 작성되어야 합니다.")
        String title,
        @NotNull(message="도서 저자는 필수적으로 작성되어야 합니다.")
        String author,
        @NotNull(message="도서 본문은 필수적으로 작성되어야 합니다.")
        String content,
        @Nullable
        String coverImageUrl,
        @NotNull(message="도서 분류는 필수적으로 지정되어야 합니다.")
        String category,
        @NotNull(message="JPA Auditing이 적용되어있지 않으므로, 생성일자를 주입해주십시오.")
        String createdAt,
        @NotNull(message="JPA Auditing이 적용되어있지 않으므로, 생성일자를 주입해주십시오.")
        String updatedAt
) {
    public CreateBookCommand toCommand()
    {
        return new CreateBookCommand(
                this.title,
                this.author,
                this.content,
                this.coverImageUrl,
                this.category,
                this.createdAt,
                this.updatedAt
        );
    }
}
