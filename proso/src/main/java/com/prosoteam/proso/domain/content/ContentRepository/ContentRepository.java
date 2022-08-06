package com.prosoteam.proso.domain.content.ContentRepository;

import com.prosoteam.proso.domain.content.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Optional<Content> findById(String Id);
}

