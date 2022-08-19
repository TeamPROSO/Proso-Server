package com.prosoteam.proso.domain.content.ContentRepository;

import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.domain.theme.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findById(Long Id);

}

