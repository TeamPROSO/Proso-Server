package com.prosoteam.proso.domain.theme.ThemeRepository;

import com.prosoteam.proso.domain.bookmark.model.BookmarkStatus;
import com.prosoteam.proso.domain.theme.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findById(String Id);

    /**
     * 작성한 테마 조회
     */
    @Query("select b from Theme b where b.user.socialId= :userId")
    List<Theme> getThemeList(@Param("userId") Long userId);
}


