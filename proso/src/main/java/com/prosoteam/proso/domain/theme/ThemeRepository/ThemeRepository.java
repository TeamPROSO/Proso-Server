package com.prosoteam.proso.domain.theme.ThemeRepository;

import com.prosoteam.proso.domain.theme.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findById(String Id);
}


