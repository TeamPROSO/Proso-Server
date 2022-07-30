package com.prosoteam.proso.domain.theme;

import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.theme.model.ThemeCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/themes")
@RequiredArgsConstructor
public class Controller {
    private final ThemeService themeService;

    //테마 생성
    @PostMapping("/theme")
    public ResponseEntity<Theme> createTheme (@RequestBody ThemeCreationRequest request) {
        return ResponseEntity.ok(themeService.createTheme(request));
    }

    //테마 조회(아이디로 테마조회 or 테마 전체 조회 둘 다 가능)
    @GetMapping("/theme")
    public ResponseEntity readThemes(@RequestParam(required = false) Long themeId) {
        if (themeId == null) {
            return ResponseEntity.ok(themeService.readThemes());
        }
        return ResponseEntity.ok(themeService.readTheme(themeId));
    }

    //테마 수정
    @PatchMapping("/theme/{themeId}")
    public ResponseEntity<Theme> updateTheme(@RequestBody ThemeCreationRequest request, @PathVariable Long themeId){
        return ResponseEntity.ok(themeService.updateTheme(themeId, request));
    }

    //테마 삭제
    @DeleteMapping("/theme/{themeId}")
    public ResponseEntity<Void> deleteTheme (@PathVariable Long themeId) {
        themeService.deleteTheme(themeId);
        return ResponseEntity.ok().build();

    }


}
