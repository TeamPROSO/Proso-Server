package com.prosoteam.proso.domain.contentImg;

import com.prosoteam.proso.domain.content.ContentRepository.ContentRepository;
import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.domain.contentImg.ContentImgRepository.ContentImgRepository;
import com.prosoteam.proso.domain.contentImg.model.ContentImg;
import com.prosoteam.proso.domain.contentImg.model.ContentImgCreationRequest;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
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

    //콘텐츠 이미지 생성
    public ContentImg createContentImg(ContentImgCreationRequest contentImg) {
        Optional<Content> content = contentRepository.findById(contentImg.getContentId());
        if (!content.isPresent()) {
            throw new BaseException(ErrorCode.CONTENT_USERS_EMPTY);
        }

        ContentImg contentImgToCreate = new ContentImg();
        BeanUtils.copyProperties(contentImg, contentImgToCreate);
        contentImgToCreate.setContent(content.get());
        return contentImgRepository.save(contentImgToCreate);
    }

    //콘텐츠 이미지 삭제
    public void deleteContentImg(Long id) {
        Optional<ContentImg> contentImg = contentImgRepository.findById(id);
        if (!contentImg.isPresent()) {
            throw new BaseException(ErrorCode.CONTENT_IMG_USERS_EMPTY);
        }
        contentImgRepository.deleteById(id);

    }
}
