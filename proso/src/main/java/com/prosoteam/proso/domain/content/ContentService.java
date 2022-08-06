package com.prosoteam.proso.domain.content;


import com.prosoteam.proso.domain.content.ContentRepository.ContentRepository;
import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.domain.content.model.ContentCreationRequest;
import com.prosoteam.proso.domain.theme.ThemeRepository.ThemeRepository;
import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
    private final ContentRepository contentRepository;

    //콘텐츠 생성
    public Content createContent(ContentCreationRequest content) {
       /*
        Optional<User> user = userRepository.findById(content.getUserId());
        if (!user.isPresent()) {
            throw new EntityNotFoundException(
                    "User Not Found");
        }
        */
        Optional<Theme> theme = themeRepository.findById(content.getThemeId());
        if (!theme.isPresent()) {
            throw new EntityNotFoundException(
                    "Theme Not Found");
        }

        Content contentToCreate = new Content();
        BeanUtils.copyProperties(content, contentToCreate);
        //contentToCreate.setUser(user.get());
        contentToCreate.setTheme(theme.get());
        return contentRepository.save(contentToCreate);
    }


    //콘텐츠 조회
    public Content readContent(Long id){
        Optional<Content> content = contentRepository.findById(id);
        if(content.isPresent()){
            return content.get();
        }
        throw new EntityNotFoundException(
                "Can not find any content under given ID!!"
        );
    }

    //콘텐츠 조회
    public List<Content> readContents(){
        return contentRepository.findAll();
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
    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }

}