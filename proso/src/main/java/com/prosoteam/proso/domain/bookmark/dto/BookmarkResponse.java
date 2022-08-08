package com.prosoteam.proso.domain.bookmark.dto;

import com.prosoteam.proso.domain.bookmark.model.BookmarkStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class BookmarkResponse{

        private final Long userId;
        private final Long themeId;
        private final String themeTitle;
        private final BookmarkStatus status;
}
