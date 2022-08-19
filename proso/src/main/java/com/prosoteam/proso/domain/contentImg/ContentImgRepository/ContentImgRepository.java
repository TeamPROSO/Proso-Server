package com.prosoteam.proso.domain.contentImg.ContentImgRepository;

import com.prosoteam.proso.domain.contentImg.model.ContentImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentImgRepository extends JpaRepository<ContentImg, Long> {
    Optional<ContentImg> findById(String Id);
}
