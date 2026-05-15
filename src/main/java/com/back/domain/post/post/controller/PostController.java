package com.back.domain.post.post.controller;

import com.back.domain.post.post.entity.Post;
import com.back.domain.post.post.service.PostService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Validated
public class PostController {
    private final PostService postService;

    private String getWriteFormHtml() {
        return getWriteFormHtml("", "", "", "");
    }

    private String getWriteFormHtml(String errorFieldName, String errorMessage, String title, String content) {
        return """
                <div style="display: flex; flex-direction: column; row-gap: 4px; align-items: center; justify-content: center; height: 100%%">
                    <div style="color: #ff0000">%s</div>
                    <form action="doWrite" method="POST" style="display: flex; flex-direction: column; row-gap: 4px; width: 300px; padding: 4px; background-color: #abe8d7">
                        <input type="text" name="title" placeholder="제목" value="%s" style="height: 24px" />
                        <textarea name="content" placeholder="내용" style="height: 96px; resize: none;">%s</textarea>
                        <input type="submit" value="작성" style="height: 24px" />
                    </form>
                </div>
                <script>
                const errorFieldName = '%s';
                
                if ( errorFieldName.length > 0 )
                {
                    // 현재까지 나온 모든 폼 검색
                    const forms = document.querySelectorAll('form');
                    // 그 중에서 가장 마지막 폼 1개 찾기
                    const lastForm = forms[forms.length - 1];
                
                    lastForm[errorFieldName].focus();
                }
                </script>
                """.formatted(errorMessage, title, content, errorFieldName);
    }

    @GetMapping("/posts/write")
    @ResponseBody
    public String write() {
        return getWriteFormHtml();
    }

    @PostMapping("/posts/doWrite")
    @ResponseBody
    @Transactional
    public String write(
            @NotBlank
            @Size(min = 2, max = 20)
            @RequestParam(defaultValue = "")
            String title,
            @NotBlank
            @Size(min = 2, max = 100)
            @RequestParam(defaultValue = "")
            String content
    ) {
        Post post = postService.write(title, content);

        return "%d번 글이 생성되었습니다.".formatted(post.getId());
    }
}
