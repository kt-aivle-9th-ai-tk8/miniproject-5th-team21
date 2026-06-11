package com.aivle.bookapp.dto;

public record PatchBookCoverImageUrlRequest (
        String coverImageUrl
) {
//  // 향후 범용 patchbook 적용 시 이것으로 전환
//    public UpdateBookCommand toCommand() {
//        return new UpdateBookCommand(
//                null,
//                null,
//                null,
//                this.coverImageUrl,
//                null,
//                null
//        );
//    }
}
