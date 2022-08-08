package com.prosoteam.proso.domain.main.contentImg;

import com.prosoteam.proso.domain.content.ContentRepository.ContentRepository;
import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.domain.main.contentImg.ContentImgRepository.ContentImgRepository;
import com.prosoteam.proso.domain.main.contentImg.model.ContentImg;
import com.prosoteam.proso.domain.main.contentImg.model.ContentImgCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
@Service
@RequiredArgsConstructor

public class ContentImgService {

    private final ContentRepository contentRepository;
    private final ContentImgRepository contentImgRepository;

    //콘텐츠 생성
    public ContentImg createContentImg(ContentImgCreationRequest contentImg) {
        Optional<Content> content = contentRepository.findById(contentImg.getContentId());
        if (!content.isPresent()) {
            throw new EntityNotFoundException(
                    "Theme Not Found");
        }

        ContentImg contentImgToCreate = new ContentImg();
        BeanUtils.copyProperties(contentImg, contentImgToCreate);
        contentImgToCreate.setContent(content.get());
        return contentImgRepository.save(contentImgToCreate);
    }

}
