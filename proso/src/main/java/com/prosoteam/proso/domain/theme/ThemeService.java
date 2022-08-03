package com.prosoteam.proso.domain.theme;

import com.prosoteam.proso.domain.theme.ThemeRepository.ThemeRepository;
import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.theme.model.ThemeCreationRequest;
import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.domain.user.repository.UserRepository;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;

    //테마 생성
    public Theme createTheme(ThemeCreationRequest theme) {
        Optional<User> user = userRepository.findById(theme.getUserId());
        if (!user.isPresent()) {
            throw new EntityNotFoundException(
                    "User Not Found");
        }

        Theme themeToCreate = new Theme();
        BeanUtils.copyProperties(theme, themeToCreate);
        themeToCreate.setUser(user.get());
        return themeRepository.save(themeToCreate);
    }



    //테마 조회
    public Theme readTheme(Long id){
        Optional<Theme> theme = themeRepository.findById(id);
        if(theme.isPresent()){
            return theme.get();
        }
        throw new EntityNotFoundException(
                "Can not find any theme under given ID!!"
        );
    }

    //테마 조회
    public List<Theme> readThemes(){
        return themeRepository.findAll();
    }




    //테마 수정
    public Theme updateTheme (Long id, ThemeCreationRequest request){
        Optional<Theme> optionalTheme = themeRepository.findById(id);
        if(!optionalTheme.isPresent()){
            throw new EntityNotFoundException(
                    "Theme is not present in the database!!"
            );
        }

        Theme theme = optionalTheme.get();
        theme.setThemeTitle(request.getThemeTitle());
        theme.setThemeIntroduce(request.getThemeIntroduce());
        theme.setThemeImgUrl(request.getThemeImgUrl());
        return themeRepository.save(theme);
    }

    //테마 삭제
    public void deleteTheme(Long id) {
        themeRepository.deleteById(id);
    }

}

