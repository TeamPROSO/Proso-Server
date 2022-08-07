package com.prosoteam.proso.domain.bookmark.repository;

import com.prosoteam.proso.domain.bookmark.model.Bookmark;
import com.prosoteam.proso.domain.bookmark.model.BookmarkStatus;
import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    @Query("select b from Bookmark b where b.user.socialId= :userId and b.theme.id= :themeId ")
    Optional<Bookmark> getBookmark(@Param("userId") Long userId, @Param("themeId") Long themeId);

    /**
     * 나중에 마이페이지에서 북마크한 테마 조회할 때 사용
     */
    @Query("select b.theme from Bookmark b where b.user.socialId= :userId and b.status= :status")
    List<Theme> getThemeList(@Param("userId") Long userId, @Param("status") BookmarkStatus status);
}