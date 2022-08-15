package com.prosoteam.proso.domain.content;


import com.prosoteam.proso.domain.content.ContentRepository.ContentRepository;
import com.prosoteam.proso.domain.content.dto.RandomContentResponse;
import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.domain.content.dto.ContentCreationRequest;
import com.prosoteam.proso.domain.theme.ThemeRepository.ThemeRepository;
import com.prosoteam.proso.domain.theme.model.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final ThemeRepository themeRepository;
    private final ContentRepository contentRepository;

    //콘텐츠 생성
    public Content createContent(ContentCreationRequest content) {
        Optional<Theme> theme = themeRepository.findById(content.getThemeId());
        if (!theme.isPresent()) {
            throw new EntityNotFoundException(
                    "Theme Not Found");
        }

        Content contentToCreate = new Content();
        BeanUtils.copyProperties(content, contentToCreate);
        contentToCreate.setTheme(theme.get());

        //content 3개 이상 올려야 theme 'INACTIVE' -> 'ACTIVE'
        if(contentToCreate.getTheme().getCountOfContents()>=2){
            contentToCreate.getTheme().changeStatus();
        }

        return contentRepository.save(contentToCreate);
    }



    //테마 아이디를 통한 콘텐츠 랜덤조회
    @Transactional
    public Object readContent(Long themeId) {
        Optional<Theme> theme = themeRepository.findById(themeId);

        List<Content> lists = theme.get().getContentsList(themeId);
        Collections.shuffle(lists);

        List<RandomContentResponse> newList = new ArrayList<>();
        lists.forEach(content -> {
                    newList.add(
                            RandomContentResponse.builder()
                                    .contentId(content.getId())
                                    .contentTitle(content.getContentTitle())
                                    .contentContent(content.getContentContent())
                                    .themeId(content.getTheme().getThemeId())
                                    .themeTitle(content.getTheme().getThemeTitle())
                                    .themeIntroduce(content.getTheme().getThemeIntroduce())
                                    .themeImgUrl(content.getTheme().getThemeImgUrl())
                                    .userId(content.getTheme().getUser().getId())
                                    .userName(content.getTheme().getUser().getUserName())
                                    .build()
                    );
                }
        );

        List<RandomContentResponse> randomResult = newList.subList(0,2);
        return randomResult.get(1);

    }






    //콘텐츠 수정
    public Content updateContent (Long id, ContentCreationRequest request){
        Optional<Content> optionalContent = contentRepository.findById(id);
        if(!optionalContent.isPresent()){
            throw new EntityNotFoundException(
                    "Content is not present in the database!!"
            );
        }

        Content content = optionalContent.get();
        content.setContentTitle(request.getContentTitle());
        content.setContentContent(request.getContentContent());
        return contentRepository.save(content);
    }

    //콘텐츠 삭제
    public void deleteContent(Long id,ContentCreationRequest request) {
        Optional<Theme> theme = themeRepository.findById(request.getThemeId());
        Optional<Content> optionalContent = contentRepository.findById(id);

        Content con = optionalContent.get();
        contentRepository.deleteById(id);

        //컨텐츠 삭제할 때 3개미만 되면 테마 INACTIVE 처리
        if(con.getTheme().getCountOfContents()<3){
            con.getTheme().reChangeStatus();
            themeRepository.save(theme.get());
        }
    }

}